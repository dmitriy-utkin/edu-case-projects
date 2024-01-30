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

@Service
@RequiredArgsConstructor
@Slf4j
public class GetHelpCommand implements IBotCommand {

    private final CommandListConfiguration commandList;

    @Override
    public String getCommandIdentifier() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "To get a list of available commands";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();

        answer.setChatId(message.getChatId());

        try {
            answer.setText(commandList.getCommands());
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
       }
    }
}
