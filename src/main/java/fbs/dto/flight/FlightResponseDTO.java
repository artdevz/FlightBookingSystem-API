package fbs.dto.flight;

import java.util.UUID;

public record FlightResponseDTO(UUID id, UUID origin, UUID destination, int price) {}
