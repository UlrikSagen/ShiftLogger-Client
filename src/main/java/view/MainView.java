package view;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;


public class MainView extends JFrame{

    private CardLayout cards;
    private JPanel root;
    private final Controller controller;
    private JButton startButton;
    private JButton exitButton;
    Color backgroundColor = new Color(0x141414);
    Color secondaryBackgroundColor = new Color(0x1F1F1F);
    Color textColor = new Color(0xD3D3D3);
    Color secondaryTextColor = new Color(0x7F7F7F);
    Color primaryColor = new Color(0x6b28c4);
    Color secondaryColor = new Color(0x2aa847);
    Font mainFont = new Font("Inter", Font.PLAIN, 16);

    private OverviewScreen overviewScreen;

    public MainView(Controller controller) {
        cards = new CardLayout();
        root = new JPanel(cards);
        root.setBackground(backgroundColor);
        add(root);
        this.controller = controller;
        ImageIcon logo = new ImageIcon(res("images/Time-tracker-logo.png"));
        Image icon = new ImageIcon(res("images/Time-tracker-icon.png")).getImage();

        TrayManager tray = new TrayManager(controller, this);

        setIconImage(icon);
        setTitle("Time Tracker");
        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        overviewScreen = new OverviewScreen(this, controller);

        root.add(new SplashScreen(this), "start");
        root.add(new MainScreen(this, controller), "main");
        root.add(overviewScreen, "overview");
        root.add(new ManualEntryScreen(this, controller), "manualentry");
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
        cards.show(root, "manualentry");
    }

    public void exit(){
        System.exit(0);
    }

    public void minimizeToTray(){
        setVisible(false);
    }

    //Public static void enableHover(JButton b, Color normal, Color hover) {
    //   b.setRolloverEnabled(true);
    //   b.setContentAreaFilled(true);
    //   b.setOpaque(true);
    //   b.setBackground(normal);
    //   b.getModel().addChangeListener(e -> {
    //       ButtonModel m = b.getModel();
    //       b.setBackground(m.isRollover() ? hover : normal);
    //   });
    //

    public static java.net.URL res(String path) {
        var url = MainView.class.getClassLoader().getResource(path);
        if (url == null) throw new IllegalStateException("Missing resource: " + path);
        return url;
    }

}
