package shiftlogger.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import shiftlogger.model.User;


public class ApiClient {
        
    private final HttpClient client = HttpClient.newHttpClient();
    private String token;
    private User user;
    private String IP_ADDR = "100.99.33.100";
    private ObjectMapper objectMapper = new ObjectMapper();
    public ApiClient(User user){
        this.user = user;
    }

    public RegisterResponse registerRequest(String username, String pwd) throws Exception{
        String json = """
                {"username": "%s", "password": "%s"}
                """.formatted(username, pwd);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(IP_ADDR + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() != 200) {
            throw new RuntimeException("Registrering feilet: " + res.statusCode());
        }
        String[] parts = res.body().split(",");

        RegisterResponse resp = objectMapper.readValue(res.body(), RegisterResponse.class);
        return resp;
    }
    
    //public LoginResponse loginRequest(String username, String pwd){
    //
    //}

    public record LoginResponse(String token){}
    public record RegisterResponse(UUID userId, String username){}
}
