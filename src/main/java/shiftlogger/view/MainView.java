package shiftlogger.view;

import shiftlogger.view.screens.*;
import shiftlogger.view.util.AppTheme;
import shiftlogger.view.util.TrayManager;

import javax.swing.*;

import shiftlogger.controller.Controller;
import shiftlogger.model.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public class MainView extends JFrame{

    private CardLayout cards;
    private JPanel root;
    private final Controller controller;
    private Settings settings;
    Color backgroundColor = new Color(0x141414);
    Color secondaryBackgroundColor = new Color(0x1F1F1F);
    Color textColor = new Color(0xD3D3D3);
    Color secondaryTextColor = new Color(0x7F7F7F);
    Color primaryColor = new Color(0x6b28c4);
    Color secondaryColor = new Color(0x2aa847);
    Font mainFont = new Font("Inter", Font.PLAIN, 16);

    private OverviewScreen overviewScreen;
    private ManualEntryScreen manualEntryScreen;
    private LoginScreen loginScreen;
    private RegisterScreen registerScreen;
    private SettingsScreen settingsScreen;

    public MainView(Settings settings, Controller controller) {
        cards = new CardLayout();
        root = new JPanel(cards);
        root.setBackground(AppTheme.BG);
        add(root);
        this.controller = controller;
        this.settings = settings;
        Image icon = new ImageIcon(res("images/Time-tracker-icon.png")).getImage();

        new TrayManager(this);

        setIconImage(icon);
        setTitle("Time Tracker");
        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        overviewScreen = new OverviewScreen(this, controller);
        manualEntryScreen = new ManualEntryScreen(this, controller);
        loginScreen = new LoginScreen(this, controller);
        registerScreen = new RegisterScreen(this, controller);
        settingsScreen = new SettingsScreen(this, controller);

        root.add(loginScreen, "login");
        root.add(registerScreen, "register");

        if(controller.loggedIn()){
            onLoginSuccess();
        }
    }

    public void onLoginSuccess() {
        overviewScreen = new OverviewScreen(this, controller);
        manualEntryScreen = new ManualEntryScreen(this, controller);

        root.add(new MainScreen(this, controller), "main");
        root.add(overviewScreen, "overview");
        root.add(manualEntryScreen, "manualentry");
        root.add(settingsScreen, "settings");

        showMain();
    }
    public void showUI() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMain(){
        cards.show(root, "main");
    }

    public void showOverview(){
        overviewScreen.refresh();
        cards.show(root, "overview");
    }

    public void showLogin(){
        cards.show(root, "login");
    }
    public void showLogin(String status, Color color){
        loginScreen.setStatus(status, color);
        cards.show(root, "login");
    }
    
    public void showRegister(){
        registerScreen.refresh();
        cards.show(root, "register");
    }
    public void showManualEntry(){
        manualEntryScreen.refresh();
        cards.show(root, "manualentry");
    }

    public void showSettings(){
        settingsScreen.refresh();
        cards.show(root, "settings");
    }

    public void showEditManualEntry(UUID id, LocalDate date, LocalTime start, LocalTime end){
        manualEntryScreen.editEntry(id, date, start, end);
        cards.show(root, "manualentry");
    }

    public void logout(){
        cards.show(root, "login");
    }

    public void exit(){
        System.exit(0);
    }

    public void minimizeToTray(){
        setVisible(false);
    }

    public java.net.URL res(String path) {
        var url = MainView.class.getClassLoader().getResource(path);
        if (url == null) throw new IllegalStateException("Missing resource: " + path);
        return url;
    }

}
