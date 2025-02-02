package fbs.models;

import java.io.Serializable;
// import java.util.TimeZone;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Airport implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Size(min=3, max=3)
    private String sign;

    private String name;

    private String city;

    private String country;

    // private TimeZone timeZone;

    public Airport(@Size(min = 3, max = 3) String sign, String name, String city, String country) {
        this.sign = sign;
        this.name = name;
        this.city = city;
        this.country = country;
    }
    
}
