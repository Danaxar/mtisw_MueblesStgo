package com.mtisw1.mueblesStgo.services;

import com.mtisw1.mueblesStgo.entities.DataEntity;
import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.entities.Sueldo;
import com.mtisw1.mueblesStgo.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;

    // Métodos
    public String leerTxt(String direccion){
        String texto = "";
        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            System.out.println("Archivo leido:\n");
            while((bfRead = bf.readLine()) != null){
                DataEntity dataEntity = new DataEntity();
                dataEntity.setFecha(bfRead.split(";")[0]);
                dataEntity.setHora(bfRead.split(";")[1]);
                dataEntity.setRut(bfRead.split(";")[2]);
                dataRepository.save(dataEntity);   // Guardar en la base de datos
                System.out.println(bfRead);
                temp = temp + bfRead;
            }
            texto = temp;
        }catch (Exception e){System.out.println("No se ha podido leer el archivo");}
        return texto;
    }

    public ArrayList<DataEntity> leerBdByRut(String rut){
        return (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRut(rut);
    }

    public ArrayList<DataEntity> leerBdByRutAndFecha(String rut, String fecha){
        return (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRutAndFecha(rut, fecha);
    }

    // Retorna true en caso de que el empleado asistió a trabajar dicho día
    public boolean asistioEmpleadoDia(String rut, String fecha){
        ArrayList<DataEntity> obj = (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRutAndFecha(rut, fecha);
        if(obj.size() == 2){
            return true;
        }
        return false;
    }

    public boolean hizoHorasExtra(EmpleadoEntity empleado, String fecha){
        ArrayList<DataEntity> obj = (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRutAndFecha(empleado.getRut(), fecha);
        String hora1 = obj.get(0).getHora();
        String hora2 = obj.get(1).getHora();
        // 18:00 - 08:00 = 10 hrs = 10 * 60 minutos = 600 minutos
        int tiempoDiff = CalculosService.tiempoDiffToMinutos(hora2, hora1);  // Aqui está el error
        if(tiempoDiff > 600){
            return true;
        }else{
            return false;
        }
    }


}
