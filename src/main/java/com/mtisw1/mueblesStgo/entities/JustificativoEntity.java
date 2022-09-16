package com.mtisw1.mueblesStgo.entities;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "justificativo")
@Data
public class JustificativoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String fecha;
    private String rut;
}
