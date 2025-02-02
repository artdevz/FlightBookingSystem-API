package fbs.dto.flight;

import java.util.UUID;

public record FlightRequestDTO(UUID origin, UUID destination, int price) {}
