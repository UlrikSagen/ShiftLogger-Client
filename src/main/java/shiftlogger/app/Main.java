package shiftlogger.app;

import java.nio.file.Path;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

import shiftlogger.service.AuthService;
import shiftlogger.service.TimeService;
import shiftlogger.controller.Controller;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;
import shiftlogger.storage.*;

public class Main {
    public static void main(String[] args) throws Exception{
        FlatDarkLaf.setup();
        AppTheme.apply();
        AuthService aService = new AuthService();
        aService.login("Ulle", "heiheihei");
        SwingUtilities.invokeLater(() -> {
            Path dbPath = Path.of(System.getProperty("user.home"), ".timetracker", "timetracker.db");
            TimeRepository repo = new SQLiteRepository(dbPath);
            Controller controller = new Controller(new TimeService(repo));
            new MainView(controller).showUI();
        });
    }
}