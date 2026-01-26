package view.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.MainView;
import view.util.AppTheme;
import view.util.MonthEntriesPanel;
import view.util.TimeEntryFormatter;


public class OverviewScreen extends JPanel{

    private final Controller controller;
    private final MainView view;

    private final JPanel header = new JPanel();
    private final JPanel body = new JPanel();
    private final JPanel dropDown = new JPanel();
    private final JPanel menu = new JPanel();

    private final JLabel entries = new JLabel("Overview");
    private final JLabel hoursWorked = new JLabel();
    private final JLabel salaryLabel = new JLabel();

    private final JButton backButton = new JButton("Back");

    private final String[] months = {
        "Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"
    };
    private final JComboBox<String[]> monthList = new JComboBox(months);
    private final String[] years = {
        "2025", "2026", "2027", "2028", "2029", "2030"
    };
    private final JComboBox<String[]> yearList = new JComboBox(years);

    private final LocalDate currentDate = LocalDate.now();
    private final String currentYear = String.valueOf(currentDate.getYear());
    private final int currentMonth = currentDate.getMonthValue();

    private MonthEntriesPanel panelEntriesPanel;

    public OverviewScreen(MainView view, Controller controller){
        this.controller = controller;
        this.view = view;
        this.panelEntriesPanel = new MonthEntriesPanel(this.controller);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //HEADER PANEL
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BODY PANEL
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ENTRIES LABEL
        entries.setAlignmentX(Component.CENTER_ALIGNMENT);
        entries.setFont(AppTheme.FONT_TITLE);

        //TEST FOR ENTRY PANEL
        panelEntriesPanel.setEntriesForMonth(controller.getEntriesByMonth(getMonth(), getYear()));
        panelEntriesPanel.setFocusable(false);

        //HOURS WORKED LABEL
        hoursWorked.setText(TimeEntryFormatter.hoursWorked(controller.getMinutesByMonth(getMonth(), getYear())));
        hoursWorked.setAlignmentX(Component.CENTER_ALIGNMENT);

        //SALARY LABEL
        salaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //DROP DOWN PANEL
        //dropDown.setLayout(new flowLayout());
        dropDown.setAlignmentX(Component.CENTER_ALIGNMENT);


        //MENU PANEL
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BACK BUTTON
        backButton.setFocusable(false);
        backButton.setPreferredSize(AppTheme.MENU_SIZE);
        backButton.setMaximumSize(AppTheme.MENU_SIZE);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> back()); 

        //MONTH DROPDOWN
        monthList.setPreferredSize(new Dimension(130, 20));
        monthList.setMaximumSize(new Dimension(130, 20));
        monthList.addActionListener(e -> refresh());
        monthList.setSelectedItem(currentMonth);

        //YEAR DROPDOWN
        yearList.setPreferredSize(new Dimension(130, 20));
        yearList.setMaximumSize(new Dimension(130, 20));
        yearList.addActionListener(e -> refresh());
        yearList.setSelectedItem(currentYear);

        //ADD COMPONENTS TO SCREEN
        add(Box.createVerticalStrut(10));
        add(header);
        header.add(entries);

        add(dropDown);
        dropDown.add(monthList);
        dropDown.add(yearList);

        add(body);
        body.add(panelEntriesPanel);
        body.add(hoursWorked);
        body.add(salaryLabel);

        add(Box.createVerticalStrut(20));

        add(menu);
        menu.add(backButton);
        add(Box.createVerticalStrut(10));
    }

    public void refresh(){
        panelEntriesPanel.setEntriesForMonth(controller.getEntriesByMonth(getMonth(), getYear()));
        hoursWorked.setText(TimeEntryFormatter.hoursWorked(controller.getMinutesByMonth(getMonth(), getYear())));
        salaryLabel.setText(TimeEntryFormatter.getOvertime(controller.getOvertime(getMonth(), getYear())));
        //salaryLabel.setText("Estimated Salary: " + TimeEntryFormatter.getSalary((controller.getSalary(getMonth(), getYear())))+ "kr.");
    }

    private void back(){
        view.showMain();
    }


    private int getMonth(){
        switch((String) monthList.getSelectedItem()){
            case "Januar": return  1;
            case "Februar": return 2;
            case "Mars": return 3;
            case "April": return 4;
            case "Mai": return 5;
            case "Juni": return 6;
            case "Juli": return 7;
            case "August": return 8;
            case "September": return 9;
            case "Oktober": return 10;
            case "November": return 11;
            case "Desember": return 12;
            default: return 1;
        }
    }
    private int getYear() {
        String y = (String) yearList.getSelectedItem();
        return Integer.parseInt(y);
    }
}
