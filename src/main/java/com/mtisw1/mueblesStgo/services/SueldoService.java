package com.mtisw1.mueblesStgo.services;

import com.mtisw1.mueblesStgo.entities.CategoriaEntity;
import com.mtisw1.mueblesStgo.entities.DataEntity;
import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import com.mtisw1.mueblesStgo.entities.Sueldo;
import com.mtisw1.mueblesStgo.repositories.SueldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.lang.Integer;


@Service
public class SueldoService {
    @Autowired
    SueldoRepository sueldoRepository;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    DataService dataService;

    @Autowired
    JustificativoService justificativoService;

    @Autowired
    AutorizacionService autorizacionService;

    // Métodos
    public ArrayList<Sueldo> listarTodos(){  // Cuando los sueldos estén calculados se muestran con esto
        return (ArrayList<Sueldo>) sueldoRepository.findAll();
    }

    public Sueldo crearSueldo(EmpleadoEntity empleadoActual){
        Sueldo sueldo = new Sueldo();
        sueldo.setRut(empleadoActual.getRut());
        sueldo.setNombreEmpleado(empleadoActual.getApellidos() + " " + empleadoActual.getNombres());
        sueldo.setYearsOfService(CalculosService.calcularYearsOfService(
                        CalculosService.fecha_actual_str,
                        empleadoActual.getFecha_in()));
        sueldo.setSueldoFijoMensual(CategoriaEntity.sueldoXcategoria(empleadoActual.getCategoria()));
        sueldo.setBonoYearsOfService(
                this.calcularBonoYearsOfService(empleadoActual)
                * sueldo.getSueldoFijoMensual()
        );
        sueldo.setPagoHorasExtras(0.0);
        sueldo.setMontoDescuentos(0.0);
        sueldo.setSueldoBruto(0.0);
        sueldo.setCotizacionPrevisional(0.0);
        sueldo.setCotizacionSalud(0.0);
        return sueldo;
    }

    public double calcularBonoYearsOfService(EmpleadoEntity empleado){
        int years = CalculosService.calcularYearsOfService(CalculosService.fecha_actual_str, empleado.getFecha_in());
        if(years < 5){
            return 0.0;
        }else if(years >= 5 && years < 10){
            return 0.05;
        }else if(years >= 10 && years < 15){
            return 0.08;
        }else if(years >= 15 && years < 20){
            return 0.11;
        }else if(years >= 20 && years < 25){
            return 0.14;
        }else if(years >= 25){
            return 0.17;
        }
        System.out.println("Error calculando years of service");
        return -1;
    }

    public void calcularSueldos() {
        // Se deben guardar en la base de datos
        System.out.println("Calculando sueldos...");
        ArrayList<EmpleadoEntity> empleados = empleadoService.obtenerEmpleados();  // Obtener los empleados
        for (int i = 0; i < empleados.size(); i++) {  // Recorrer los empleados
            EmpleadoEntity empleadoActual = empleados.get(i);  // Obtener empleado i
            ArrayList<DataEntity> ingresos = dataService.leerBdByRut(empleadoActual.getRut());  // Obtener ingresos
            System.out.println("Registros de " + empleadoActual.getNombres() + " = " + Integer.toString(ingresos.size()));
            // It works
            Sueldo sueldo = crearSueldo(empleadoActual);  // Crear entidad sueldo (planilla)

            // Asistencias y Atrasos
            System.out.println("Revisando asistencias y atrasos...");
            Integer[] fechaDeTrabajo = CalculosService.copiarArray(CalculosService.fecha_actual_int);
            fechaDeTrabajo[1]--;  // Retroceder un mes = {2022, 8, 1};
            for (int j = 0; j < 30; j++) {  // Recorrer días del mes
                String fechaActual = CalculosService.ArrayToFecha(fechaDeTrabajo);
                // Verificar si el empleado asitió a trabajar
                if (!dataService.asistioEmpleadoDia(empleadoActual.getRut(), fechaActual)) {
                    // Si no fue a trabajar verificar si tiene justificativo
                    if (!justificativoService.existeJustificativo(fechaActual, empleadoActual.getRut())) {
                        // Si no tiene justificativo entonces se descuenta el 15% de su sueldo
                        agregarDescuento(sueldo, CalculosService.descuentoXinasistencia);
                    }
                }else{  // Si asistió a trabajar -> Verificar atraso
                    procesarHoraLlegada(empleadoActual, fechaActual, CalculosService.horaEntrada, sueldo);
                }
                fechaDeTrabajo[2]++;  // Ir al día siguiente
            }  // Llegado a este punto ya se han descontado los días de inasistencia sin justificativos
            // Falta calcular las horas extras

            // Hora extras
            System.out.println("Revisando horas extras...");
            Integer[] fechaDeHoraExtra = CalculosService.copiarArray(CalculosService.fecha_actual_int);  // Obtengo la fecha actual
            fechaDeHoraExtra[1] = fechaDeHoraExtra[1] - 1;  // Retrocedo 1 mes
            for (int j = 0; j < 30; j++) {  // Recorrer los 30 días del mes
                String fechaActual = CalculosService.ArrayToFecha(fechaDeHoraExtra);  // Convertir a string la fecha
                // Revisar si hizo horas extras ese día
                if (dataService.hizoHorasExtra(empleadoActual, fechaActual)) {
                    // Revisar si tiene autorizacion para hacer horas extras
                    System.out.println(empleadoActual.getNombres() + " ha hecho horas extras el dia " + fechaActual);
                    if (autorizacionService.existeAutorizacion(fechaActual, empleadoActual)) {
                        System.out.println("Realizando pago...");
                        // Agregar al monto de pago de horas extras
                        double montoHorasExtrasActual = sueldo.getPagoHorasExtras();  // get monto hr extra act
                        double pago = CategoriaEntity.horaExtraXcategoria(empleadoActual.getCategoria());
                        // Calculo el pago
                        sueldo.setPagoHorasExtras(montoHorasExtrasActual + pago);
                    }else{
                        System.out.println("\t pero no cuenta con autorizacion");
                    }
                }
                fechaDeHoraExtra[2]++;  // Aumentar un día
            }
            /* // Atrasos
            System.out.println("Revisando atrasos...");
            Integer[] fechaDeAtraso = CalculosService.copiarArray(CalculosService.fecha_actual_int);
            fechaDeAtraso[1] = fechaDeAtraso[1] - 1;  // Retroceder un mes
            for (int j = 0; j < 30; j++) {  // Analizar atrasos
                String fechaActual = CalculosService.ArrayToFecha(fechaDeAtraso);  // Convertir a string la fecha
                procesarHoraLlegada(empleadoActual, fechaActual, "08:00", sueldo);
                fechaDeAtraso[2]++;  // Aumentar un día
            }*/
            sueldoBruto(sueldo); // Calcular sueldo bruto
            sueldoFinal(sueldo); // Calcular sueldo final
            sueldoRepository.save(sueldo); // Guardar en la base de datos
        }
        System.out.println("Sueldos calculados exitosamente");
    }

