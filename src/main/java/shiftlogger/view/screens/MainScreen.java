package shiftlogger.view.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import shiftlogger.app.Main;
import shiftlogger.controller.Controller;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;

public class MainScreen extends JPanel {

    private final JPanel header = new JPanel();
    private final JPanel body = new JPanel();
    private final JPanel menu = new JPanel();

    private final JLabel timerLabel = new JLabel("00:00:00");
    private final JButton startButton = new JButton("Start Timer");
    private final JButton stopButton = new JButton("Stop Timer");
    private final JButton manualButton = new JButton("Add Entry");
    private final JButton exitButton = new JButton("Exit");
    private final JButton overviewButton = new JButton("Overview");
    private final JButton settingsButton = new JButton("Settings");
    private final JButton hideButton = new JButton("Hide Window");
    private final JButton logoutButton = new JButton("Log out");
    private final JLabel systemMessage = new JLabel();
    
    Dimension mainSize = new Dimension(200, 40);

    private final Controller controller;
    private final MainView view;

    private final javax.swing.Timer swingTimer;
    private LocalTime startTime;
    private Instant startInstant;
    private LocalDate startDate;
    private LocalTime endTime;

    public MainScreen(MainView view, Controller controller){
        this.controller = controller;
        this.view = view;
        ImageIcon logo = new ImageIcon(view.res("images/Time-tracker-logo.png"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //HEADER PANEL
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //HEADER LOGO
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //DATE TEXT
        JLabel date = new JLabel(controller.getDate());
        date.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BODY PANEL
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);

        //TIMER
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setText("00:00:00");
        timerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        timerLabel.setPreferredSize(mainSize);
        timerLabel.setMinimumSize(mainSize);
        timerLabel.setMaximumSize(mainSize);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        swingTimer = new javax.swing.Timer(1000, e -> {
            long elapsedSeconds = Duration.between(startInstant, Instant.now()).getSeconds();
            timerLabel.setText(formatHMS(elapsedSeconds));
        });
        swingTimer.setInitialDelay(0);


        //START BUTTON
        startButton.setFocusable(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> startTimer());
        AppTheme.stylePrimaryButton(startButton);
        
        //STOP BUTTON
        stopButton.setFocusable(false);
        stopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopButton.addActionListener(e -> stopTimer());
        stopButton.setVisible(false);
        AppTheme.styleSuccessButton(stopButton);

        //SYSTEM MESSAGE LABEL
        systemMessage.setFont(new Font("Inter", Font.PLAIN, 12));
        systemMessage.setPreferredSize(AppTheme.systemMessageSize);
        systemMessage.setMaximumSize(AppTheme.systemMessageSize);
        systemMessage.setMinimumSize(AppTheme.systemMessageSize);
        systemMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        systemMessage.setHorizontalAlignment(SwingConstants.CENTER);

        //MENU PANEL
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //MANUAL ENTRY BUTTON
        AppTheme.styleMenuButton(manualButton);
        manualButton.addActionListener(e -> manualEntry());

        //OVERVIEW BUTTON
        AppTheme.styleMenuButton(overviewButton);
        overviewButton.addActionListener(e -> overview());

        //SETTNGS BUTTON
        AppTheme.styleMenuButton(settingsButton);
        settingsButton.addActionListener(e -> settings());

        //HIDE BUTTON
        AppTheme.styleMenuButton(hideButton);
        hideButton.addActionListener(e -> minimizeToTray());

        //LOGOUT BUTTON
        AppTheme.styleMenuButton(logoutButton);
        logoutButton.addActionListener(e -> logout());

        //EXIT BUTTON
        AppTheme.styleMenuButton(exitButton);
        exitButton.addActionListener(e -> exit());     

        //ADD COMPONENTS TO SCREEN
        add(header);
        header.add(logoLabel);
        add(Box.createVerticalStrut(30));

        add(body);
        body.add(timerLabel);
        body.add(Box.createVerticalStrut(30));
        body.add(date);
        body.add(Box.createVerticalStrut(10));
        body.add(startButton);
        body.add(stopButton);
        body.add(Box.createVerticalStrut(10));
        body.add(systemMessage);

        add(Box.createVerticalStrut(20));

        add(menu);
        menu.add(Box.createVerticalStrut(10));
        menu.add(overviewButton);
        menu.add(Box.createVerticalStrut(10));
        menu.add(manualButton);
        menu.add(Box.createVerticalStrut(10));
        menu.add(settingsButton);
        menu.add(Box.createVerticalStrut(10));
        menu.add(hideButton);
        menu.add(Box.createVerticalStrut(10));
        menu.add(logoutButton);
        menu.add(Box.createVerticalStrut(10));
        menu.add(exitButton);
    }

    private void startTimer(){
        startDate = LocalDate.now();
        startTime = LocalTime.now().withNano(0);
        startInstant = Instant.now();

        startButton.setVisible(false);
        stopButton.setVisible(true);
        systemMessage.setText(" ");
        swingTimer.start();
    }

    private void stopTimer(){
        endTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        startTime = startTime.truncatedTo(ChronoUnit.MINUTES);
        startButton.setVisible(true);
        stopButton.setVisible(false);
        systemMessage.setText(" ");
        swingTimer.stop();
        try{
            if(controller.validateEntry(startDate, startTime, endTime)){
                controller.postEntry(startDate, startTime, endTime);
                systemMessage.setForeground(AppTheme.SUCCESS);
                systemMessage.setText("New shift added");
            }
            else{
                systemMessage.setForeground(AppTheme.ERROR);
                systemMessage.setText("Shift too short");
            }
        }catch(Exception e){
            systemMessage.setText("Could not create entry.");
            System.out.println(e.getMessage());
        }
    }

    private static String formatHMS(long totalSeconds){
        long h = (totalSeconds/3600);
        long m = (totalSeconds % 3600) / 60;
        long s = (totalSeconds % 60);

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    private void manualEntry(){
        view.showManualEntry();
    }

    private void overview(){
        view.showOverview();
    }

    private void settings(){
        view.showSettings();
    }


    private void logout(){
        if(swingTimer.isRunning()){
            stopTimer();
        }
        controller.logout();
        view.logout();
        //view.dispose(); 
        //Main.reLaunch();
    }

    private void exit(){
        if (swingTimer.isRunning()){
            stopTimer();
        }view.logout();
        view.exit();
    }

    private void minimizeToTray(){
        view.minimizeToTray();
    }
}