package org.botchat.exception;

public class CustomBotException extends Exception{

    public CustomBotException(String errorMessage){
        super(errorMessage);
    }
}
