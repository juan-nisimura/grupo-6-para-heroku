package dominio.presupuestos;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import dominio.compra.*;

@Entity
public class Presupuesto {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
    private Proveedor proveedor;
	
	@OneToOne
    private Detalle detalle = new Detalle();
    
	@OneToOne(optional = true)
	private DocumentoComercial documentoComercial;

    public Presupuesto(CompraPendiente compraPendiente, Proveedor proveedor) {
        compraPendiente.agregarPresupuesto(this);
        this.proveedor = proveedor;
    }
    
	public Presupuesto() { //rompe hibernate sin esto al crear item en la app
	}
    
    public void setProveedor(Proveedor proveedor) {
    	this.proveedor = proveedor;
    }
    
    public void setDetalle(Detalle detalle) {
    	this.detalle = detalle;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
    	this.documentoComercial = documentoComercial;
    }
    
    public Presupuesto agregarItem(Item unItem) {
    	detalle.agregarItem(unItem);
    	return this;
    }
    
    public Proveedor getProveedor() {
        return this.proveedor;
    }
    
    public Detalle getDetalle() {
        return this.detalle;
    }
    
    public DocumentoComercial getDoccomercial() { //si se pone mas de una mayuscula hibernate no lo detecta
        return this.documentoComercial;
    }

    public double getTotal() {
        return detalle.getTotal();
    }
    
    public boolean proveedorEs(Proveedor unProveedor) {
    	return proveedor == unProveedor;
    }
    public boolean tieneMismoDetalle(Detalle unDetalle) {
        return this.detalle.esIgualA(unDetalle);
    }
    
    public long getId(){
    	  return this.id;
      }
}