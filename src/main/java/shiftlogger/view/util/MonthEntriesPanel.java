package shiftlogger.view.util;

import shiftlogger.controller.Controller;
import shiftlogger.view.MainView;
import shiftlogger.model.TimeEntry;

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
        table.setFocusable(true);


        // Høyreklikk-meny
        JPopupMenu menu = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Rediger");
        JMenuItem del = new JMenuItem("Slett");
        edit.addActionListener(e -> editSelected());
        del.addActionListener(e -> deleteSelected());
        menu.add(edit);
        menu.add(del);

        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row < 0) return;

                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    editSelected();
                }
            }

            @Override public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row < 0) return;

                if (e.getButton() == MouseEvent.BUTTON3) {
                    table.setRowSelectionInterval(row, row);
                    menu.show(table, e.getX(), e.getY());
                }
            }
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
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));
        scrollPane.setViewportBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setEntriesForMonth(java.util.List<TimeEntry> entries) {
        model.setEntries(entries);
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        TimeEntry entry = model.getEntryAt(row);
        int ok = JOptionPane.showConfirmDialog(this, "Edit: " + entry.date() + " " + entry.start() + "-" + entry.end(), "Bekreft endring", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION){
            view.showEditManualEntry(entry.id(), entry.date(), entry.start(), entry.end());
        }
    }

    private void deleteSelected(){
        int row = table.getSelectedRow();
        if (row < 0) return;

        TimeEntry entry = model.getEntryAt(row);

        int ok = JOptionPane.showConfirmDialog(this, "Vil du slette denne oppførningen?     \n" + entry.date() + " (" + entry.start() + "-" + entry.end() + ")", "Bekreft sletting", JOptionPane.YES_NO_OPTION);

        if (ok == JOptionPane.YES_OPTION) {
            try{
                controller.deleteEntry(entry.id());
                model.removeAt(row);

            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Kunne ikke slette entry");
            }
        }
    }
}