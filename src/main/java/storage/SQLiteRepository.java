package storage;

import model.Contract;
import model.TimeEntry;

import java.nio.file.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class SQLiteRepository implements TimeRepository {

    private final String url;
    private final ContractLoader contractLoader = new ContractLoader();

    public SQLiteRepository(Path dbPath) {
        this.url = "jdbc:sqlite:" + dbPath.toAbsolutePath();
        try {
            Path parent = dbPath.getParent();
            if (parent != null) Files.createDirectories(parent);
        } catch (Exception e) {
            throw new RuntimeException("Kunne ikke opprette mappe for DB", e);
        }
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void initSchema() {
        String sql = """
            CREATE TABLE IF NOT EXISTS time_entries (
              date TEXT PRIMARY KEY,
              start_time TEXT NOT NULL,
              end_time TEXT NOT NULL
            );
            """;
        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("DB schema init feilet", e);
        }
    }

    @Override
    public List<TimeEntry> loadEntries() {
        String sql = "SELECT date, start_time, end_time FROM time_entries ORDER BY date";
        List<TimeEntry> out = new ArrayList<>();

        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("date"));
                LocalTime start = LocalTime.parse(rs.getString("start_time"));
                LocalTime end = LocalTime.parse(rs.getString("end_time"));
                out.add(new TimeEntry(date, start, end));
            }
        } catch (SQLException e) {
            throw new RuntimeException("loadEntries feilet", e);
        }
        return out;
    }

    @Override
    public void saveEntries(List<TimeEntry> entries) {
        // enkleste: overskriv alt (greit for nå)
        String deleteSql = "DELETE FROM time_entries";
        String upsertSql = """
            INSERT INTO time_entries(date, start_time, end_time)
            VALUES(?, ?, ?)
            ON CONFLICT(date) DO UPDATE SET
              start_time = excluded.start_time,
              end_time   = excluded.end_time
            """;

        try (Connection c = connect()) {
            c.setAutoCommit(false);

            try (Statement st = c.createStatement()) {
                st.executeUpdate(deleteSql);
            }

            try (PreparedStatement ps = c.prepareStatement(upsertSql)) {
                for (TimeEntry e : entries) {
                    ps.setString(1, e.getDate().toString());
                    ps.setString(2, e.getStart().toString());
                    ps.setString(3, e.getEnd().toString());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException("saveEntries feilet", e);
        }
    }

    @Override
    public Contract loadContract() {
        return contractLoader.loadContract(); // behold json som før
    }

    @Override
    public void saveContract(Contract contract) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
