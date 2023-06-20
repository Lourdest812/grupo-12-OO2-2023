package com.TpObjetos2.TpGrupo12.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.TpObjetos2.TpGrupo12.entities.Dispositivo;
import com.TpObjetos2.TpGrupo12.entities.SensorEstacionamiento;
import com.TpObjetos2.TpGrupo12.models.DispositivoModel;
import com.TpObjetos2.TpGrupo12.models.SensorEstacionamientoModel;

public interface ISensorEstacionamientoService {
	public List<SensorEstacionamiento> getAll();

    public SensorEstacionamientoModel insertOrUpdate(SensorEstacionamientoModel estacionamientoModel);
    
    public boolean remove(int id);
    
    public SensorEstacionamiento findByid(int id);
    
    @Query("SELECT p.estadoLibre FROM Plazas p")
    public List<Boolean> getPlazas();

	DispositivoModel agregarMedicion(Dispositivo dispositivoModel, LocalDateTime fecha, boolean estadoLibre);

	DispositivoModel insertOrUpdateEst(Dispositivo dispositivoModel);
	
	public List<SensorEstacionamiento> traerstacionamientosActivos();
	
	public SensorEstacionamiento crearEstacionamientoConPlazas();

}
