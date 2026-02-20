package shiftlogger.view.screens;

import shiftlogger.app.Main;
import shiftlogger.controller.Controller;
import shiftlogger.model.Settings;
import shiftlogger.storage.SettingsLoader;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class SettingsScreen extends JPanel {

    private final MainView view;
    private final Controller controller;
    private Settings settings;

    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    private final JRadioButton darkThemeButton = new JRadioButton("dark");
    private final JRadioButton lightThemeButton = new JRadioButton("light");

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JPanel themeGroup = new JPanel();

    private final JButton applyButton = new JButton("Save changes");
    private final JButton backButton = new JButton("Back");
    private final JLabel statusLabel = new JLabel(" ");

    public SettingsScreen(MainView view, Controller controller) {
        this.view = view;
        this.controller = controller;
        this.settings = controller.loadSettings();
        setLayout(new BorderLayout());

        add(buildCenterCard(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        wireActions();
    }

    private JComponent buildCenterCard() {
        // Ytre wrapper som sentrerer kortet
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 28, 24, 28));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tittel
        JLabel title = new JLabel("Settings");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        title.setForeground(AppTheme.TEXT);
        card.add(title);
        card.add(Box.createVerticalStrut(70));

        // Settings form
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);

        refresh();

        
        buttonGroup.add(darkThemeButton);
        buttonGroup.add(lightThemeButton);
        themeGroup.add(darkThemeButton);
        themeGroup.add(lightThemeButton);

        addRow(form, 0, "Theme", themeGroup);

        card.add(form);

        card.add(Box.createVerticalStrut(14));

        // Status (feil/info)
        statusLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        statusLabel.setForeground(AppTheme.ERROR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(statusLabel);

        card.add(Box.createVerticalStrut(12));

        // Knapper
        AppTheme.stylePrimaryButton(applyButton);
        AppTheme.styleMenuButton(backButton);

        card.add(applyButton);
        card.add(Box.createVerticalStrut(70));
        card.add(backButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        wrapper.add(card, gbc);

        return wrapper;
    }

    private JComponent buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel footerText = new JLabel("© 2026 ShiftLogger App", SwingConstants.CENTER);
        footerText.setFont(new Font("Inter", Font.PLAIN, 12));
        footerText.setForeground(AppTheme.TEXT_MUTED);
        footer.add(footerText, BorderLayout.CENTER);

        return footer;
    }

    private void addRow(JPanel form, int row, String labelText, JComponent field) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 6, 0);
        c.gridx = 0;
        c.gridy = row * 2;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(labelText);
        label.setFont(AppTheme.FONT_BASE);
        label.setForeground(AppTheme.TEXT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(label, c);

        c.gridx = 1;
        form.add(field, c);
    }

    private void wireActions() {
        backButton.addActionListener(e -> view.showMain());
        applyButton.addActionListener(e -> apply());
    }

    private void apply() {
        String selectedTheme = darkThemeButton.isSelected() ? "dark" : "light";
        Settings newSettings = new Settings(selectedTheme);
        controller.saveSettings(newSettings);
        view.dispose();
        Main.reLaunch();
    }
    public void setStatus(String status, Color color){
        usernameField.setText("");
        passwordField.setText("");
        this.statusLabel.setForeground(color);
        this.statusLabel.setText(status);
    }

    public void refresh(){
        darkThemeButton.setSelected(settings.theme().equals("dark") ? true : false);
        lightThemeButton.setSelected(settings.theme().equals("light") ? true : false);
    }

}
