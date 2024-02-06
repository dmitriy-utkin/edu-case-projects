package ru.practice.cryptobot.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.practice.cryptobot.configuration.CommandListConfiguration;
import ru.practice.cryptobot.service.CryptoCurrencyService;

import java.text.MessageFormat;


@Service
@Slf4j
@RequiredArgsConstructor
public class StartCommand implements IBotCommand {

    private final CryptoCurrencyService cryptoCurrencyService;

    private final CommandListConfiguration commandList;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Launching of this bot";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        cryptoCurrencyService.saveNewUser(message.getChatId(), message.getFrom());

        try {
            answer.setText(MessageFormat.format(
                    "Hello, {0}! This bot will help you to catch the right time to buy or sell the BTCs.\n{1}",
                    message.getFrom().getFirstName(), commandList.getCommands()));
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}