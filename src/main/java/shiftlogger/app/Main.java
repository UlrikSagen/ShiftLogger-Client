package shiftlogger.app;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

import shiftlogger.service.AuthService;
import shiftlogger.service.TimeService;
import shiftlogger.controller.Controller;
import shiftlogger.http.ApiClient;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;

public class Main {
    public static void main(String[] args) throws Exception{
        FlatDarkLaf.setup();
        AppTheme.apply();
        SwingUtilities.invokeLater(() -> {
            try{
                Controller controller = new Controller(new TimeService(new ApiClient()), new AuthService());
                new MainView(controller).showUI();

            }catch(Exception e){
                System.out.println(e.getMessage() + "hei");
            }
        });
    }
}