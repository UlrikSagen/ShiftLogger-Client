package app;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

import controller.Controller;
import service.TimeService;
import storage.TimeRepository;
import view.AppTheme;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        AppTheme.apply();

        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(new TimeService(new TimeRepository()));
            new MainView(controller).showUI();
        });
        
        // Additional application logic can be added here
    }
}