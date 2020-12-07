package dominio.compra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.Validate;

import dominio.presupuestos.CriterioDeSeleccionPresupuesto;

@Entity
public class MedioPago {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private TipoPago tipoPago;
	private String identificador;

	public MedioPago(TipoPago tipoPago, String identificador) {
		Validate.notNull(tipoPago, "tipo pago faltante");
		Validate.notNull(identificador, "identificador faltante");
		this.tipoPago = tipoPago;
		this.identificador = identificador;
	}
	
	public MedioPago() { //rompe hibernate sin esto al crear item en la app
	}
	
    public long getId(){
    	  return this.id;
      } 
	
    public void setTipoPago(Long id_tipo_pago) {
    	this.tipoPago = null;
    	if(id_tipo_pago == 0)
    	this.tipoPago = TipoPago.TARJETA_CREDITO;
    	if(id_tipo_pago == 1) 
    	this.tipoPago = TipoPago.TARJETA_DEBITO;
    	if(id_tipo_pago == 2)
    	this.tipoPago = TipoPago.EFECTIVO;
    	if(id_tipo_pago == 3)
    	this.tipoPago = TipoPago.CAJERO_AUTOMATICO;
    	if(id_tipo_pago == 4)
    	this.tipoPago = TipoPago.DINERO_CUENTA;
    }
}
