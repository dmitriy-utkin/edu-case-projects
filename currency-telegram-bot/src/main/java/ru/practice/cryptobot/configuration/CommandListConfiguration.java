package ru.practice.cryptobot.configuration;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CommandListConfiguration {

    private String commands = """
            Available commands:
                -> /help - to get a list of available commands
                -> /get_price - to get the BTC price in USD
                -> /subscribe [price] - to subscribe for the exact BTC price in USD
                -> /get_subscription - to get the price what you are following
                -> /unsubscribe - to cancel your subscription
            """;
}
