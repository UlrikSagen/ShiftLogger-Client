package shiftlogger.view.screens;

import shiftlogger.controller.Controller;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class LoginScreen extends JPanel {

    private final MainView view;
    private final Controller controller;

    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    private final JButton loginButton = new JButton("Log in");
    private final JButton exitButton = new JButton("Exit");
    private final JLabel statusLabel = new JLabel(" "); // plassholder for feil/status

    public LoginScreen(MainView view, Controller controller) {
        this.view = view;
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(AppTheme.BG);

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
        card.setBackground(AppTheme.BG); // legg til i AppTheme (evt bruk BG og border)
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo
        ImageIcon logo = new ImageIcon(view.res("images/Time-tracker-logo.png"));
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(logoLabel);
        card.add(Box.createVerticalStrut(18));

        // Tittel
        JLabel title = new JLabel("Welcome back");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        title.setForeground(AppTheme.TEXT);
        card.add(title);

        JLabel subtitle = new JLabel("Dont have a user? Click here to create one");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Inter", Font.PLAIN, 12));
        subtitle.setForeground(AppTheme.TEXT_MUTED);
        //card.add(subtitle);

        card.add(Box.createVerticalStrut(22));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension fieldSize = new Dimension(260, 36);
        usernameField.setPreferredSize(fieldSize);
        usernameField.setMinimumSize(fieldSize);
        usernameField.setMaximumSize(fieldSize);

        passwordField.setPreferredSize(fieldSize);
        passwordField.setMinimumSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);

        AppTheme.styleTextField(usernameField);
        AppTheme.styleTextField(passwordField);

        addRow(form, 0, "Username", usernameField);
        addRow(form, 1, "Password", passwordField);

        card.add(form);
        card.add(subtitle);
        card.add(Box.createVerticalStrut(14));

        // Status (feil/info)
        statusLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        statusLabel.setForeground(AppTheme.ERROR); // legg til i AppTheme, evt bruk TEXT
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(statusLabel);

        card.add(Box.createVerticalStrut(12));

        // Knapper
        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
        buttons.setOpaque(false);
        buttons.setMaximumSize(new Dimension(260, 40));

        AppTheme.stylePrimaryButton(loginButton);
        AppTheme.styleMenuButton(exitButton);

        buttons.add(loginButton);
        buttons.add(exitButton);

        card.add(buttons);

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
        label.setFont(new Font("Inter", Font.PLAIN, 13));
        label.setForeground(AppTheme.TEXT);
        form.add(label, c);

        c.gridy = row * 2 + 1;
        c.insets = new Insets(0, 0, 12, 0);
        form.add(field, c);
    }

    private void wireActions() {
        exitButton.addActionListener(e -> view.exit());

        passwordField.addActionListener(e -> login());
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());
        loginButton.addActionListener(e -> login());
    }

    private void login() {
        setBusy(true);
        statusLabel.setText(" ");

        String username = usernameField.getText().trim();
        char[] pw = passwordField.getPassword();
        String password = new String(pw);

        if (username.isBlank()) {
            statusLabel.setText("Username is required.");
            setBusy(false);
            return;
        }
        if (password.isBlank()) {
            statusLabel.setText("Please enter password");
            setBusy(false);
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override protected Void doInBackground() throws Exception {
                
                try{
                    controller.login(username, password);
                } catch (RuntimeException e){
                    throw new RuntimeException();
                }
                return null;
            }

            @Override protected void done() {
                setBusy(false);
                Arrays.fill(pw, '\0'); // wipe char[] litt ryddigere

                try {
                    get();
                    view.showMain();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    statusLabel.setText("Login failed.");
                }
            }
        };

        worker.execute();
    }

    private void setBusy(boolean busy) {
        loginButton.setEnabled(!busy);
        exitButton.setEnabled(!busy);
        usernameField.setEnabled(!busy);
        passwordField.setEnabled(!busy);
        loginButton.setText(busy ? "Logging in..." : "Log in");
    }
}
