package org.botchat.ai;

import org.botchat.entity.TokenClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class GigaChatConnector {

    private final String token;

    public GigaChatConnector(String authorization, String uuid){
        this.token = this.tokenObtain(authorization, uuid);
    }

    public String tokenObtain(String codeAuthorization, String UUID){
        Optional<ResponseEntity<TokenClass>> result = Optional.empty();
        try {
            URI uri = new URI("https://ngw.devices.sberbank.ru:9443/api/v2/oauth");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded"); //JSON format to header
            headers.add("Accept", "application/json");
            headers.add("RqUID", UUID);
            headers.add("Authorization", "Basic " + codeAuthorization);

            HttpEntity<String> entity = new HttpEntity<String>("scope=GIGACHAT_API_PERS", headers);
            result = Optional.of(new RestTemplate().exchange(uri, HttpMethod.POST, entity, TokenClass.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert result != null;
        String token = result.orElseThrow().getBody().getToken();
        return token;
    }

    public String getMessage(String message, String systemMessage){

        String answer = null;

        try {
            Optional<String> systemMessageOpt = Optional.ofNullable(systemMessage);

            URI uri = new URI("https://gigachat.devices.sberbank.ru/api/v1/chat/completions");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json"); //JSON format to header
            headers.add("Accept", "application/json");
            headers.add("Authorization", "Bearer " + this.token);

            JSONArray list = new JSONArray();
            JSONObject userJson = new JSONObject();
            JSONObject systemJson = null;

            if (systemMessageOpt.isPresent() && !systemMessage.isEmpty()) {
                systemJson = new JSONObject();
                systemJson.put("role", "system");
                systemJson.put("content", systemMessageOpt.orElseThrow());
                list.put(systemJson);
            }

            userJson.put("role", "user");
            userJson.put("content", message);

            list.put(userJson);

            JSONObject request = new JSONObject();
            request.put("model", "GigaChat");
            request.put("messages", list);
            request.put("stream", Boolean.FALSE);
            request.put("update_interval", 0);

            HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

            RestTemplate utfTemplate = new RestTemplate();
            utfTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            Optional<ResponseEntity<?>> outer =
                    Optional.of(utfTemplate.exchange(uri, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {}));
            Map<String, Object> mapping = (Map<String, Object>) outer.orElseThrow().getBody();

            answer = (String) mapping.entrySet().stream()
                    .filter(x -> x.getKey().equals("choices"))
                    .map(x -> (ArrayList) x.getValue())
                    .map(x -> (Map<String, Object>) x.stream().findFirst().get())
                    .map(x -> (Map<String, Object>) x.get("message"))
                    .map(x -> x.get("content"))
                    .findFirst().orElseGet(() -> "ERROR ANSWER, CHECK LOG");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        assert answer != null;
        return answer;
    }

    public String getMessage(String message){
        return this.getMessage(message, "");
    }
}
