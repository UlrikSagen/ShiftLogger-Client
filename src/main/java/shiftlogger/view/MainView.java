package view;

import controller.Controller;
import view.screens.*;
import view.util.TrayManager;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;


public class MainView extends JFrame{

    private CardLayout cards;
    private JPanel root;
    private final Controller controller;
    Color backgroundColor = new Color(0x141414);
    Color secondaryBackgroundColor = new Color(0x1F1F1F);
    Color textColor = new Color(0xD3D3D3);
    Color secondaryTextColor = new Color(0x7F7F7F);
    Color primaryColor = new Color(0x6b28c4);
    Color secondaryColor = new Color(0x2aa847);
    Font mainFont = new Font("Inter", Font.PLAIN, 16);

    private OverviewScreen overviewScreen;
    private ManualEntryScreen manualEntryScreen;

    public MainView(Controller controller) {
        cards = new CardLayout();
        root = new JPanel(cards);
        root.setBackground(backgroundColor);
        add(root);
        this.controller = controller;
        Image icon = new ImageIcon(res("images/Time-tracker-icon.png")).getImage();

        new TrayManager(this);

        setIconImage(icon);
        setTitle("Time Tracker");
        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        overviewScreen = new OverviewScreen(this, controller);
        manualEntryScreen = new ManualEntryScreen(this, controller);

        //root.add(new SplashScreen(this), "start");
        root.add(new MainScreen(this, controller), "main");
        root.add(overviewScreen, "overview");
        root.add(manualEntryScreen, "manualentry");
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

    public void showManualEntry(){
        manualEntryScreen.refresh();
        cards.show(root, "manualentry");
    }

    public void showEditManualEntry(LocalDate date, LocalTime start, LocalTime end){
        manualEntryScreen.refresh();
        manualEntryScreen.editEntry(date, start, end);
        cards.show(root, "manualentry");
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
