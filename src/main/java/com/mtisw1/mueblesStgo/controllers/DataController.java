package com.mtisw1.mueblesStgo.controllers;

import com.mtisw1.mueblesStgo.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;


@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    DataService dataService;
    private String info;
    private boolean readed = false;  // Se inicializa en falso

    @GetMapping("/leertxt")
    public String leerTxt(){
        if(this.readed == false){
            this.info = dataService.leerTxt("uploadFiles/DATA.txt");
            this.readed = true;
        }else{
            System.out.println("El archivo ya ha sido leido");
        }
        return "index";
    }
}
