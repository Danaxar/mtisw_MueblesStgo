package com.mtisw1.mueblesStgo.services;
import com.mtisw1.mueblesStgo.entities.AutorizacionEntity;
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
}
