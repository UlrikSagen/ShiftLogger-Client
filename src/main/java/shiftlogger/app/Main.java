package shiftlogger.app;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import shiftlogger.service.AuthService;
import shiftlogger.service.TimeService;
import shiftlogger.storage.SettingsLoader;
import shiftlogger.controller.Controller;
import shiftlogger.http.ApiClient;
import shiftlogger.model.Settings;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;

public class Main {
    private static Controller controller;
    private static Settings settings;
    public static void main(String[] args) throws Exception{
        controller = new Controller(new TimeService(new ApiClient()), new AuthService());
        settings = SettingsLoader.loadSettings();
        setTheme();        
        launch();
    }

    public static void launch(){
        SwingUtilities.invokeLater(() -> {
            try{
                new MainView(settings, controller).showUI();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        });
    }

    public static void reLaunch(){
        settings = SettingsLoader.loadSettings();
        setTheme();        
        launch();
    }

    public static void setTheme(){
        if(settings.theme().equals("dark")){
            FlatDarkLaf.setup();
            AppTheme.apply(settings.theme());
        }
        else if(settings.theme().equals("light")){
            FlatLightLaf.setup();
            AppTheme.apply(settings.theme());
        }
    }
}