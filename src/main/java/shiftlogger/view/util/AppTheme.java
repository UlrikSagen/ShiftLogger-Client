package shiftlogger.view.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import shiftlogger.model.Contract;

import java.awt.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Central theme for the whole Swing app.
 * Call AppTheme.apply() once before creating any Swing components.
 * This file is AI-Generated.
 */
public final class AppTheme {

    private AppTheme() {}
    private static String path;
    private static ObjectMapper objectMapper = new ObjectMapper();

    // ===== Your palette =====
    public static Color BG            = new Color(0x141414);
    public static Color BG_2          = new Color(0x1F1F1F);
    public static Color BG_3          = new Color(0x2A2A2A);
    public static Color CARD_BG       = new Color(0x1F1F1F);

    public static Color TEXT          = new Color(0xD3D3D3);
    public static Color TEXT_MUTED    = new Color(0x7F7F7F);

    public static Color PRIMARY       = new Color(0x6B28C4); // purple
    public static Color SECONDARY     = new Color(0x2AA847); // green

    public static Color BORDER        = new Color(0x343434);
    public static Color FOCUS         = PRIMARY;

    public static Color ERROR         = new Color(0xD04F4F);
    public static Color SUCCESS       = new Color(0x008000);

    public static Color WARNING       = new Color(0xD0A94F);
    public static Color INFO          = new Color(0x4F86D0);

    // ===== Typography =====
    public static final Font FONT_BASE      = new Font("Inter", Font.PLAIN, 14);
    public static final Font FONT_SUBTITLE      = new Font("Inter", Font.PLAIN, 12);
    public static final Font FONT_BIG_BUTTON      = new Font("Inter", Font.BOLD, 15);
    public static final Font FONT_TITLE     = new Font("Inter", Font.BOLD, 18);
    public static final Font FONT_MONO      = new Font(Font.MONOSPACED, Font.PLAIN, 13);

    // ===== Dimensions =====
    public static final Dimension MENU_SIZE = new Dimension(150, 28);
    public static final Dimension PICKER_SIZE = new Dimension(200, 30);
    public static final Dimension mainSize = new Dimension(200, 40);
    public static final Dimension systemMessageSize = new Dimension(200, 15);


    // ===== Public entrypoint =====
    public static void apply(String theme) {

        if(theme.equals("dark")){
            path = "data/theme_dark.json";
        }
        else if(theme.equals("light")){
            path = "data/theme_light.json";
        }
        load(path);
        installDefaults();
        installFonts(FONT_BASE);
    }

    public static void load(String path){
        ThemeConfig t;
        try (InputStream in = AppTheme.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Fant ikke filen: " + path);
            }
            String json = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            t = objectMapper.readValue(json, ThemeConfig.class);
        } catch (Exception e) {
            throw new IllegalStateException("Feil ved lasting av theme", e);
        }
    
