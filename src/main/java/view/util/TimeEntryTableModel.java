package view.util;

import model.TimeEntry;
import controller.Controller;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;  

public class TimeEntryTableModel extends AbstractTableModel {

    private final String[] cols = {"Dato", "Start", "Slutt", "Varighet"};
    private final List<TimeEntry> rows = new ArrayList<>();

    Controller controller;

    public TimeEntryTableModel(Controller controller){
        this.controller = controller;
    }

    public void setEntries(List<TimeEntry> entries) {
        rows.clear();
        rows.addAll(entries);
        fireTableDataChanged();
    }

    public TimeEntry getEntryAt(int viewRow) {
        return rows.get(viewRow);
    }

    public void removeAt(int row) {
        rows.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int col) { return cols[col]; }

    @Override
    public Object getValueAt(int r, int c) {
        TimeEntry e = rows.get(r);
        return switch (c) {
            case 0 -> TimeEntryFormatter.getDate(e.getDate());
            case 1 -> TimeEntryFormatter.getTime(e.getStart());
            case 2 -> TimeEntryFormatter.getTime(e.getEnd());
            case 3 -> formatDuration(controller.getMinutesByEntry(e));
            default -> "";
        };
    }

    private String formatDuration(int d) {
        long mins = d;
        long h = mins / 60;
        long m = mins % 60;
        return String.format("%d:%02d", h, m);
    }
}
