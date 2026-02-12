package view.util;

import model.TimeEntry;
import controller.Controller;
import view.MainView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.*;


public class MonthEntriesPanel extends JPanel {

    private final TimeEntryTableModel model;
    private final JTable table;
    private final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    
    private final Controller controller;
    private final MainView view;

    public MonthEntriesPanel(MainView view, Controller controller) {
        this.controller = controller;
        this.view = view;
        this.model = new TimeEntryTableModel(controller);
        this.table = new JTable(model);

        setLayout(new BorderLayout());
        setFocusable(false);

        //  Setter tekst i celler sentrert
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setFocusable(false);


        // Dobbeltklikk = edit
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
                    editSelected();
                }
            }DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        });

        // Enter = edit, Delete = slett
        table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "edit");
        table.getActionMap().put("edit", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { editSelected(); }
        });

        table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delete");
        table.getActionMap().put("delete", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { deleteSelected(); }
        });

        // Høyreklikk-meny
        JPopupMenu menu = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Rediger");
        JMenuItem del = new JMenuItem("Slett");
        edit.addActionListener(e -> editSelected());
        del.addActionListener(e -> deleteSelected());
        menu.add(edit);
        menu.add(del);

        table.setComponentPopupMenu(menu);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setEntriesForMonth(java.util.List<TimeEntry> entries) {
        model.setEntries(entries);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        TimeEntry entry = model.getEntryAt(row);
        int ok = JOptionPane.showConfirmDialog(this, "Edit: " + entry.getDate() + " " + entry.getStart() + "-" + entry.getEnd(), "Bekreft endring", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION){
            view.showEditManualEntry(entry.getDate(), entry.getStart(), entry.getEnd());
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        TimeEntry entry = model.getEntryAt(row);

        int ok = JOptionPane.showConfirmDialog(this, "Vil du slette denne oppførningen?     \n" + entry.getDate() + " (" + entry.getStart() + "-" + entry.getEnd() + ")", "Bekreft sletting", JOptionPane.YES_NO_OPTION);

        if (ok == JOptionPane.YES_OPTION) {
            model.removeAt(row);
            controller.deleteEntry(entry.getDate());
        }
    }
}