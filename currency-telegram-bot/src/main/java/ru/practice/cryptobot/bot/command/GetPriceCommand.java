package ru.practice.cryptobot.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.practice.cryptobot.service.CryptoCurrencyService;
import ru.practice.cryptobot.utils.TextUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetPriceCommand implements IBotCommand {

    private final CryptoCurrencyService cryptoCurrencyService;

    @Override
    public String getCommandIdentifier() {
        return "get_price";
    }

    @Override
    public String getDescription() {
        return "To get BTC price in USD";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            answer.setText("Current BTC price: " + TextUtil.toString(cryptoCurrencyService.getBitcoinPrice()) + " USD");
            absSender.execute(answer);
        } catch (Exception e) {
            log.error("Error occurred in /get_price методе", e);
        }
    }
}