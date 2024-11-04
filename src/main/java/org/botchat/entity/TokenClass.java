package org.botchat.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenClass {

    @JsonProperty("access_token")
    private String token;

    @JsonCreator
    public TokenClass(@JsonProperty("access_token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenClass{" +
                "access_token='" + token + '\'' +
                '}';
    }
}
