package fbs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fbs.models.Airport;

public interface AirportRepository extends JpaRepository<Airport, UUID> {}
