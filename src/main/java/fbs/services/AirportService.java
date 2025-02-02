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

import fbs.dto.airport.AirportRequestDTO;
import fbs.dto.airport.AirportResponseDTO;
import fbs.models.Airport;
import fbs.repositories.AirportRepository;

@Service
public class AirportService {
    
    private final AirportRepository airportR;

    public AirportService(AirportRepository airportR) {
        this.airportR = airportR;
    }

    public void create(AirportRequestDTO data) {

        Airport airport = new Airport(
            data.sign(),
            data.name(),
            data.city(),
            data.country()
        );

        airportR.save(airport);

    }

    public List<AirportResponseDTO> readAll() {

        return airportR.findAll().stream()
            .map(airport -> new AirportResponseDTO(
                airport.getId(),
                airport.getSign(),                
                airport.getName(),
                airport.getCity(),
                airport.getCountry()
            ))
            .collect(Collectors.toList());
    }

    public AirportResponseDTO readById(UUID id) {

        Airport airport = airportR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not Found."));
        
        return new AirportResponseDTO(
            airport.getId(),
            airport.getSign(),                
            airport.getName(),
            airport.getCity(),
            airport.getCountry()
        );
    }

    public void update(UUID id, Map<String, Object> fields) {

        Airport airport = airportR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not Found"));

        fields.forEach((key, value) -> {
            
            switch (key) {
                case "sign":
                    airport.setSign((String) value);

                case "name":
                    airport.setName((String) value);
                    break;
                
                case "city":
                    airport.setCity((String) value);
                    break;

                case "country":
                    airport.setCountry((String) value);
                    break;

                default:
                    Field field = ReflectionUtils.findField(Airport.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, airport, value);
                    }
                    break;
            
            }

        });

        airportR.save(airport);

    }

    public void delete(UUID id) {

        if (!airportR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not Found.");
        airportR.deleteById(id);        

    }

}
