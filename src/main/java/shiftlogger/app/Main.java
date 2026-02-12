package app;

import java.nio.file.Path;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

import controller.Controller;
import service.TimeService;
import storage.TimeRepository;
import view.MainView;
import view.util.AppTheme;
import storage.SQLiteRepository;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        AppTheme.apply();
        
        SwingUtilities.invokeLater(() -> {
            Path dbPath = Path.of(System.getProperty("user.home"), ".timetracker", "timetracker.db");
            TimeRepository repo = new SQLiteRepository(dbPath);
            Controller controller = new Controller(new TimeService(repo));
            new MainView(controller).showUI();
        });
    }
}