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
            -> /subscribe [price] [type] - to subscribe for the exact BTC price in USD for operations BUY or SELL 
            -> /get_subscription - to get the price what you are following
            -> /unsubscribe [type] - to cancel subscription for all, buy or sell notifications (types 'ALL' = all subscriptions, 'BUY' = only buy subscription, 'SELL' - only sell subscriptions
            
            """;
}
