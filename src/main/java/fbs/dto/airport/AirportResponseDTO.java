package fbs.dto.airport;

import java.util.UUID;

public record AirportResponseDTO(UUID id, String sign, String name, String city, String country) {}