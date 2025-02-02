package fbs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fbs.models.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {}