package com.mtisw1.mueblesStgo.entities;
import lombok.Data;
import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "empleado")
@Data
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(length = 12, unique = true, nullable = false)  // Considera rut con puntos y guión
    private String rut;
    private String apellidos;
    private String nombres;
    private String fecha_nac;
    private Integer categoria;
    private String fecha_in;
}
