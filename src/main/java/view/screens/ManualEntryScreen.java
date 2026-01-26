package view.screens;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.MainView;
import view.util.*;

public class ManualEntryScreen extends JPanel{
    private final Controller controller;
    private final MainView view;

    private final JPanel header = new JPanel();
    private final JPanel body = new JPanel();
    private final JPanel menu = new JPanel();

    private final JLabel placeholder = new JLabel("MANUAL ENTRY PAGE");

    private final JButton backButton = new JButton("Back");
    

    public ManualEntryScreen(MainView view, Controller controller){
        this.controller = controller;
        this.view = view;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //HEADER PANEL
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BODY PANEL
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);

        //MENU PANEL
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BACK BUTTON
        backButton.setFont(AppTheme.FONT_BASE);
        backButton.setFocusable(false);
        backButton.setPreferredSize(AppTheme.MENU_SIZE);
        backButton.setMaximumSize(AppTheme.MENU_SIZE);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> back());   

        //ADD COMPONENTS TO SCREEN
        add(header);

        add(Box.createVerticalStrut(50));

        add(body);
        body.add(placeholder);

        add(Box.createVerticalStrut(50));

        add(menu);
        menu.add(backButton);

    }

    private void back(){
        view.showMain();
    }
}
