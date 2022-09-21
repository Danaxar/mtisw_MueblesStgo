package com.mtisw1.mueblesStgo.services;

import com.mtisw1.mueblesStgo.entities.JustificativoEntity;
import com.mtisw1.mueblesStgo.repositories.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class JustificativoService{
    @Autowired
    JustificativoRepository justificativoRepository;

    public ArrayList<JustificativoEntity> listAllJustificativo(){
        return (ArrayList<JustificativoEntity>) justificativoRepository.findAll();
    }

    public JustificativoEntity addJustificativo(JustificativoEntity justificativo){
        return justificativoRepository.save(justificativo);
    }

    public boolean existeJustificativo(String fecha, String rut){
        ArrayList<JustificativoEntity> justificativo = justificativoRepository.findByFechaAndRut(fecha, rut);
        if(justificativo.size() == 1){
            return true;
        }else{
            return false;
        }
    }
}
