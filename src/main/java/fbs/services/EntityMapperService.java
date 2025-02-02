package fbs.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fbs.models.Airport;
import fbs.repositories.AirportRepository;

@Service
public class EntityMapperService {
    
    AirportRepository airportR;
    
    public EntityMapperService(
        AirportRepository airportR
    ) {
        this.airportR = airportR;
    }

    public Airport findAirportById(UUID id) {
        return airportR.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport"));
    }

}
