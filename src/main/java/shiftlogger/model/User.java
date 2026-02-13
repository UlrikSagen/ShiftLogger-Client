package shiftlogger.model;

public record User(
    String username,
    String token,
    Contract contract,
    Settings settings
){}
