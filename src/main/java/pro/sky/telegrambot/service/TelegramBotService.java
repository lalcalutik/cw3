package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotService.class);

    public TelegramBotService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendMessage(long chatId, String text, @Nullable ParseMode parseMode) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        if (parseMode != null) {
            sendMessage.parseMode(parseMode);
        }
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            LOG.error("SendMessage was failed due to: " + sendResponse.description());
        }
    }

    public void sendMessage(long chatId, String text) {
        sendMessage(chatId, text, null);
    }
}
