package dominio.entidad;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

@Entity
public class Empresa extends EntidadJuridica {

	@Enumerated(EnumType.STRING)
	private TipoEmpresa tipoEmpresa;

	public Empresa() {
		
	}
	
	public Empresa(String razonSocial, String nombreFicticio, String cuit, String direccionPostal,
			TipoEmpresa tipoEmpresa, List<EntidadBase> entidades) {
		super(razonSocial, nombreFicticio, cuit, direccionPostal, entidades);
		Validate.notNull(tipoEmpresa, "tipo empresa faltante");
		this.tipoEmpresa = tipoEmpresa;
	}
	
	public String getTipo() {
		return "Empresa";
	}
	
	@Override
	public String getUrlView() {
		return "/entidades/empresas/" + id.toString() + "/";
	}
	
	public String getTipoEmpresa() {
		return tipoEmpresa.toString();
	}
}
