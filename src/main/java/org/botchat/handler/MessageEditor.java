package org.botchat.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface MessageEditor {
    DeleteMessage deleteMessage();
    SendMessage sendMessage(String text);
    EditMessageText editMessageText(String newText);
}
