package dominio.presupuestos;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.*;
import dominio.entidad.Entidad;
import dominio.usuario.*;
import repositorios.RepositorioUsuarios;

@Entity
public class CompraPendiente {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
    private List<Presupuesto> presupuestos = new ArrayList<>();
	
	@OneToOne(cascade = {CascadeType.ALL})
    private Detalle detalle = new Detalle();
    
	@ManyToOne(optional = true, cascade = {CascadeType.ALL})
    private Proveedor proveedor;
    
	private CriterioDeSeleccionPresupuesto criterioDeSeleccion = CriterioDeSeleccionPresupuesto.SinCriterioDeSeleccion;
	
    private int cantidadPresupuestosRequeridos = 0;
    private LocalDate fecha;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    private MedioPago medioPago;
    
    @ManyToMany
    private List<Usuario> usuariosRevisores = new ArrayList<>();
    
    @ManyToOne(optional = true,cascade = {CascadeType.ALL})
    private Entidad entidad;
    
    @ElementCollection
    private List<String> etiquetas = new ArrayList<String>();
    
    public CompraPendiente() {

    }
    
    public void setCantidadPresupuestosRequeridos(int unaCantidad) {
        cantidadPresupuestosRequeridos = unaCantidad;
    }
    
    public long getId(){
  	  return this.id;
    }
    
    public List<Presupuesto> getPresupuestos(){
    	  return this.presupuestos;
      }
    
    public String getFecha(){
    	if(fecha == null)
    		return "NULL";
    		else
    	  return this.fecha.toString();//format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    } 
    
    public Detalle getDetalle(){
    	  return this.detalle;
    }
    
    public Proveedor getProveedor(){
    	  return this.proveedor;
    }
    
    public MedioPago getMediopago(){//si se pone mas de una mayuscula hibernate no lo detecta
  	  return this.medioPago;
    } 
    
    public Entidad getEntidad(){
    	  return this.entidad;
    }   
    
    public long getCantidadpresupactuales(){ //si se pone mas de una mayuscula hibernate no lo detecta
  	  return this.presupuestos.size();
    }
    
    public int getCantidadpresuprequeridos(){ //si se pone mas de una mayuscula hibernate no lo detecta
    	  return this.cantidadPresupuestosRequeridos;
      }
    
    public String getCriterioseleccion(){ //si se pone mas de una mayuscula hibernate no lo detecta
    	  return this.criterioDeSeleccion.toString();
      } 
    
    public void setCriterioSeleccion(Long id_criterio) {
    	if(id_criterio == 0)
    	this.criterioDeSeleccion = CriterioDeSeleccionPresupuesto.ElUsuarioOlvidoElegirCriterio;
    	if(id_criterio == 1)
    	this.criterioDeSeleccion = CriterioDeSeleccionPresupuesto.SinCriterioDeSeleccion;
    	if(id_criterio == 2) 
    	this.criterioDeSeleccion = CriterioDeSeleccionPresupuesto.PresupuestoMasBarato;
    	if(id_criterio == 3)
    	this.criterioDeSeleccion = CriterioDeSeleccionPresupuesto.PresupuestoMasCaro;
    	if(id_criterio == 4)
    	this.criterioDeSeleccion = CriterioDeSeleccionPresupuesto.LoDejoASuCriterio;
    }
    
    public CompraPendiente setCriterioDeSeleccion(CriterioDeSeleccionPresupuesto unCriterio) { //TODO quedarse con este y borrar el de arriba
    	this.criterioDeSeleccion = unCriterio;
    	return this;
    }
    
    public void setMedioPago(MedioPago medioPago) {
    	this.medioPago = medioPago;
    }
    
    public void setFecha(LocalDate fecha) {
    	this.fecha = fecha;
    }
    
    public void setDetalle(Detalle detalle) {
    	this.detalle = detalle;
    }
    
    public CompraPendiente setProveedor(Proveedor proveedor) {
    	this.proveedor = proveedor;
    	return this;
    }

    public CompraPendiente agregarPresupuesto(Presupuesto unPresupuesto) {
        presupuestos.add(unPresupuesto);
        return this;
    }

    public boolean verificarCantidadPresupuestos() {
    	return validarCondicion(presupuestos.size() == cantidadPresupuestosRequeridos,
    			"La cantidad de presupuestos cargada es incorrecta. Se necesitan " + 
    			cantidadPresupuestosRequeridos + " y se han cargado " + presupuestos.size());
    }
    
    public boolean verificarDetallePresupuesto() {
    	return validarCondicion(presupuestoProveedorSeleccionado().tieneMismoDetalle(detalle),
    			"El presupuesto del proveedor seleccionado no coincide con el detalle de la compra");
    }
    
    public boolean verificarCriterioDeSeleccion(){
    	return validarCondicion(criterioDeSeleccion.verificar(presupuestos, presupuestoProveedorSeleccionado()),
    									"El presupuesto del proveedor elegido no es el m�s barato");
    }
    
    public boolean validarCondicion(boolean condicion, String mensaje) {
    	if(!condicion) {
    		enviarMensajeRevisores(mensaje+" para la compra "+this.getId());
    		return false;
    	}
    	return true;
    }
    
    public boolean verificarParametrosNotNull() {
    	return validarCondicion(proveedor != null,"proveedor faltante") &&
    	validarCondicion(medioPago != null, "medio de pago faltante") &&
    	validarCondicion(fecha != null, "fecha faltante") &&
		validarCondicion(entidad != null,"entidad faltante");
    }
    
    public Presupuesto presupuestoProveedorSeleccionado() {
    	return presupuestos.stream().filter(presupuesto -> presupuesto.proveedorEs(proveedor)).findFirst().get();
    }
    
    public CompraPendiente agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }
    
    public void agregarUsuarioRevisor(Usuario unUsuario) {
    	usuariosRevisores.add(unUsuario);
    }
    
    public void quitarUsuarioRevisor(Usuario unUsuario) {
    	usuariosRevisores.remove(unUsuario);
    }
    
    public void enviarMensajeRevisores(String texto) {
    	Mensaje unMensaje = new Mensaje(this, texto);
    	//usuariosRevisores.stream().forEach(unUsuario -> unUsuario.recibirMensaje(unMensaje)); 
    	//TODO borrar lo de abajo y quedarse con esta linea comentada
    	
    	Usuario usuario = RepositorioUsuarios.getInstance().getUsuario("pepe");
		
    	//transaction ya activa de antes de entrar al metodo
    	EntityManager em = PerThreadEntityManagers.getEntityManager();
    	em.persist(unMensaje);
		usuario.bandejaDeEntrada.agregarMensaje(unMensaje);
		
    	
    }
    
    public void setEntidad(Entidad entidad) {
    	this.entidad = entidad;
    }
    
    public boolean verificarQueEsValida() {
    	return verificarParametrosNotNull() &&
                verificarCantidadPresupuestos() &&
    			verificarDetallePresupuesto() &&
    			verificarCriterioDeSeleccion();
    }
    
    public void validarCompra() {
    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa se intenta validar la compra "+this.getId());
	    if(verificarQueEsValida()) {
			Compra compra = new Compra(proveedor, medioPago, fecha, presupuestos, detalle, usuariosRevisores, etiquetas);
			entidad.agregarCompra(compra);
			enviarMensajeRevisores("La compra "+this.getId()+" fue validada.");
		}

    }
    
}