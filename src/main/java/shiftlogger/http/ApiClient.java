package shiftlogger.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import shiftlogger.model.Contract;
import shiftlogger.model.Settings;

import java.util.Map;
import java.util.UUID;

public class ApiClient {
        
    private final HttpClient client = HttpClient.newHttpClient();
    private String BASE_ADDR;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient(){
        this.BASE_ADDR = "http://100.99.33.100/api";
    }

    public RegisterResponse registerRequest(String username, String pwd) throws Exception{
        Map<String, String> payload = Map.of("username", username, "password", pwd);
        String json = objectMapper.writeValueAsString(payload);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_ADDR + "/auth/register"))
                .header("Content-Type", "application/json")
                .expectContinue(false) 
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

                HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 201) {
            throw new RuntimeException("Registrering feilet: " + res.statusCode() + res.body() + "\n");
        }

        RegisterResponse resp = objectMapper.readValue(res.body(), RegisterResponse.class);
        return resp;
    }

    public LoginResponse loginRequest(String username, String pwd) throws Exception{
        String json = """
                {"username": "%s", "password": "%s"}
                """.formatted(username, pwd);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_ADDR + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("Login feilet: " + res.statusCode() + res.body() + "\n");
        }
        LoginResponse resp = objectMapper.readValue(res.body(), LoginResponse.class);
        return resp;
    }
    
    public record LoginResponse(String username, String token, Contract contract, Settings settings){}
    public record RegisterResponse(UUID userId, String username){}
}
