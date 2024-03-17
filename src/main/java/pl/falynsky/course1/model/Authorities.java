package pl.falynsky.course1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Authorities {

    @Id
    private String username;
    private String authority;
}
