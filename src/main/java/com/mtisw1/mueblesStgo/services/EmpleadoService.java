package com.mtisw1.mueblesStgo.services;

import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public ArrayList<EmpleadoEntity> obtenerEmpleados(){
        return (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
    }

    public EmpleadoEntity obtenerPorRut(String rut){
        return empleadoRepository.findByRut(rut);
    }
}