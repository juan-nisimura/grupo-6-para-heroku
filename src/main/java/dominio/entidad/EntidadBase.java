package dominio.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.Validate;

@Entity
public class EntidadBase extends Entidad {

	private String descripcion;
	private boolean tieneEntidadJuridica;
	
	public EntidadBase() {
		
	}
	
	public void setTieneEntidadJuridica(boolean tieneEntidadJuridica) {
		this.tieneEntidadJuridica = tieneEntidadJuridica;
	}
	
	public EntidadBase(String nombreFicticio, String descripcion) {
		Validate.notNull(nombreFicticio, "nombre ficticio faltante");
		Validate.notNull(descripcion, "descripcion faltante");
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
		this.tieneEntidadJuridica = false;
	}

    public boolean puedeAgregarseAEntidadJuridica() {
        return !tieneEntidadJuridica && categorias.stream().allMatch(categoria -> categoria.puedeAgregarseAEntidadJuridica());
    }
    
	public String getTipo() {
		return "Base";
	}
	
	@Override
	public String getUrlView() {
		return "/entidades/entidades_base/" + id.toString() + "/";
	}
	
	public String getDescripcion() {
		return descripcion;
	}
}
