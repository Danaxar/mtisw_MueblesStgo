package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/listar")
    public String listar(Model model) {
        ArrayList<EmpleadoEntity> empleados =empleadoService.obtenerEmpleados();
        model.addAttribute("empleados",empleados);
        System.out.println("Tabla empleados:");
        System.out.println("Id, Apellidos, Categoria, Fecha_in, Fecha_nac, Nombres, Rut");
        for(int i = 0; i < empleados.size(); i++){
            System.out.println(
                    empleados.get(i).getId().toString() + " " +
                    empleados.get(i).getApellidos().toString() + " " +
                    empleados.get(i).getCategoria().toString() + " " +
                    empleados.get(i).getFecha_in().toString() + " " +
                    empleados.get(i).getFecha_nac().toString() + " " +
                    empleados.get(i).getNombres().toString() + " " +
                    empleados.get(i).getRut().toString()
            );
        }
        return "index";
    }

}