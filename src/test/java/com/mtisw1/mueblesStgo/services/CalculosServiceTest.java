package com.mtisw1.mueblesStgo.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculosServiceTest {

    @Test
    void fechaToArray() {
        Integer[] arrayTest = {2022, 1, 5};
        Assertions.assertArrayEquals(arrayTest, CalculosService.fechaToArray("2022/01/05"));
    }

    @Test
    void fechaBarraLateralToGuion() {
        String test = "2022/09/23";
        String result = CalculosService.fechaBarraLateralToGuion(test);
        Assertions.assertEquals("2022-09-23", result);
    }

    @Test
    void horaToArray(){
        String horaInicial = "08:15";
        Integer[] horaFinal = {8,15};
        Integer[] result = CalculosService.horaToArray(horaInicial);
        Assertions.assertArrayEquals(horaFinal, result);
    }

    @Test
    void arrayToHora() {
        Integer[] array = {8,15};
        String result = CalculosService.ArrayToHora(array);
        Assertions.assertEquals("08:15", result);
    }

    @Test
    void arrayToFecha(){
        Integer[] fecha = {2022,9,23};
        String result = CalculosService.ArrayToFecha(fecha);
        Assertions.assertEquals("2022/09/23", result);
    }

    @Test
    void calcularYearsOfService() {
        Integer result = CalculosService.calcularYearsOfService("2022/09/23", "2012/09/23");
        Assertions.assertEquals(10, result);
    }

    @Test
    void tiempoDiffToMinutos() {
        int result = CalculosService.tiempoDiffToMinutos("18:00","08:00");
        Assertions.assertEquals(600, result);
    }

    @Test
    void copiarArray() {
        Integer[] array = {1,2,3};
        Assertions.assertArrayEquals(array, CalculosService.copiarArray(array));
    }
}