package dominio.entidad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Compra;
import repositorios.RepositorioCategorias;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Categoria {
	@Id
	@GeneratedValue
	protected Long id;
	protected String nombre;
	public abstract void setBloquearNuevosEgresos(boolean bloquearNuevasCompras);
	public abstract void setEgresosMaximos(double egresosMaximos) ;
	public abstract void setBloquarAgregarEntidadesBase(boolean bloquearAgregarEntidadesBase) ;
	public abstract void setBloquearFormarParteEntidadJuridica(boolean bloquearFormarParteEntidadJuridica);
	public abstract void validarAgregarCompra(Entidad entidad, Compra unaCompra);
    public abstract void validarAgregarEntidadBase(EntidadJuridica entidad, EntidadBase entidadBase);
    public abstract boolean puedeAgregarseAEntidadJuridica();
    public abstract boolean puedeAgregarEntidadesBase();
    
    public List<Entidad> getEntidades() {
    	return RepositorioCategorias.getInstance().getEntidades(id);
    }
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    public String getId() {
    	return id.toString();
    }
    
    public String getNombre() {
    	return nombre;
    }
    
	protected abstract String getUrlView();
}