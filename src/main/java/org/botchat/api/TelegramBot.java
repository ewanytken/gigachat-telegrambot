package org.botchat.api;

import org.botchat.ai.GigaChatConnector;
import org.botchat.handler.*;
import org.botchat.handler.impl.MessageEditorImpl;
import org.botchat.handler.impl.UserSearchImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    private GigaChatConnector gigaChatConnector;

    public TelegramBot(String token, GigaChatConnector gigaChatConnector){
        super(token);
        this.gigaChatConnector = gigaChatConnector;
    }

//    261127092 chat id
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        User user = update.getMessage().getFrom();
        MessageEditor messageEditor = new MessageEditorImpl(message);
        UserSearch userSearch = new UserSearchImpl(user);
        User currentUser = update.getMessage().getFrom();
        User needUser = userSearch.findUserByName(currentUser.getFirstName());

        try {
            if(message.getText().equals("/start")) {
                SendMessage mess = messageEditor.sendMessage("Hello " + needUser.getFirstName());
                this.execute(mess);
            }
            if(message.getText().contains("ai ")) {

                    String messageToAi = message.getText().substring(3);
                    System.out.println("Question to AI: " + messageToAi);
                    String role = "Представь, что ты специалист в области: ";
                    String resultAi = this.gigaChatConnector.getMessage(messageToAi, role);
                    System.out.println("AI answer: " + resultAi);

                    SendMessage mess = messageEditor.sendMessage(resultAi);
                    this.execute(mess);
                }

    //                DeleteMessage del = messageEditor.deleteMessage();
    //                this.execute(del);
    //                EditMessageText edit = messageEditor.editMessageText("EDIT Message");
    //                this.execute(edit);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBotUsername() {
        return System.getenv("BOT_NAME");
    }
}
