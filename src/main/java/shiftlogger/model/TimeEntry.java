package shiftlogger.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TimeEntry(
        UUID id,
        UUID userId,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        OffsetDateTime createdAt,
        OffsetDateTime lastEdit
    ) {}