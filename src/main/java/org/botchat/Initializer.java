package org.botchat;

import org.botchat.ai.GigaChatConnector;
import org.botchat.api.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Initializer {
    public static void main(String[] args) throws TelegramApiException {
        GigaChatConnector aiChat = new GigaChatConnector(System.getenv("Authorization"), System.getenv("RqUID"));
        TelegramBot telegramBot = new TelegramBot(System.getenv("BOT_TOKEN"), aiChat);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
