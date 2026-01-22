package app;

import view.*;
import controller.Controller;
import model.TimeEntry;
import service.TimeService;
import storage.TimeRepository;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        AppTheme.apply();
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(new TimeService(), new TimeRepository());
            new MainView(controller).showUI();
        });
        
        // Additional application logic can be added here
    }
}