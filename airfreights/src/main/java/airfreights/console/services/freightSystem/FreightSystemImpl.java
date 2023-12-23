package airfreights.console.services.freightSystem;

import airfreights.console.model.Airport;
import airfreights.console.model.AirportList;
import airfreights.console.model.Freight;
import airfreights.console.model.FreightList;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class FreightSystemImpl implements FreightSystem {

    public FreightSystemImpl(AirportList airportList) {
        this.airportList = airportList;
        this.freightList = new FreightList();
        this.scanner = new Scanner(System.in);
    }

    private final SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
    private final Scanner scanner;
    private final AirportList airportList;
    private final FreightList freightList;
    private final String snippet = " (4 - завершение работы):";
    private final String STOP_WORD = "4";

    @Override
    public void addFreight() {
        Freight freight = new Freight();

        System.out.println("Меню добавления рейса, следуйте инструкциям" + snippet);

        System.out.println("Введите номер рейса в формате XXXX" + snippet);
        String freightNumber = getFreightNumber();
        if (freightNumber.equalsIgnoreCase(STOP_WORD)) return;
        freight.setFreightNumber(freightNumber);

        System.out.println("Введите дату вылета в формате ДД/ММ/ГГГГ" + snippet);
        Date departureDate = getDepartureDate();
        if (departureDate == null) return;
        freight.setDepartureDate(departureDate);

        System.out.println("Введите время вылета в формате ЧЧ:ММ" + snippet);
        String departureTime = getDepartureTime();
        if (departureTime.equalsIgnoreCase(STOP_WORD)) return;
        freight.setDepartureTime(departureTime);

        System.out.println("Введите длительность перелета в формате ЧЧ.ММ" + snippet);
        String flightTime = getFlightTime();
        if (flightTime.equalsIgnoreCase(STOP_WORD)) return;
        freight.setFlightTime(flightTime);

        System.out.println("Введите номер аэропорта вылета в формате XXX" + snippet);
        Airport airportDeparture = getAirport();
        if (airportDeparture == null) return;
        freight.setAirportDeparture(airportDeparture);

        System.out.println("Введите номер аэропорта прибытия в формате XXX" + snippet);
        Airport airportArrival = getAirport(airportDeparture.getAirportCode());
        if (airportArrival == null) return;
        freight.setAirportArrival(airportArrival);


        System.out.println("Введите стоимость купленных билетов в формате XXX+ (>0)" + snippet);
        String freightCost = getFreightCost();
        if (freightCost.equalsIgnoreCase(STOP_WORD)) return;
        freight.setFlightCost(Integer.parseInt(freightCost));

        System.out.println("Рейс успешно добавлен");
        freightList.updateFreightList(freight);
    }

    @Override
    public void getAllFreights() {
        if (freightList.getFreightList() == null || freightList.getFreightList().size() == 0)
            System.out.println("Информация о рейсах отсутствует");
        StringBuilder sb = new StringBuilder();
        freightList.getFreightList().forEach(freight -> sb.append(createFreightInfo(freight)));
        System.out.println("Информация обо всех зарегистрированных рейсах:");
        System.out.println(sb);
    }

    @Override
    public void getFreightByNumber() {
        if (freightList.getFreightList().size() == 0) {
            System.out.println("Информация о рейсах отсутствует");
            return;
        }
        System.out.println("Введите номер рейса для поиска: ");
        String inputNumber = inputData();
        Optional<Freight> freight = freightList.getFreightList().stream()
                .filter(fr -> fr.getFreightNumber().equalsIgnoreCase(inputNumber)).findFirst();
        if (freight.isEmpty()) {
            System.out.println("Рейса с таким номером не существует");
            return;
        }
        System.out.println("Информация о рейсе " + inputNumber + ":");
        System.out.println(createFreightInfo(freight.get()));
    }

    @Override
    public String inputData() {
        return scanner.nextLine();
    }

    private StringBuilder createFreightInfo(Freight freight) {
        StringBuilder sb = new StringBuilder();
        String leftSnippetAllFreights = "Информация о рейсе: ";

        sb.append(leftSnippetAllFreights)
                .append(freight.getFreightNumber()).append(" ").append(simpleDate.format(freight.getDepartureDate()))
                .append(" ").append(freight.getDepartureTime()).append(" ").append(freight.getFlightTime()).append(" ")
                .append(freight.getAirportDeparture().getAirportCode()).append(" ")
                .append(freight.getAirportArrival().getAirportCode()).append(" ")
                .append(freight.getFlightCost()).append("\n");
        return sb;
    }

    private String getFreightNumber() {
        while (true) {
            String freightNumber = inputData().toUpperCase(Locale.ROOT);
            if (freightNumber.equalsIgnoreCase(STOP_WORD)) return freightNumber;
            List<String> alreadyExistedFreights = freightList.getFreightList().stream()
                    .map(Freight::getFreightNumber).toList();
            if (alreadyExistedFreights.contains(freightNumber)) {
                System.out.println("Такой рейс уже создан, попробуйте снова" + snippet);
                continue;
            }
            if (!freightNumber.matches("[a-zA-Z0-9]{4}")) {
                System.out.println("Неправильно введен рейс, попробуйте снова" + snippet);
                continue;
            }
            return freightNumber;
        }
    }

    private Date getDepartureDate() {
        while (true) {
            String departureDate = inputData();
            if (departureDate.equalsIgnoreCase(STOP_WORD)) return null;
            try {
                if (Integer.parseInt(departureDate.split("/")[1]) > 12) {
                    System.out.println("Неправильно указан месяц, попробуйте снова" + snippet);
                    continue;
                }
                return simpleDate.parse(departureDate);
            } catch (Exception e) {
                System.out.println("Неправильный формат даты, попробуйте снова" + snippet);
            }
        }
    }

    private String getDepartureTime() {
        while (true) {
            String departureTime = inputData();
            if (departureTime.equalsIgnoreCase(STOP_WORD)) return departureTime;
            if (!departureTime.matches("[0-9]{2}:[0-9]{2}")) {
                System.out.println("Неправильный формат даты, попробуйте снова");
                continue;
            }
            try {
                if (Integer.parseInt(departureTime.split(":")[0]) > 23
                        || Integer.parseInt(departureTime.split(":")[1]) > 59) {
                    System.out.println("Неправильное время, попробуйте снова");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Неправильный формат даты, попробуйте снова");
                continue;
            }
            return departureTime;
        }
    }

    private String getFlightTime() {
        while (true) {
            String flightTime = inputData();
            if (flightTime.equalsIgnoreCase(STOP_WORD)) return flightTime;
            if (!flightTime.matches("[0-9]{2}.[0-5][0-9]")) {
                System.out.println("Неправильное время полета, попробуйте снова" + snippet);
                continue;
            }
            return flightTime;
        }
    }

    private String getFreightCost() {
        while (true) {
            String freightCost = inputData();
            if (freightCost.equalsIgnoreCase(STOP_WORD)) return freightCost;
            if (!freightCost.matches("[0-9]+") && Integer.parseInt(freightCost) > 0) {
                System.out.println("Неправильно введена стоимость, попробуйте снова" + snippet);
                continue;
            }
            return freightCost;
        }
    }

    private Airport getAirport() {
        while (true) {
            String airportCode = inputData().toUpperCase(Locale.ROOT);
            if (airportCode.equalsIgnoreCase("4")) return null;
            Airport airport = isValidAirport(airportCode);
            if (airport == null) {
                System.out.println("Такого аэропорта нет, попробуйте снова." + snippet);
                continue;
            }
            return airport;
        }
    }

    private Airport getAirport(String airportDeparture) {
        while (true) {
            String airportCode = inputData().toUpperCase(Locale.ROOT);
            if (airportCode.equalsIgnoreCase("4")) return null;
            Airport airport = isValidAirport(airportCode);
            if (airport == null) {
                System.out.println("Такого аэропорта нет, попробуйте снова." + snippet);
                continue;
            }
            if (airport.getAirportCode().equalsIgnoreCase(airportDeparture)) {
                System.out.println("Аэропорт прибытия не может быть идентичным аэропорту вылета, попробуйте снова");
                continue;
            }
            return airport;
        }
    }

    private Airport isValidAirport(String airport) {
        return airportList.getAirportList().stream()
                                        .filter(ap -> ap.getAirportCode().equalsIgnoreCase(airport))
                                        .findFirst().orElse(null);
    }

}
