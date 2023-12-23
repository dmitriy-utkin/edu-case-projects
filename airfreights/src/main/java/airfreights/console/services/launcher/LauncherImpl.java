package airfreights.console.services.launcher;

import airfreights.console.model.AirportList;
import airfreights.console.services.freightSystem.FreightSystem;
import airfreights.console.services.freightSystem.FreightSystemImpl;
import airfreights.console.services.jsonParser.JsonParser;
import airfreights.console.services.jsonParser.JsonParserImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LauncherImpl implements Launcher{

    public LauncherImpl() {
        System.out.println("Сервис поиска авиабилетов\n\n");
        JsonParser jsonParser = new JsonParserImpl();
        AirportList airports = jsonParser.getAirportsListFromJson("src/main/resources/airports.json");
        this.freightSystem = new FreightSystemImpl(airports);
        this.scanner = new Scanner(System.in);
    }

    private final String snippet = "";

    private final String mainMenu = """
                Главное меню:
                
                1 - ввод рейса
                2 - вывод всех рейсов
                3 - поиск рейса по номеру
                4 - завершение работы
                """;

    private final FreightSystem freightSystem;
    private final Scanner scanner;

    @Override
    public void launch() {
        String input = "";
        do {
            System.out.println(mainMenu);
            System.out.println("Введите номер меню: ");
            input = input();
            processInput(input);
        }
        while (!input.equals("4"));
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void overApp() {
        try {
            for (int i = 3; i >= 1; i--) {
                System.out.println("Приложение будет закрыто через " + i + (i == 1 ? " секунду." : " секунды."));
                Thread.sleep(1000);
            }
            System.out.println("Бум!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processInput(String input) {
        switch (input) {
            case "1" -> freightSystem.addFreight();
            case "2" -> freightSystem.getAllFreights();
            case "3" -> freightSystem.getFreightByNumber();
            case "4" -> overApp();
            default -> System.out.println("Некорректный пункт меню, вы можете повторить ввод.");
        }
    }
}
