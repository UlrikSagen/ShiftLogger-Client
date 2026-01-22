package view;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.AWTException;

public class TrayManager {

    private final SystemTray tray;
    private final TrayIcon trayIcon;
    private final MainView view;
    private final boolean supported;


    public TrayManager(MainView view) {
        this.supported = SystemTray.isSupported();
        this.view = view;
        if (!supported) {
            tray = null;
            trayIcon = null;
            return;
        }
        this.tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage("images/Time-tracker-icon.png");

        PopupMenu menu = new PopupMenu();

        MenuItem openItem = new MenuItem("Åpne");
        openItem.addActionListener(e -> open());

        MenuItem exitItem = new MenuItem("Avslutt");
        exitItem.addActionListener(e -> exit());

        menu.add(openItem);
        menu.add(exitItem);

        this.trayIcon = new TrayIcon(image, "Time Tracker", menu);
        trayIcon.setImageAutoSize(true);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void exit(){
        tray.remove(trayIcon);
        view.exit();
    }

    public void open(){
        view.setVisible(true);
        view.toFront();
    }
}
