package airfreights.spring.services.response.implementation;

import airfreights.spring.model.Freight;
import airfreights.spring.services.response.ResponseService;
import com.sun.istack.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public enum ResponseServiceImpl {;

    private interface Result{@BooleanFlag Boolean getResult(); }
    private interface Error{@NotNull String getError(); }

    @Value
    public static class SuccessResponse implements ResponseService, Result {
        public SuccessResponse() {
            this.result = true;
        }
        Boolean result;
    }

    @Value
    public static class SuccessResponseWithMessage implements ResponseService, Result {
        public SuccessResponseWithMessage(String message) {
            this.result = true;
            this.message = message;
        }
        Boolean result;
        String message;
    }

    @Value
    public static class SuccessFreightResponse implements ResponseService, Result {
        public SuccessFreightResponse(Freight freight) {
            this.result = true;
            this.freight = freight;
        }
        Boolean result;
        Freight freight;
    }

    @Value
    public static class SuccessFreightListResponse implements ResponseService, Result {
        public SuccessFreightListResponse(List<Freight> freights) {
            this.result = true;
            this.count = freights.size();
            this.freights = freights;
        }
        Boolean result;
        int count;
        List<Freight> freights;
    }

    @Value
    public static class ErrorResponse implements ResponseService, Result, Error {
        public ErrorResponse(String error) {
            this.result = false;
            this.error = error;
        }
        Boolean result;
        String error;
    }

}
