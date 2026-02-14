package shiftlogger.view.screens;

import shiftlogger.controller.Controller;
import shiftlogger.view.MainView;
import shiftlogger.view.util.AppTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;

import java.awt.*;
import java.util.Arrays;

public class RegisterScreen extends JPanel {

    private final MainView view;
    private final Controller controller;

    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    private final JButton registerButton = new JButton("Register");
    private final JButton backButton = new JButton("back");
    private final JLabel statusLabel = new JLabel(" ");

    public RegisterScreen(MainView view, Controller controller) {
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
        card.setBackground(AppTheme.BG);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo
        ImageIcon logo = new ImageIcon(view.res("images/Time-tracker-logo.png"));
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(logoLabel);
        card.add(Box.createVerticalStrut(18));

        // Tittel
        JLabel title = new JLabel("Register new user:");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        title.setForeground(AppTheme.TEXT);
        card.add(title);
        card.add(Box.createVerticalStrut(22));

        // Login form
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

        //Subtitle
        JEditorPane subtitle = new JEditorPane();
        subtitle.setContentType("text/html");
        subtitle.setEditable(false);
        subtitle.setOpaque(false);
        subtitle.setFocusable(false);
        subtitle.setBackground(AppTheme.BG); 
        subtitle.setFont(AppTheme.FONT_SUBTITLE);
        subtitle.setText("\u00A0");
        card.add(subtitle);
        card.add(Box.createVerticalStrut(14));

        // Status (feil/info)
        statusLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        statusLabel.setForeground(AppTheme.ERROR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(statusLabel);

        card.add(Box.createVerticalStrut(12));

        // Knapper
        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
        buttons.setOpaque(false);
        buttons.setMaximumSize(new Dimension(260, 40));

        AppTheme.stylePrimaryButton(registerButton);
        AppTheme.styleMenuButton(backButton);

        buttons.add(registerButton);
        buttons.add(backButton);

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
        backButton.addActionListener(e -> view.showLogin(" ", AppTheme.ERROR));

        passwordField.addActionListener(e -> register());
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());
        registerButton.addActionListener(e -> register());
    }

    private void register() {
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
        }else if(password.length() < 8){
            statusLabel.setText("Password must be at least 8 characters long");
            setBusy(false);
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override protected Void doInBackground() throws Exception {
                
                try{
                    controller.register(username, password);
                } catch (RuntimeException e){
                    throw new RuntimeException();
                }
                return null;
            }

            @Override protected void done() {
                setBusy(false);
                Arrays.fill(pw, '\0');
                
                try {
                    get();
                    view.showLogin("User Registered!", AppTheme.SUCCESS);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    if (ex.getMessage().contains("exists")){
                        statusLabel.setText("Username allready taken");
                    }
                    else{
                        //statusLabel.setText("Register failed.");
                    }
                }
            }
        };

        worker.execute();
    }

    private void setBusy(boolean busy) {
        registerButton.setEnabled(!busy);
        backButton.setEnabled(!busy);
        usernameField.setEnabled(!busy);
        passwordField.setEnabled(!busy);
        registerButton.setText(busy ? "Registering..." : "Register");
    }

    public void refresh(){
        usernameField.setText("");
        passwordField.setText("");
    }
}