    public void sueldoBruto(Sueldo sueldo){
        // Sueldo base - descuentos por atraso/falta + bonoYearsOfService + horas extras
        Integer sueldoBase = sueldo.getSueldoFijoMensual();
        double descuentos = sueldo.getMontoDescuentos();
        double bonoYearsOfService = sueldo.getBonoYearsOfService();
        double montoHorasExtras = sueldo.getPagoHorasExtras();
        double sueldoBruto = sueldoBase + bonoYearsOfService - descuentos + montoHorasExtras;
        sueldo.setSueldoBruto(sueldoBruto);
    }

    public void sueldoFinal(Sueldo sueldo){
        // Sueldo bruto - cotizaciones
        double sueldoBruto = sueldo.getSueldoBruto();
        double cotizacionPrevisional = sueldoBruto * CalculosService.cotizacionPrevisional;
        sueldo.setCotizacionPrevisional(cotizacionPrevisional);

        double cotizacionSalud = sueldoBruto * CalculosService.cotizacionSalud;
        sueldo.setCotizacionSalud(cotizacionSalud);

        Double sueldoFinal = sueldoBruto - (cotizacionPrevisional + cotizacionSalud);
        sueldo.setSueldoFinal(sueldoFinal.intValue());
    }

    public void procesarHoraLlegada(EmpleadoEntity empleado, String fecha, String hora_programada, Sueldo sueldo){
        // Aplica un descuento de ser necesario a un empleado a un día específico según una hora programada al objeto sueldo
        String rutConsultar = empleado.getRut();
        ArrayList<DataEntity> obj = (ArrayList<DataEntity>) dataService.leerBdByRutAndFecha(empleado.getRut(), fecha);  // No está encontrando la ocurrencia correcta
        DataEntity registro = obj.get(0); // Obtener el primer ingreso, el de la mañana
        int minutosAtraso = CalculosService.tiempoDiffToMinutos(registro.getHora(), hora_programada);
        if(minutosAtraso > 70){
            // Se considera inasistencia -> Descontar a no ser que tenga justificativo
            if(!justificativoService.existeJustificativo(fecha, empleado.getRut())){
                // Descontar ya que no tiene justificativo -15%
                agregarDescuento(sueldo, CalculosService.descuentoXinasistencia);
            }
        }else if(minutosAtraso > 45){ // - 6%
            agregarDescuento(sueldo, 0.06);
        }else if(minutosAtraso > 25){ // - 3%
            agregarDescuento(sueldo, 0.03);
        }else if(minutosAtraso > 10){ // - 1%
            agregarDescuento(sueldo, 0.01);
        }
    }

    public void agregarDescuento(Sueldo sueldo, double porcentaje){
        double montoDescuentoActual = sueldo.getMontoDescuentos();  // Obtener la cantidad actual
        double descuento = porcentaje * sueldo.getSueldoFijoMensual();
        sueldo.setMontoDescuentos(montoDescuentoActual + descuento);
    }
}
