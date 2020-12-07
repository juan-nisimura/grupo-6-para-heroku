package dominio.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import repositorios.RepositorioCategorias;
import repositorios.RepositorioEntidades;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EntidadJuridica extends Entidad {

	@OneToMany
	private List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>(); // puede ser vacia
	
	private String razonSocial;
	private String cuit;
	private String direccionPostal;
	private String codigoInscripcion;
	
	//@OneToMany
	//private List<EntidadBase> entidades_usadas = new ArrayList<EntidadBase>();
	
	public String getRazonSocial() {
		return razonSocial;
	}
	
	public String getCuit() {
		return cuit;
	}
	
	public String getDireccionPostal() {
		return direccionPostal;
	}
	
	public String getCodigoInscripcion() {
		return codigoInscripcion;
	}
	
	public EntidadJuridica() {
		
	}
	
	public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, List<EntidadBase> entidades) {
		Validate.notNull(razonSocial, "razon social faltante");
		Validate.notNull(nombreFicticio, "nombre ficticio faltante");
		Validate.notNull(cuit, "cuit faltante");
		Validate.notNull(direccionPostal, "direccion postal faltante");
		this.razonSocial = razonSocial;
		this.nombreFicticio = nombreFicticio;
		this.cuit = cuit;
		this.direccionPostal = direccionPostal;
		this.entidadesBase = entidadesBase;
		//this.entidadesBase = this.tomarEntidadesDisponibles(entidades);
		//this.entidadesBase.stream().forEach(entidad -> this.entidades_usadas.add(entidad));
	}

	public void setCodigoInscripcion(String codigoInscripcion) {
		this.codigoInscripcion = codigoInscripcion;
	}
	
	/*
	public List<EntidadBase> tomarEntidadesDisponibles(List<EntidadBase> entidades){
		return entidades.stream().filter(entidad->!entidades_usadas.contains(entidad)).collect(Collectors.toList());
	}*/

	public void agregarEntidadBase(EntidadBase entidadBase) {
		categorias.stream().forEach(categoria -> categoria.validarAgregarEntidadBase(this, entidadBase));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entidadesBase.add(entidadBase);
		entidadBase.setTieneEntidadJuridica(true);
		transaction.commit();
	}

	public List<EntidadBase> getEntidadesBase() {
		return entidadesBase;
	}

	public boolean puedeAgregarEntidadesBase() {
		return categorias.stream().allMatch(categoria -> categoria.puedeAgregarEntidadesBase());
	}

	public void quitarEntidadBase(EntidadBase entidadBase) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entidadesBase.remove(entidadBase);
		entidadBase.setTieneEntidadJuridica(false);
		transaction.commit();
	}
	
	public String getUrlEditarEntidadesBase() {
		return this.getUrlView() + "editar_entidades_base";
	}
	
	public String getTablaEntidadesBase() {
    	tabla = "<table>" + 
    			"    	<tr>" + 
    			"            <th> Id </th>" + 
    			"            <th> Nombre </th>" + 
    			"            <th></th>" + 
    			"        </tr>";
    	entidadesBase.stream().forEach((entidadBase) -> {tabla = tabla + 
			    			"<tr>" + 
			    			"   <td> " + entidadBase.getId() + "</td>" + 
			    			"   <td> " + entidadBase.getNombre() + "</td>" + 
			    			"   <td><a href = " + entidadBase.getUrlView() + ">Ver</a></th>" +
			    			"</tr>";});
    	return tabla;
    }
    
    public String getTablaEntidadesBaseEditar() {
    	tabla = "<table>" + 
    			"    	<tr>" + 
    			"            <th> Id </th>" + 
    			"            <th> Nombre </th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"            <th></th>" + 
    			"        </tr>";
    	
    	entidadesBase.stream().forEach((entidadBase) -> {tabla = tabla + 
    			"<tr>" + 
    			"   <td> " + entidadBase.getId() + "</td>" + 
    			"   <td> " + entidadBase.getNombre() + "</td>" + 
    			"   <td><a href = " + entidadBase.getUrlView() + ">Ver</a></th>" +
				"<td><a href = " + this.getUrlView() + "quitar_entidad_base/" + entidadBase.getId() + ">Quitar Entidad Base</a></th>" +
		    	"</tr>";
    			});
    	if(this.puedeAgregarEntidadesBase()) {
    		List<EntidadBase> todasLasEntidadesBase = RepositorioEntidades
        			.getInstance().getEntidadesBase().stream()
        			.filter(entidadBase -> !entidadesBase.contains(entidadBase))
        			.collect(Collectors.toList());    	
        	todasLasEntidadesBase.stream().forEach((entidadBase) -> {tabla = tabla + 
    			"<tr>" + 
    			"   <td> " + entidadBase.getId() + "</td>" + 
    			"   <td> " + entidadBase.getNombre() + "</td>" + 
    			"   <td><a href = " + entidadBase.getUrlView() + ">Ver</a></th>";
    			if(entidadBase.puedeAgregarseAEntidadJuridica())
    				tabla = tabla + "<td><a href = " + this.getUrlView() + "agregar_entidad_base/" + entidadBase.getId() + ">Agregar Entidad Base</a></th></tr>";
    			else
    				tabla = tabla + "<td>No se puede agregar la Entidad Base</th></tr>";
    			});
        	return tabla + "<table>";
    	}
    	else
    		return tabla + "<table>" + "</br>La entidad no puede agregar entidades base";	
    }
}
