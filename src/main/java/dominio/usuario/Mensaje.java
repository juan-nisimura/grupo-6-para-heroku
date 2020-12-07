package dominio.usuario;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import dominio.presupuestos.*;

@Entity
public class Mensaje {
	
	@Id
	@GeneratedValue
	private Long id;
	/*
	@ManyToOne
	CompraPendiente unaCompra;
	TODO volver a incluir, se hace un lio para borrar las compras que son referenciadas por mensajes*/
	
	String unMensaje;
	
	public Mensaje() {}
	
	public Mensaje(CompraPendiente unaCompra, String unMensaje){
		//this.unaCompra = unaCompra;TODO incluir
		this.unMensaje = unMensaje;
	}
	
	public void enviarseBandejaMensajes(BandejaDeMensajes unaBandeja) {
		unaBandeja.agregarMensaje(this);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDescripcion() {
		return unMensaje;
	}
}