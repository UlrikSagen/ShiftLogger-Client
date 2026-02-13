package shiftlogger.storage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import shiftlogger.model.Contract;
import shiftlogger.model.Tariff;

public class ContractLoader {

    public Contract loadContract() {
        try (InputStream in = TimeRepository.class.getClassLoader().getResourceAsStream("data/contract.json")) {
            if (in == null) {
                throw new IllegalStateException("Fant ikke filen: data/contract.json");
            }
            String json = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            String salaryType = extractString(json, "salaryType");
            int salary = Integer.parseInt(extractNumber(json, "salary"));
            float overtime = Float.parseFloat(extractNumber(json, "overtimeFactor"));
            boolean paidBreak = Boolean.parseBoolean(extractValue(json, "paidBreak"));
            String tariff = extractString(json, "tariff");
            float hoursByWeek = Float.parseFloat(extractNumber(json, "hoursByWeek"));


            return new Contract(salaryType, salary, overtime, paidBreak, hoursByWeek, new Tariff(tariff));
        } catch (Exception e) {
            throw new IllegalStateException("Feil ved lasting av kontrakt", e);
        }
    }

    private String extractNumber(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*([-+]?[0-9]*\\.?[0-9]+)";
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (m.find()) return m.group(1);
        throw new IllegalStateException("Fant ikke " + key + " i contract.json");
    }

    private String extractValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*(true|false)";
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(pattern, java.util.regex.Pattern.CASE_INSENSITIVE).matcher(json);
        if (m.find()) return m.group(1).toLowerCase();
        throw new IllegalStateException("Fant ikke " + key + " i contract.json");
    }

    private String extractString(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (m.find()) return m.group(1);
        // support nested object like "tariff": { "tariff": "value" }
        pattern = "\"" + key + "\"\\s*:\\s*\\{[^}]*\"tariff\"\\s*:\\s*\"([^\"]*)\"[^}]*\\}";
        m = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (m.find()) return m.group(1);
        throw new IllegalStateException("Fant ikke " + key + " i contract.json");
    }

    public void saveContract(Contract contract){
        System.out.println("Work in progress");
    }
}

