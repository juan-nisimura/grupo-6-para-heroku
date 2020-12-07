package dominio.presupuestos;


import java.util.*;
import dominio.compra.*;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

@Entity
public class Detalle {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToMany
    List<Item> items = new ArrayList<Item>();
    
	String moneda;
	
	public List<Item> getItems() {
		return items;
	}
	
    public double getTotal() {
        return items.stream().mapToDouble(item -> item.getValorTotal()).sum();
    }

    public boolean esIgualA(Detalle detalle) {
        return this.estaIncluidoEn(detalle) && detalle.estaIncluidoEn(this);
    }

    public boolean estaIncluidoEn(Detalle detalle){
        return items.stream().allMatch(unItem -> detalle.tieneItem(unItem));
    }

    public boolean tieneItem(Item otroItem) {
        return items.stream().anyMatch(unItem -> unItem.esIgualA(otroItem));
    }

	public Detalle agregarItem(Item unItem) {
		items.add(unItem);
		return this;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public void convertirMoneda(String nueva_moneda) {
		if(moneda != nueva_moneda) {
			APImercado mercado = new APImercado();
			items.stream().forEach( i -> i.valorUnitario = mercado.convertirMoneda(i.valorUnitario, moneda, nueva_moneda));
			this.moneda = nueva_moneda;
		}
	}
	
    public long getId(){
    	  return this.id;
      } 
	
}
