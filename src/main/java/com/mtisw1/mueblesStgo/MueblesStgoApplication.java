package com.mtisw1.mueblesStgo;

import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MueblesStgoApplication {
	@Autowired
	EmpleadoRepository empleadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(MueblesStgoApplication.class, args);
		System.out.println("Running.");
	}

}
