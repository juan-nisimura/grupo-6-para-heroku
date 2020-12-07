package dominio.entidad;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Compra;

@Entity
public class CategoriaDefault extends Categoria {
	
	boolean bloquearNuevasCompras = false;
	boolean bloquearAgregarEntidadesBase = false;
    boolean bloquearFormarParteEntidadJuridica = false;
	double egresosMaximos;
	
	public CategoriaDefault() {
		
	}
	
	public CategoriaDefault(String nombre, boolean bloquearNuevasCompras, boolean bloquearAgregarEntidadesBase,
			boolean bloquearFormarParteEntidadJuridica, double egresosMaximos) {
		this.nombre = nombre;
		this.bloquearNuevasCompras = bloquearNuevasCompras;
		this.bloquearAgregarEntidadesBase = bloquearAgregarEntidadesBase;
		this.bloquearFormarParteEntidadJuridica = bloquearFormarParteEntidadJuridica;
		this.egresosMaximos = egresosMaximos;
	}

    public void validarAgregarCompra(Entidad entidad, Compra unaCompra) {
        if(bloquearNuevasCompras && entidad.egresosSuperan(egresosMaximos)){
            throw new RuntimeException("La entidad ha superado el limite de "+egresosMaximos+
                                        " egresos totales. No se puede agregar la compra");
        }
    }

	public void setBloquearNuevosEgresos(boolean bloquearNuevasCompras) {
		this.bloquearNuevasCompras = bloquearNuevasCompras;
	}

	public void setEgresosMaximos(double egresosMaximos) {
		this.egresosMaximos = egresosMaximos;
    }

	public void setBloquarAgregarEntidadesBase(boolean bloquearAgregarEntidadesBase) {
		this.bloquearAgregarEntidadesBase = bloquearAgregarEntidadesBase;
	} 

	public void setBloquearFormarParteEntidadJuridica(boolean bloquearFormarParteEntidadJuridica) {
		this.bloquearFormarParteEntidadJuridica = bloquearFormarParteEntidadJuridica;
    }

    public void validarAgregarEntidadBase(EntidadJuridica entidad, EntidadBase entidadBase){
        if(!entidad.puedeAgregarEntidadesBase()){
            throw new RuntimeException("La categoria de la entidad no permite agregar entidades base");
        }
        if(!entidadBase.puedeAgregarseAEntidadJuridica()){
            throw new RuntimeException("La categoria de la entidad base no permite añadirla a una entidad juridica");
        }
    }

    public boolean puedeAgregarseAEntidadJuridica() {
        return !bloquearFormarParteEntidadJuridica;
    }

    public boolean puedeAgregarEntidadesBase() {
		return !bloquearAgregarEntidadesBase;
	}
    
    public String getBloquearNuevasCompras(){
    	return this.toString(bloquearNuevasCompras);
    }
    
    public String getBloquearAgregarEntidadesBase(){
    	return toString(bloquearAgregarEntidadesBase);
    }
    
    public String getBloquearFormarParteEntidadJuridica(){
    	return toString(bloquearFormarParteEntidadJuridica);
    }
    
    public String getEgresosMaximos(){
    	return String.valueOf(egresosMaximos);
    }
    
    public String toString(boolean bool) {
    	if(bool)	return "True";
    	return "False";
    }
    
    public String getUrlView() {
    	return "/categorias/categorias_default/" + id.toString();
    }
    
    public String getUrlDelete() {
    	return "/categorias/" + id.toString() + "/delete";
    }
    
    public String getUrlEditar() {
    	return "/categorias/categorias_default/" + id.toString() + "/editar";
    }
}
