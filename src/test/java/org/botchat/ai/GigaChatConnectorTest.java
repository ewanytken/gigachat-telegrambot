package org.botchat.ai;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

public class GigaChatConnectorTest {

    @Test
    public void tokenObtain() {
    }

    @ParameterizedTest
    @CsvSource({ "userMessage,systemMessage", "userMessage,null", "userMessage,''" })
    @DisplayName("getMessage Test")
    public void getMessageWithParameters(String message, String systemMessage) {

        Optional<String> systemMessageOpt = Optional.ofNullable(systemMessage);

        JSONArray list = new JSONArray();
        JSONObject userJson = new JSONObject();
        JSONObject systemJson = null;

        if (systemMessageOpt.isPresent() && !systemMessage.isEmpty()) {
            systemJson = new JSONObject();
            systemJson.put("role", "system");
            systemJson.put("content", systemMessageOpt.orElseGet(() -> "Нет роли"));
            list.put(systemJson);
        }

        userJson.put("role", "user");
        userJson.put("content", message);

        list.put(userJson);

        Assertions.assertEquals(2, list.length());
    }

    @Test
    @DisplayName("getMessage Test without parameters")
    public void getMessage() {
        String message = "New message";
        String systemMessage = "role message";
        Optional<String> systemMessageOpt = Optional.ofNullable(systemMessage);

        JSONArray list = new JSONArray();
        JSONObject userJson = new JSONObject();
        JSONObject systemJson = null;

        if (systemMessageOpt.isPresent() && !systemMessage.isEmpty()) {
            systemJson = new JSONObject();
            systemJson.put("role", "system");
            systemJson.put("content", systemMessageOpt.orElseGet(() -> "Нет роли"));
            list.put(systemJson);
        }

        userJson.put("role", "user");
        userJson.put("content", message);

        list.put(userJson);
        Assertions.assertEquals(2, list.length());

        Assertions.assertEquals(1, list.length());
    }
}