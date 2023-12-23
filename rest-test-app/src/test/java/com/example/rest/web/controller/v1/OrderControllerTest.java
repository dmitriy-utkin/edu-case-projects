package com.example.rest.web.controller.v1;

import com.example.rest.AbstractTestController;
import com.example.rest.StringTestUtils;
import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.mapper.v1.OrderMapper;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.service.ClientService;
import com.example.rest.service.OrderService;
import com.example.rest.web.model.OrderListResponse;
import com.example.rest.web.model.OrderResponse;
import com.example.rest.web.model.UpsertClientRequest;
import com.example.rest.web.model.UpsertOrderRequest;
import net.bytebuddy.utility.RandomString;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class OrderControllerTest extends AbstractTestController {

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    public void whenFindAll_thenReturnAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        Client firstClient = createClient(1L, null);
        Client secondClient = createClient(2L, null);
        orders.add(createOrder(1L, 100L, firstClient));
        orders.add(createOrder(2L, 200L, secondClient));
        orders.add(createOrder(3L, 300L, secondClient));

        List<OrderResponse> orderResponses = new ArrayList<>();
        orderResponses.add(createOrderResponse(1L, 100L));
        orderResponses.add(createOrderResponse(2L, 200L));
        orderResponses.add(createOrderResponse(3L, 300L));

        OrderListResponse orderListResponse = new OrderListResponse(orderResponses);

        Mockito.when(orderService.findAll()).thenReturn(orders);
        Mockito.when(orderMapper.orderListToOrderListResponse(orders)).thenReturn(orderListResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/order"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/find_all_orders_response.json");

        Mockito.verify(orderService, Mockito.times(1)).findAll();
        Mockito.verify(orderMapper, Mockito.times(1)).orderListToOrderListResponse(orders);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenFindOrderById_thenReturnOrder() throws Exception {
        Client client = createClient(1L, null);
        Order order = createOrder(1L, 100L, client);
        OrderResponse orderResponse = createOrderResponse(1L, 100L);

        Mockito.when(orderService.findById(1L)).thenReturn(order);
        Mockito.when(orderMapper.orderToResponse(order)).thenReturn(orderResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/order/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/find_order_by_id_response.json");


        Mockito.verify(orderService, Mockito.times(1)).findById(1L);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToResponse(order);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateOrderById_thenReturnNewOrder() throws Exception {
        Client client = createClient(1L, null);
        Order order = new Order(1L, "Test product 1", BigDecimal.valueOf(100L), client, Instant.now(), Instant.now());
        Order createdOrder = createOrder(1L, 100L, client);
        OrderResponse orderResponse = createOrderResponse(1L, 100L);
        UpsertOrderRequest request = new UpsertOrderRequest(1L, "Test product 1", BigDecimal.valueOf(100L));

        Mockito.when(orderService.save(order)).thenReturn(createdOrder);
        Mockito.when(orderMapper.orderToResponse(createdOrder)).thenReturn(orderResponse);
        Mockito.when(orderMapper.requestToOrder(request)).thenReturn(order);

        String actualResponse = mockMvc.perform(post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/create_order_response.json");

        Mockito.verify(orderService, Mockito.times(1)).save(order);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToResponse(createdOrder);
        Mockito.verify(orderMapper, Mockito.times(1)).requestToOrder(request);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateOrder_thenReturnUpdatedOrder() throws Exception {
        UpsertOrderRequest request = new UpsertOrderRequest(1L, "New Test product 1", BigDecimal.valueOf(100L));
        Order updatedOrder = new Order(1L, "New Test product 1", BigDecimal.valueOf(100L), null, null, null);
        OrderResponse orderResponse = new OrderResponse(1L, "New Test product 1", BigDecimal.valueOf(100L));

        Mockito.when(orderService.update(updatedOrder)).thenReturn(updatedOrder);
        Mockito.when(orderMapper.requestToOrder(1L, request)).thenReturn(updatedOrder);
        Mockito.when(orderMapper.orderToResponse(updatedOrder)).thenReturn(orderResponse);

        String actualResponse = mockMvc.perform(put("/api/v1/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/update_order_response.json");

        Mockito.verify(orderService, Mockito.times(1)).update(updatedOrder);
        Mockito.verify(orderMapper, Mockito.times(1)).requestToOrder(1L, request);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToResponse(updatedOrder);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenDeleteOrderById_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/order/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(orderService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindOrderByNotExistedId_thenReturnError() throws Exception {
        Mockito.when(orderService.findById(500L)).thenThrow(new EntityNotFoundException("Order with ID 500 not found"));

        String actualResponse = mockMvc.perform((get("/api/v1/order/500")))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/order_by_id_not_found_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateOrderWithoutClientId_thenReturnError() throws Exception {
        UpsertOrderRequest request = new UpsertOrderRequest();
        request.setProduct("Test product 1");
        request.setCost(BigDecimal.valueOf(100L));
        String actualResponse = mockMvc.perform(post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/create_order_without_client_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateOrderWithNegativeClientId_thenReturnError() throws Exception {
        UpsertOrderRequest request = new UpsertOrderRequest(-1L, "Test product 1", BigDecimal.valueOf(100L));
        String actualResponse = mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/create_order_with_negative_client_id_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @MethodSource("invalidProduct")
    public void whenCreateOrderWithProductInvalidSize_thenReturnError(String product) throws Exception {
        UpsertOrderRequest request = new UpsertOrderRequest(1L, product, BigDecimal.valueOf(100L));
        String actualResponse = mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/order/create_order_with_product_length_error.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    private static Stream<Arguments> invalidProduct() {
        return Stream.of(
                Arguments.of(RandomString.make(2)),
                Arguments.of(RandomString.make(201))
        );
    }
}