        BG          = Color.decode(t.colors().get("BG"));
        BG_2        = Color.decode(t.colors().get("BG_2"));
        BG_3        = Color.decode(t.colors().get("BG_3"));
        CARD_BG     = Color.decode(t.colors().get("CARD_BG"));
        TEXT        = Color.decode(t.colors().get("TEXT"));
        TEXT_MUTED  = Color.decode(t.colors().get("TEXT_MUTED"));
        PRIMARY     = Color.decode(t.colors().get("PRIMARY"));
        SECONDARY   = Color.decode(t.colors().get("SECONDARY"));
        BORDER      = Color.decode(t.colors().get("BORDER"));
        FOCUS       = Color.decode(t.colors().get("FOCUS"));
        ERROR       = Color.decode(t.colors().get("ERROR"));
        SUCCESS     = Color.decode(t.colors().get("SUCCESS"));
        WARNING     = Color.decode(t.colors().get("WARNING"));
        INFO        = Color.decode(t.colors().get("INFO"));
    }
    public record ThemeConfig(Map<String, String> colors) {}

    private static void installDefaults() {
        // ---- Base ----
        put("control", BG_2);
        put("Panel.background", BG);
        put("Viewport.background", BG);
        put("ScrollPane.background", BG);

        put("Label.foreground", TEXT);
        put("Label.disabledForeground", TEXT_MUTED);

        put("ToolTip.background", BG_3);
        put("ToolTip.foreground", TEXT);
        put("ToolTip.border", lineBorder(BORDER));

        put("Separator.foreground", BORDER);
        put("Separator.background", BG);

        // ---- Buttons ----
        put("Button.background", BG_2);
        put("Button.foreground", TEXT);
        put("Button.select", BG_3);
        put("Button.focus", new ColorUIResource(FOCUS));
        put("Button.border", lineBorder(BORDER));
        put("Button.disabledText", TEXT_MUTED);

        // ---- Toggle / checkbox / radio ----
        put("ToggleButton.background", BG_2);
        put("ToggleButton.foreground", TEXT);
        put("ToggleButton.border", lineBorder(BORDER));

        put("CheckBox.background", BG);
        put("CheckBox.foreground", TEXT);
        put("CheckBox.focus", new ColorUIResource(FOCUS));

        put("RadioButton.background", BG);
        put("RadioButton.foreground", TEXT);
        put("RadioButton.focus", new ColorUIResource(FOCUS));

        // ---- Text inputs (JTextField, JTextArea, JPasswordField, etc.) ----
        put("TextField.background", BG_2);
        put("TextField.foreground", TEXT);
        put("TextField.caretForeground", TEXT);
        put("TextField.inactiveForeground", TEXT_MUTED);
        put("TextField.selectionBackground", tint(PRIMARY, 0.25f));
        put("TextField.selectionForeground", TEXT);
        put("TextField.border", lineBorder(BORDER));

        put("PasswordField.background", BG_2);
        put("PasswordField.foreground", TEXT);
        put("PasswordField.caretForeground", TEXT);
        put("PasswordField.selectionBackground", tint(PRIMARY, 0.25f));
        put("PasswordField.selectionForeground", TEXT);
        put("PasswordField.border", lineBorder(BORDER));

        put("TextArea.background", BG_2);
        put("TextArea.foreground", TEXT);
        put("TextArea.caretForeground", TEXT);
        put("TextArea.selectionBackground", tint(PRIMARY, 0.25f));
        put("TextArea.selectionForeground", TEXT);
        put("TextArea.border", lineBorder(BORDER));

        put("TextPane.background", BG_2);
        put("TextPane.foreground", TEXT);
        put("TextPane.caretForeground", TEXT);

        put("EditorPane.background", BG_2);
        put("EditorPane.foreground", TEXT);
        put("EditorPane.caretForeground", TEXT);

        // ---- ComboBox (dropdown) ----
        put("ComboBox.background", BG_2);
        put("ComboBox.foreground", TEXT);
        put("ComboBox.selectionBackground", tint(PRIMARY, 0.25f));
        put("ComboBox.selectionForeground", TEXT);
        put("ComboBox.buttonBackground", BG_3);
        put("ComboBox.border", lineBorder(BORDER));

        // List used by ComboBox popup
        put("List.background", BG_2);
        put("List.foreground", TEXT);
        put("List.selectionBackground", tint(PRIMARY, 0.25f));
        put("List.selectionForeground", TEXT);
        //put("List.cellRenderer", BG);

        // ---- Spinner (great for time/date editing) ----
        put("Spinner.background", BG_2);
        put("Spinner.foreground", TEXT);
        put("Spinner.border", lineBorder(BORDER));

        // ---- Table (overview screen) ----
        put("Table.background", BG_2);
        put("Table.foreground", TEXT);
        put("Table.selectionBackground", tint(PRIMARY, 0.22f));
        put("Table.selectionForeground", TEXT);
        put("Table.gridColor", BORDER);
        put("Table.focusCellHighlightBorder", lineBorder(FOCUS));

        put("TableHeader.background", BG_3);
        put("TableHeader.foreground", TEXT);
        put("TableHeader.cellBorder", lineBorder(BORDER));

        // ---- Scrollbars ----
        put("ScrollBar.background", BG);
        put("ScrollBar.thumb", BG_3);
        put("ScrollBar.thumbDarkShadow", BG_3);
        put("ScrollBar.thumbHighlight", BG_3);
        put("ScrollBar.thumbShadow", BG_3);
        put("ScrollBar.track", BG);

        // ---- Menus ----
        put("MenuBar.background", BG);
        put("MenuBar.foreground", TEXT);

        put("Menu.background", BG);
        put("Menu.foreground", TEXT);

        put("MenuItem.background", BG);
        put("MenuItem.foreground", TEXT);
        put("MenuItem.selectionBackground", tint(PRIMARY, 0.25f));
        put("MenuItem.selectionForeground", TEXT);

        put("PopupMenu.background", BG);
        put("PopupMenu.border", lineBorder(BORDER));

        // ---- Tabs ----
        put("TabbedPane.background", BG);
        put("TabbedPane.foreground", TEXT);
        put("TabbedPane.selected", BG_2);
        put("TabbedPane.contentAreaColor", BG);
        put("TabbedPane.borderHightlightColor", BORDER);
        put("TabbedPane.darkShadow", BG);
        put("TabbedPane.light", BG);

        // ---- Progress / Slider ----
        put("ProgressBar.background", BG_2);
        put("ProgressBar.foreground", SECONDARY);
        put("ProgressBar.selectionBackground", TEXT);
        put("ProgressBar.selectionForeground", BG);

        put("Slider.background", BG);
        put("Slider.foreground", TEXT);

        // ---- Dialogs / OptionPane ----
        put("OptionPane.background", BG);
        put("OptionPane.messageForeground", TEXT);
        put("OptionPane.border", lineBorder(BORDER));
        put("Panel.background", BG); // keep consistent for dialogs too

        // ---- File chooser ----
        put("FileChooser.background", BG);
        put("FileChooser.foreground", TEXT);

        // ---- Tree ----
        put("Tree.background", BG);
        put("Tree.foreground", TEXT);
        put("Tree.selectionBackground", tint(PRIMARY, 0.25f));
        put("Tree.selectionForeground", TEXT);

        // ---- Internal frames (if you ever use them) ----
        put("InternalFrame.activeTitleBackground", BG_3);
        put("InternalFrame.activeTitleForeground", TEXT);
        put("InternalFrame.inactiveTitleBackground", BG_2);
        put("InternalFrame.inactiveTitleForeground", TEXT_MUTED);
        put("InternalFrame.border", lineBorder(BORDER));

    }

    private static void installFonts(Font baseFont) {
        FontUIResource fuir = new FontUIResource(baseFont);

        // Apply to all default keys that are Fonts:
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fuir);
            }
        }
    }

    // ===== Utilities =====
    private static void put(String key, Color color) {
        if (color == null) {
            throw new IllegalArgumentException("AppTheme.put(): Color is null for key: " + key);
        }
        UIManager.put(key, new ColorUIResource(color));
    }   

    private static void put(String key, Object value) {
        UIManager.put(key, value);
    }

    private static Border lineBorder(Color c) {
        return BorderFactory.createLineBorder(c, 1, true);
    }

    /**
     * Returns a tinted color by blending base with background.
     * amount: 0..1 where higher -> closer to base color.
     */
    private static Color tint(Color base, float amount) {
        amount = Math.max(0f, Math.min(1f, amount));
        int r = (int) (BG_2.getRed()   * (1 - amount) + base.getRed()   * amount);
        int g = (int) (BG_2.getGreen() * (1 - amount) + base.getGreen() * amount);
        int b = (int) (BG_2.getBlue()  * (1 - amount) + base.getBlue()  * amount);
        return new Color(r, g, b);
    }

    // ===== Optional helpers you can use per-component =====

    /** Apply a "primary action" style to a button (e.g., Start). */
    public static void stylePrimaryButton(JButton b) {
        b.setFont(FONT_BIG_BUTTON);
        b.setBackground(PRIMARY);
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setPreferredSize(mainSize);
        b.setMaximumSize(mainSize);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
    }

    /** Apply a "success action" style to a button (e.g., Stop/Save if you want green). */
    public static void styleSuccessButton(JButton b) {
        b.setFont(FONT_BIG_BUTTON);
        b.setBackground(SECONDARY);
        b.setForeground(new Color(0x0B0B0B));
        b.setFocusPainted(false);
        b.setPreferredSize(mainSize);
        b.setMaximumSize(mainSize);
        b.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
    }

    /** Make text components look consistent even if L&F behaves differently. */
    public static void styleTextArea(JTextArea ta) {
        ta.setBackground(BG_2);
        ta.setForeground(TEXT);
        ta.setCaretColor(TEXT);
        ta.setSelectionColor(tint(PRIMARY, 0.25f));
        ta.setSelectedTextColor(TEXT);
        ta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }
    public static void styleTextField(JTextField tf) {
        tf.setBackground(BG_2);
        tf.setForeground(TEXT);
        tf.setCaretColor(TEXT);
        tf.setSelectionColor(tint(PRIMARY, 0.25f));
        tf.setSelectedTextColor(TEXT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    public static void styleMenuButton(JButton mb) {
        mb.setFont(AppTheme.FONT_BASE);
        mb.setFocusable(false);
        mb.setPreferredSize(AppTheme.MENU_SIZE);
        mb.setMaximumSize(AppTheme.MENU_SIZE);
        mb.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public static void enableHover(AbstractButton b, HoverRole role) {
    Color base;
    Color hover;
    Color pressed;

        switch (role) {
            case PRIMARY -> {
                base = AppTheme.PRIMARY;
                hover = AppTheme.tint(AppTheme.PRIMARY, 0.15f);
                pressed = AppTheme.tint(AppTheme.PRIMARY, 0.30f);
            }
            case SECONDARY -> {
                base = AppTheme.SECONDARY;
                hover = AppTheme.tint(AppTheme.SECONDARY, 0.15f);
                pressed = AppTheme.tint(AppTheme.SECONDARY, 0.30f);
            }
            default -> {
                base = AppTheme.BG_2;
                hover = AppTheme.BG_3;
                pressed = AppTheme.BG_3.darker();
            }
        }

        b.setBackground(base);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (b.isEnabled()) b.setBackground(hover);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (b.isEnabled()) b.setBackground(base);
            }
            @Override public void mousePressed(MouseEvent e) {
                if (b.isEnabled()) b.setBackground(pressed);
            }
            @Override public void mouseReleased(MouseEvent e) {
                if (b.isEnabled()) b.setBackground(hover);
            }
        });
    }
    public enum HoverRole {
    PRIMARY,
    SECONDARY,
    NEUTRAL
    }
}
