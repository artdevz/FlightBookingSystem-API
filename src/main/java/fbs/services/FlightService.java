package fbs.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import fbs.dto.flight.FlightRequestDTO;
import fbs.dto.flight.FlightResponseDTO;
import fbs.models.Flight;
import fbs.repositories.FlightRepository;

@Service
public class FlightService {
    
    private final FlightRepository flightR;

    private final EntityMapperService mapperS;

    public FlightService(FlightRepository flightR, EntityMapperService mapperS) {
        this.flightR = flightR;
        this.mapperS = mapperS;
    }

    public void create(FlightRequestDTO data) {

        Flight flight = new Flight(
            mapperS.findAirportById(data.origin()),
            mapperS.findAirportById(data.destination()),
            data.price()
        );

        flightR.save(flight);

    }

    public List<FlightResponseDTO> readAll() {

        return flightR.findAll().stream()
            .map(flight -> new FlightResponseDTO(
                flight.getId(),
                flight.getOrigin().getId(),                
                flight.getDestination().getId(),
                flight.getPrice()
            ))
            .collect(Collectors.toList());
    }

    public FlightResponseDTO readById(UUID id) {

        Flight flight = flightR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not Found."));
        
        return new FlightResponseDTO(
            flight.getId(),
            flight.getOrigin().getId(),                
            flight.getDestination().getId(),
            flight.getPrice()
        );
    }

    public void update(UUID id, Map<String, Object> fields) {

        Flight flight = flightR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not Found"));

        fields.forEach((key, value) -> {
            
            switch (key) {
                case "price":
                    flight.setPrice((int)value);
                    break;

                default:
                    Field field = ReflectionUtils.findField(Flight.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, flight, value);
                    }
                    break;
            
            }

        });

        flightR.save(flight);

    }

    public void delete(UUID id) {

        if (!flightR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not Found.");
        flightR.deleteById(id);        

    }

}
