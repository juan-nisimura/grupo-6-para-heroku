package dominio.compra;

import javax.persistence.*;

import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

@Entity
public class DireccionPostal {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Transient
	private APImercado mercado_libre;
	private String pais;
	private String provincia;
	private String ciudad;
	private String direccion;
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public void setCiudad(String id_ciudad) {
		this.ciudad = mercado_libre.obtenerCiudadDeID(id_ciudad);
	}
	
	public void setProvincia(String id_ciudad) {
		this.provincia = mercado_libre.obtenerProvinciaDeCiudad(id_ciudad);
	}
	
	public void setPais(String id_ciudad) {
		this.pais = mercado_libre.obtenerPaisDeCiudad(id_ciudad);
	}
	
	public JSONObject pais(String codigo_pais) {
		return mercado_libre.getInfoDePais(codigo_pais);
	}
	
	public String provincia(String provincia) {
		return mercado_libre.obtenerProvinciaDeZip(provincia);
	}
	
    public long getId(){
  	  return this.id;
    } 
	
	
	public void setterCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public void setterProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public void setterPais(String pais) {
		this.pais = pais;
	}
}
