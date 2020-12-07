package dominio.compra;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.Validate;

import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Compra {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
	private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
	
	@OneToOne
	private Detalle detalle = new Detalle();
	
	@ManyToOne
	private Proveedor proveedor;
	
	@ManyToOne
	private MedioPago medioPago;
	
	@OneToOne
	private DocumentoComercial documentoComercial;
	
	private LocalDate fecha;
	
	@ManyToMany
	private List<Usuario> usuariosRevisores = new ArrayList<>();
	
	@ElementCollection
	private List<String> etiquetas;

	public double valor_total() {
		return detalle.getTotal();
	}

	public String getId() {
		return id.toString();
	}
	
	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}
	
	public List<Item> getDetalle() {
		return detalle.getItems();
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public String getMedioPago() {
		return medioPago.toString();
	}
	
	public String getDocumentoComercial() {
		return documentoComercial.toString();
	}
	
	public List<String> getEtiquetas() {
		return etiquetas;
	}
	
	public String mes() {
		Month mes = fecha.getMonth();
		String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
		
		String primeraLetra = nombre.substring(0,1);
		String mayuscula = primeraLetra.toUpperCase();
		String demasLetras = nombre.substring(1, nombre.length());
		nombre = mayuscula + demasLetras;

		return nombre;
	}
	
	public Compra(Proveedor proveedor, 
				  MedioPago medioPago, 
				  LocalDate fecha,
				  List<Presupuesto> presupuestos,
				  Detalle detalle,
				  List<Usuario> usuariosRevisores,
				  List<String> etiquetas) {
		Validate.notNull(proveedor, "proveedor faltante");
		Validate.notNull(medioPago, "medio de pago faltante");
		Validate.notNull(fecha, "fecha faltante");
		Validate.notNull(detalle, "detalle faltante");
		Validate.notNull(usuariosRevisores, "usuarios faltante");
		Validate.notNull(etiquetas, "lista de etiquetas faltante");
		// Preconditions.validateNotNull(entidad,"entidad faltante");
		this.proveedor = proveedor;
		this.medioPago = medioPago;
		this.fecha = fecha;
		this.detalle = detalle;
		this.usuariosRevisores = usuariosRevisores;
		this.presupuestos = presupuestos;
		this.etiquetas = etiquetas;
		// this.entidad = entidad;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}
	
	public boolean esDelMes(String mes) {
		return this.mes().equals(mes);
	}
	
	public void agregarEtiqueta(String etiqueta) {
		etiquetas.add(etiqueta);
	}
	
	public void quitarEtiqueta(String etiqueta) {
		etiquetas.remove(etiqueta);
	}

	public boolean tieneEtiqueta(String etiqueta) {
		return etiquetas.contains(etiqueta);
	}
	
}
