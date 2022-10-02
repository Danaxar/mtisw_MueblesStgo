package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.entities.Sueldo;
import com.mtisw1.mueblesStgo.services.SueldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.crypto.Data;
import java.util.ArrayList;

@Controller
@RequestMapping("/sueldo")
public class SueldoController {
    @Autowired
    SueldoService sueldoService;
    @Autowired
    DataController dataController;

    // Mostrar tabla
    @GetMapping("/planilla")
    public String mostrarPlanilla(Model model){
        ArrayList<Sueldo> sueldos = sueldoService.listarTodos();
        model.addAttribute("sueldos", sueldos);
        return "sueldos";  // Sueldos.html
    }

    // Activar calculo de sueldos
    @GetMapping("/calcular")
    public String calcularSueldos(){
        dataController.leerTxt();
        sueldoService.calcularSueldos();  // Los carga a la base de datos
        return "redirect:/sueldo/planilla";
    }
}
