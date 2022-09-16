package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.entities.AutorizacionEntity;
import com.mtisw1.mueblesStgo.entities.JustificativoEntity;
import com.mtisw1.mueblesStgo.services.AutorizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autorizacion")
public class AutorizacionController {
    @Autowired
    AutorizacionService autorizacionService;

    // Cuando se mapea a esta dirección permite cargar la vista del formulario con el objeto cargado
    @GetMapping("/formulario")
    public String mostrarForm(Model model){
        AutorizacionEntity autorizacion = new AutorizacionEntity();
        model.addAttribute("autorizacion", autorizacion);
        return "/autorizaciones";
    }

    @PostMapping(value = "/agregar")
    public String agregarJustificativo(@ModelAttribute("autorizacion") AutorizacionEntity autorizacion){
        autorizacionService.add(autorizacion);
        return "redirect:/autorizacion/formulario";
    }
}
