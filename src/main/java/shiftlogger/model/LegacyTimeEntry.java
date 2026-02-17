package shiftlogger.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record LegacyTimeEntry(
        UUID id,
        LocalDate date,
        LocalTime start,
        LocalTime end
) {}
