package view.screens;

import view.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Font;

public class SplashScreen extends JPanel {

    private JButton startButton;
    private JButton exitButton;

    public SplashScreen(MainView view){

        ImageIcon logo = new ImageIcon(view.res("images/Time-tracker-logo.png"));
        setLayout(new BorderLayout());
        //setSize(400, 600);

        //HEADER PANEL
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BODY PANEL
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //BODY LOGO
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        body.add(Box.createVerticalStrut(60));
        body.add(logoLabel);

        //GET STARTED BUTTON
        startButton = new JButton("Get Started");
        startButton.addActionListener(e -> view.showMain());
        startButton.setFocusable(false);
        startButton.setPreferredSize(new Dimension(200, 40));
        startButton.setMaximumSize(new Dimension(200, 40));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        body.add(Box.createVerticalStrut(100));
        body.add(startButton);


        //EXIT BUTTON
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> view.exit());
        exitButton.setFocusable(false);
        exitButton.setPreferredSize(new Dimension(200, 40));
        exitButton.setMaximumSize(new Dimension(200, 40));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        body.add(Box.createVerticalStrut(10));
        body.add(exitButton);

        //FOOTER PANEL
        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());

        //FOOTER TEXT
        JLabel footerText = new JLabel("© 2026 Time Tracker App", SwingConstants.CENTER);
        footerText.setFont(new Font("Inter", Font.PLAIN, 12));
        footerText.setForeground(AppTheme.TEXT_MUTED);
        footerText.setVerticalAlignment(JLabel.CENTER);
        footer.add(footerText);

        //ADD PANELS TO FRAME
        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }
}

