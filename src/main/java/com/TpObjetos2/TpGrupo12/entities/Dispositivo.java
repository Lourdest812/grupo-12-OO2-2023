package com.TpObjetos2.TpGrupo12.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@Table(name="dispositivo")
@Inheritance(strategy = InheritanceType.JOINED)
public class Dispositivo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;  

    @Column(name="nombre")
    private String nombre;

    @Column(name="activo")

    protected boolean activo;
    
    @OneToMany(mappedBy="dispositivo")
    private List<Medicion> mediciones;
    
    @OneToMany(mappedBy="dispositivo")
    private List<Evento> eventos;

    protected void setIdDispositivo(int id) {
        this.id = id;    
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

	public Dispositivo(int id, String nombre, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}

	public Dispositivo(String nombre, boolean activo) {
		this.nombre = nombre;
		this.activo = activo;
	}

	public Dispositivo() {
	}
	
}

