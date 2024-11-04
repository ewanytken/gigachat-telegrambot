package org.botchat.handler.impl;

import org.botchat.handler.MessageEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class MessageEditorImpl implements MessageEditor {
    private Message message;
    final static Logger logger = LoggerFactory.getLogger(MessageEditorImpl.class);
    public MessageEditorImpl(Message message){
        this.message = message;
    }
    @Override
    public DeleteMessage deleteMessage(){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(this.message.getChatId());
        deleteMessage.setMessageId(this.message.getMessageId());
        return deleteMessage;
    }
    @Override
    public SendMessage sendMessage(String text){
        Optional<SendMessage> sendMessage = Optional.empty();
        if(this.message.hasText()) {
            Long chatId = this.message.getChatId();
            sendMessage = Optional.of(new SendMessage());
            sendMessage.get().setChatId(String.valueOf(chatId));
            sendMessage.get().setText(text);
        }
        return sendMessage.orElseGet(SendMessage::new);
    }
    @Override
    public EditMessageText editMessageText(String newText){
        Optional<EditMessageText> editMessage = Optional.empty();
        if(this.message.hasText()) {
            editMessage = Optional.of(new EditMessageText());
            editMessage.get().setChatId(String.valueOf(this.message.getChatId()));
            editMessage.get().setMessageId(this.message.getMessageId());
            editMessage.get().setText(newText);
        }
        return editMessage.orElseGet(EditMessageText::new);
    }
}
