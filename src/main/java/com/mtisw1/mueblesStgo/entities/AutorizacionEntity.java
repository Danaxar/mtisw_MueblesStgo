package com.mtisw1.mueblesStgo.entities;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "autorizacion")
@Data
public class AutorizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;
    private String fecha;
    private String rut;
}



