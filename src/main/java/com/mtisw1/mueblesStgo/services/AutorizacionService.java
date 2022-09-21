package com.mtisw1.mueblesStgo.services;
import com.mtisw1.mueblesStgo.entities.AutorizacionEntity;
import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.repositories.AutorizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class AutorizacionService {
    @Autowired
    AutorizacionRepository autorizacionRepository;

    public ArrayList<AutorizacionEntity> listAllAutorizacion(){
        return (ArrayList<AutorizacionEntity>) autorizacionRepository.findAll();
    }

    public AutorizacionEntity add(AutorizacionEntity autorizacion){
        return autorizacionRepository.save(autorizacion);
    }

    public boolean existeAutorizacion(String fecha, EmpleadoEntity empleado){
        String fechaFormateada = CalculosService.fechaBarraLateralToGuion(fecha);
        ArrayList<AutorizacionEntity> autorizacion = autorizacionRepository.findByFechaAndRut(fechaFormateada, empleado.getRut());
        if(autorizacion.size() == 1){
            return true;
        }else{
            return false;
        }
    }
}
