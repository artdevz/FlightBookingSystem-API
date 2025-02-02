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

import fbs.dto.flight.FlightRequestDTO;
import fbs.dto.flight.FlightResponseDTO;
import fbs.services.FlightService;
import jakarta.validation.Valid;

@RequestMapping("/flight")
@RestController
public class FlightController {
    
    private final FlightService flightS;

    public FlightController(FlightService flightS) {
        this.flightS = flightS;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid FlightRequestDTO request) {
        
        flightS.create(request);

        return new ResponseEntity<>("Created Flight", HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDTO>> readAll() {

        return new ResponseEntity<>(flightS.readAll(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> readById(@PathVariable UUID id) {

        return new ResponseEntity<>(flightS.readById(id), HttpStatus.OK);

    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {
        
        flightS.update(id, fields);
        return new ResponseEntity<>("Updated Flight", HttpStatus.OK);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        
        flightS.delete(id);
        return new ResponseEntity<>("Deleted Flight", HttpStatus.OK);
           
    }

}
