package fbs.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "origin_id", nullable = false)
    private Airport origin; // Departure

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Airport destination; // Arrival

    private int price; // Cents of Dollars

    public Flight(Airport origin, Airport destination, int price) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

}
