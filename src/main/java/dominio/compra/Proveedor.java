package dominio.compra;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.Validate;

@Entity
public class Proveedor {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String razon_social;
	private String dni_cuil_cuit;
	
	@OneToOne(cascade = {CascadeType.ALL})
	DireccionPostal direccion_postal;

	public Proveedor() {
		
	}
	
    public long getId(){
    	  return this.id;
      } 
	
	public Proveedor(String razonSocial, String dni_cuil_cuit, DireccionPostal direccion_postal) {
		Validate.notNull(razonSocial, "razon social faltante");
		Validate.notNull(dni_cuil_cuit, "dni/cuil/cuit faltante");
		Validate.notNull(direccion_postal, "direccion postal faltante");
		this.razon_social = razonSocial;
		this.dni_cuil_cuit = dni_cuil_cuit;
		this.direccion_postal = direccion_postal;
	}
	
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public void setDni_cuil_cuit(String dni_cuil_cuit) {
		this.dni_cuil_cuit = dni_cuil_cuit;
	}
}
