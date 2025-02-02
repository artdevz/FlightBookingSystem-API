package fbs.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fbs.dto.airport.AirportRequestDTO;
import fbs.dto.airport.AirportResponseDTO;
import fbs.services.AirportService;
import jakarta.validation.Valid;

@RequestMapping("/airport")
@RestController
public class AirportController {
    
    private final AirportService airportS;

    public AirportController(AirportService airportS) {
        this.airportS = airportS;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid AirportRequestDTO request) {
        
        airportS.create(request);

        return new ResponseEntity<>("Created Airport", HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<AirportResponseDTO>> readAll() {

        return new ResponseEntity<>(airportS.readAll(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDTO> readById(@PathVariable UUID id) {

        return new ResponseEntity<>(airportS.readById(id), HttpStatus.OK);

    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {
        
        airportS.update(id, fields);
        return new ResponseEntity<>("Updated Airport", HttpStatus.OK);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        
        airportS.delete(id);
        return new ResponseEntity<>("Deleted Airport", HttpStatus.OK);
           
    }

}
