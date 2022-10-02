package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.entities.JustificativoEntity;
import com.mtisw1.mueblesStgo.services.JustificativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("justificativo")
public class JustificativoController {
    @Autowired
    private JustificativoService justificativoService;

    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<JustificativoEntity> justificativos = justificativoService.listAllJustificativo();
        model.addAttribute("justificativos", justificativos);
        return "index";
    }

    // Cuando se mapea a esta dirección permite cargar la vista del formulario con el objeto cargado
    @GetMapping("/formulario")
    public String mostrarForm(Model model){
        JustificativoEntity justificativo = new JustificativoEntity();
        model.addAttribute("justificativo", justificativo);
        return "justificativos";
    }

    @PostMapping(value = "/agregar")
    public String agregarJustificativo(@ModelAttribute("justificativo") JustificativoEntity justificativo){
        justificativoService.addJustificativo(justificativo);
        return "redirect:/justificativo/formulario";
    }
}
