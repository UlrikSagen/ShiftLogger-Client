package shiftlogger.service;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shiftlogger.http.ApiClient;
import shiftlogger.http.ApiClient.LoginResponse;
import shiftlogger.model.User;

public class AuthService {
    
    ApiClient client;
    public AuthService(){
        this.client = new ApiClient();
    }
    public boolean registerUser(String username, String pwd){
        try{
            client.registerRequest(username, pwd);
            return true;
        } catch(Exception e){
            throw new RuntimeException("Could not register" + e.getMessage());
        }
    }

    public User login(String username, String pwd) throws Exception{
        try{
            LoginResponse res = client.loginRequest(username, pwd);
            return new User(res.username(), res.token(), res.contract(), res.settings());

        }catch(Exception e){
            throw new RuntimeException("Could not log in: " + e.getMessage());
        }
    }
}
