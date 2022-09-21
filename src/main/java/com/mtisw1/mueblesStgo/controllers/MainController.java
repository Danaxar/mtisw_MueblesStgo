package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    EmpleadoRepository empleadoRepository;

    @GetMapping
    public String agregarEmpleadosPorDefecto(){
        // Agregar empleados
        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setRut("20.675.871-6");
        empleado.setApellidos("CATALAN VALENZUELA");
        empleado.setNombres("DANIEL ESTEBAN");
        empleado.setFecha_nac("2001/01/25");
        empleado.setFecha_in("2019/04/14");
        empleado.setCategoria(1);
        empleadoRepository.save(empleado);
        System.out.println("Empleado agregado correctamente");
        return "index"; // index.html
    }
}
