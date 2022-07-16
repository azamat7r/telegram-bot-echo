package kz.craftim.samples.telegrambotecho;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class EchoBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;

    public EchoBot(@Value("${telegram.token}") String botToken,
                   @Value("${telegram.username}") String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setReplyToMessageId(update.getMessage().getMessageId());
            message.setText(update.getMessage().getText());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void setup() {
        System.out.println(getBotUsername() + " started successfully!");
    }
}
