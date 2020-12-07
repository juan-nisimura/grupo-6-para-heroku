package dominio.entidad;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrganizacionSocial extends EntidadJuridica {

	public OrganizacionSocial() {
		
	}
	
	public OrganizacionSocial(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, List<EntidadBase> entidades) {
		super(razonSocial, nombreFicticio, cuit, direccionPostal, entidades);
	}
	
	public String getTipo() {
		return "Organizacion Social";
	}
	
	@Override
	public String getUrlView() {
		return "/entidades/organizaciones_sociales/" + id.toString() + "/";
	}
}