package shiftlogger.view.util;

import java.awt.SystemTray;
import java.awt.TrayIcon;

import shiftlogger.view.MainView;

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
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/shiftlogger-icon.png"));
        Image scaled = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        PopupMenu menu = new PopupMenu();
        this.trayIcon = new TrayIcon(scaled, "ShiftLogger", menu);
        trayIcon.setImageAutoSize(false);
        
        MenuItem openItem = new MenuItem("Åpne");
        openItem.addActionListener(e -> open());

        MenuItem exitItem = new MenuItem("Avslutt");
        exitItem.addActionListener(e -> exit());
        openItem.setFont(AppTheme.FONT_TITLE);
        exitItem.setFont(AppTheme.FONT_TITLE);

        menu.add(openItem);
        menu.add(exitItem);

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
