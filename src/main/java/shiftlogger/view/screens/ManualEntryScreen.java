package view.screens;

import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import controller.Controller;
import view.MainView;
import view.util.*;

public class ManualEntryScreen extends JPanel{
    private final Controller controller;
    private final MainView view;
    private final TimePicker tpStart;
    private final TimePicker tpEnd;
    private final DatePicker dp;
    private final JPanel header = new JPanel();
    private final JPanel datePanel = new JPanel();
    private final JPanel startPanel = new JPanel();
    private final JPanel endPanel = new JPanel();
    private final JPanel body = new JPanel();
    private final JPanel menu = new JPanel();

    private final JLabel dato = new JLabel("dato:");
    private final JLabel start = new JLabel("start:");
    private final JLabel slutt = new JLabel("slutt:");
    private final Locale locale = Locale.forLanguageTag("nb-NO");
    private final DatePickerSettings dateSettings = new DatePickerSettings(locale);
    private final TimePickerSettings timeSettings = new TimePickerSettings(locale);

    private final JLabel placeholder = new JLabel("Add or Edit");
    private final JLabel systemMessage = new JLabel(" ");

    private final JButton enterButton = new JButton("Enter");
    private final JButton backButton = new JButton("Back");

    public ManualEntryScreen(MainView view, Controller controller){
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

        //BODY PANEL
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);

        //MENU PANEL
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        //HEADER TEXT
        placeholder.setFont(AppTheme.FONT_TITLE);
        placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);

        //SYSTEM MESSAGE TEXT
        systemMessage.setFont(AppTheme.FONT_MONO);
        systemMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        systemMessage.setPreferredSize(AppTheme.systemMessageSize);
        systemMessage.setMinimumSize(AppTheme.systemMessageSize);
        systemMessage.setMaximumSize(AppTheme.systemMessageSize);
        systemMessage.setHorizontalAlignment(SwingConstants.CENTER);


        //DATELABEL PANEL
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //START TIME PANEL
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //END TIME PANEL
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
        endPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //DATE PICKER SETTINGS
        dateSettings.setFormatForDatesCommonEra ("dd.MM.yyyy");
        dateSettings.setFontCalendarDateLabels(AppTheme.FONT_BASE);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel,AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates,AppTheme.BG_3);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearNavigationButtons, AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.CalendarTextNormalDates,AppTheme.TEXT);
        dateSettings.setColor(DatePickerSettings.DateArea.TextFieldBackgroundValidDate, AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.TextFieldBackgroundInvalidDate, AppTheme.BG_2);
        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextInvalidDate, AppTheme.TEXT_MUTED);
        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, AppTheme.TEXT);
        dateSettings.setFontValidDate(AppTheme.FONT_BASE);
        dateSettings.setFontInvalidDate(AppTheme.FONT_BASE);
        
        //TIME PICKER SETTINGS
        //timeSettings.setFormatForDisplayTime("hh:mm");
        timeSettings.setColor(TimePickerSettings.TimeArea.TextFieldBackgroundValidTime, AppTheme.BG_2);
        timeSettings.setColor(TimePickerSettings.TimeArea.TextFieldBackgroundInvalidTime, AppTheme.BG_2);
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, AppTheme.TEXT);
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextInvalidTime, AppTheme.TEXT_MUTED);

        //DATE PICKER TEXT
        dato.setAlignmentX(Component.CENTER_ALIGNMENT);

        //DATE PICKER
        dp = new DatePicker(dateSettings);
        dp.setSize(AppTheme.MENU_SIZE);
        dp.setMaximumSize(AppTheme.MENU_SIZE);
        dp.setPreferredSize(AppTheme.MENU_SIZE);
        dp.setDate(LocalDate.now());
        dp.getComponentDateTextField().setBackground(AppTheme.BG_2);
        dp.getComponentDateTextField().setFont(AppTheme.FONT_BASE);
        dp.getComponentDateTextField().setForeground(AppTheme.TEXT);
        dp.setAlignmentX(Component.CENTER_ALIGNMENT);
        //dp.getComponent().setBackground(AppTheme.BG);

        //START TEXT
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        //TIME PICKER START
        tpStart = new TimePicker(timeSettings);
        tpStart.setSize(AppTheme.MENU_SIZE);
        tpStart.setMaximumSize(AppTheme.MENU_SIZE);
        tpStart.setPreferredSize(AppTheme.MENU_SIZE);
        tpStart.setTime(LocalTime.of(8,30));
        tpStart.getComponentTimeTextField().setBackground(AppTheme.BG_2);
        tpStart.getComponentTimeTextField().setForeground(AppTheme.TEXT);
        tpStart.getComponentTimeTextField().setFont(AppTheme.FONT_BASE);
        tpStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //END TEXT
        slutt.setAlignmentX(Component.CENTER_ALIGNMENT);

        //TIME PICKER END
        tpEnd = new TimePicker(timeSettings);
        tpEnd.setSize(AppTheme.MENU_SIZE);
        tpEnd.setMaximumSize(AppTheme.MENU_SIZE);
        tpEnd.setPreferredSize(AppTheme.MENU_SIZE);
        tpEnd.setTime(LocalTime.now());
        tpEnd.getComponentTimeTextField().setBackground(AppTheme.BG_2);
        tpEnd.getComponentTimeTextField().setForeground(AppTheme.TEXT);
        tpEnd.getComponentTimeTextField().setFont(AppTheme.FONT_BASE);
        tpEnd.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ENTER BUTTON
        AppTheme.stylePrimaryButton(enterButton);
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.addActionListener(e -> enter());

        //BACK BUTTON
        AppTheme.styleMenuButton(backButton);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> back());   

        //ADD COMPONENTS TO SCREEN
        add(header);
        header.add(logoLabel);
        //header.add(placeholder);

        add(Box.createVerticalStrut(30));

        add(datePanel);
        add(startPanel);
        add(endPanel);
        datePanel.add(dato);
        datePanel.add(dp);
        startPanel.add(start);
        startPanel.add(tpStart);
        endPanel.add(slutt);
        endPanel.add(tpEnd);
        add(Box.createVerticalStrut(30));
        add(body);
        body.add(enterButton);
        add(Box.createVerticalStrut(10));
        body.add(systemMessage);


        add(Box.createVerticalStrut(110));
        add(menu);
        menu.add(backButton);
    }

    private void back(){
        view.showMain();
    }
    private void enter(){
        LocalDate date = getDate();
        LocalTime start = getStartTime();
        LocalTime end = getEndTime();
        if(controller.validateEntry(date, start, end)){
            controller.addOrEditEntry(date, start, end);
            systemMessage.setForeground(AppTheme.SECONDARY);
            systemMessage.setText("Entry submitted");
        }
        else{
            systemMessage.setForeground(AppTheme.WARNING);
            systemMessage.setText("Invalid entry");
        }
    }
    private LocalDate getDate(){
        return dp.getDate();
    }
    private LocalTime getEndTime(){
        return tpEnd.getTime();
    }
    private LocalTime getStartTime(){
        return tpStart.getTime();
    }
    public void refresh(){
        systemMessage.setText(" ");
    }
    public void editEntry(LocalDate date, LocalTime start, LocalTime end){
        dp.setDate(date);
        tpStart.setTime(start);
        tpEnd.setTime(end);
    }
}
