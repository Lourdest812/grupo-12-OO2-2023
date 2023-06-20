package com.TpObjetos2.TpGrupo12.components;

import java.time.LocalDate;
import java.time.*;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.TpObjetos2.TpGrupo12.entities.Dispositivo;
import com.TpObjetos2.TpGrupo12.entities.Evento;
import com.TpObjetos2.TpGrupo12.entities.Medicion;
import com.TpObjetos2.TpGrupo12.entities.MedicionAlumbrado;
import com.TpObjetos2.TpGrupo12.entities.SensorAlumbrado;

import com.TpObjetos2.TpGrupo12.services.ISensorAlumbradoService;

@Component
public class FuncionesAlumbrado {
	
	@Autowired
    @Qualifier("sensorAlumbradoService")
    private ISensorAlumbradoService sensorAlumbradoService;
	
	
	//Alumbrado funcion que se va a ejecutar para buscar un dispositivo random y devolver su ultima medicion dependiendo de esta medicion se va a ejecutar un evento
		public MedicionAlumbrado traerUltimaMediacion() {
       
		MedicionAlumbrado medi; 
			
		List<SensorAlumbrado> dispositivos = sensorAlumbradoService.getAll();
        int id = (int) (Math.random()*dispositivos.size());
        
        //asigno el dispositivo a una variable dispositivo para poder consumir las mediciones
        Dispositivo buscar = sensorAlumbradoService.findByid(dispositivos.get(id).getId());
        List<Medicion> mediciones = buscar.getMediciones();
        
        // si las mediciones estan vacias creamos la primera y devolvemos null lo usamos como flag para cuando llamemos a la funcion
        if(mediciones.size() == 0)  
        {
        	
        	medi = null;
        	// lo mandamos a generar la primera medicion del dispositivo con la luz apagada en la proxima variable cuando se vea nuevamente la hora y el porcentaje de luz se decidira si prenderla
        	sensorAlumbradoService.agregarMedicion(buscar, LocalDateTime.now(), false, 20);
        	
        }else {
        	
        	//en caso de que ya tenga mediciones se va a devolver la ultima generada
        	 medi = (MedicionAlumbrado) mediciones.get(mediciones.size()-1);
        	 
        }
        return medi;
    }

	
	@Scheduled(fixedDelay=5000)
		public void mostarHola() {

			MedicionAlumbrado implementar = this.traerUltimaMediacion();
			//LocalDate fecha = 
			LocalTime horaInicio = LocalTime.of(23, 0);
			LocalTime horaFin = LocalTime.of(07, 0);
			
			LocalTime horaMedAhora = implementar.getFechaRegistro().toLocalTime();
			
			
			if(implementar == null) {
				
				System.out.println("\n No tiene mediciones , agregamos la primera :D ");
				
			}
			
			// registro el horario en que debería estar prendida la luz
			//agregar or para que verifique el la obcuridadPor
			if((horaMedAhora.isAfter(horaInicio) && horaMedAhora.isBefore(horaFin)) || (implementar.getOscuridadActualPor() > 70) ) 
			{
				//verifico si la luz esta apagada , si esta apagada la prendo y genero un evento
				if(implementar.isEstadoActual() == false) {
					
					implementar.setEstadoActual(true);
					
					//generar evento futuro
					Dispositivo dispo = implementar.getDispositivo();
					Evento agregar = new Evento("Encender Luz", implementar.getFechaRegistro(),dispo);
					sensorAlumbradoService.agregarEventos(dispo, agregar);
					 System.out.println("\n AGREWGAMOS EVENTO");
					
				}
						
					}
			else {
				
				
				if(implementar.isEstadoActual() == true) {
					
					implementar.setEstadoActual(false);
					
					Dispositivo dispo = implementar.getDispositivo();
					Evento agregar =new Evento("Apagar Luz", implementar.getFechaRegistro(),dispo);
					 sensorAlumbradoService.agregarEventos(dispo,agregar);
					 System.out.println("\n AGREWGAMOS EVENTO");
					
				}
			}
				
				
				
				LocalDateTime fechanueva = implementar.getFechaRegistro();
				
				fechanueva = fechanueva.plusHours(1);
				
				//generamos un double random de 1 a 100 para poner en la obscuridad de la hora siguiente
				double nuevaObscuridad = Math.random()*100+1;
				
				sensorAlumbradoService.agregarMedicion(implementar.getDispositivo(), fechanueva, implementar.isEstadoActual(), nuevaObscuridad);
				 System.out.println("\n AGREWGAMOS Medicion");
				
				
			};
			
			
			
			
			
			
		}

