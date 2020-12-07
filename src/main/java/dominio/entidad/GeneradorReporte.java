package dominio.entidad;


import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.Transient;

import dominio.compra.*;

public class GeneradorReporte {
		
	private Set<String> etiquetas = new HashSet<String>();
	
	private List<String> etiq = new ArrayList<String>();
	
	private String mesActual;
	private LocalDate fecha;
	 
	private HashMap<String, List<Compra>> categorias = new HashMap<String, List<Compra>>();
	
	public GeneradorReporte() {
		this.fecha = LocalDate.now();
		this.mesActual = this.mes();
	}
	
	public HashMap<String, List<Compra>> generarReporte(List<Compra> compras){
		return this.categorizar(this.sonDelMes(compras));
	}

	public HashMap<String, List<Compra>> categorizar(List<Compra> compras){
		this.convertir();
		for (int i=0; i<this.cantidadDeEtiquetas(); i++) {
			categorias.put(this.etiq(i), this.comprasDeEtiq(this.etiq(i), compras));
		}
		return categorias;
	}
	
	public void convertir() {
		etiq = etiquetas.stream().collect(Collectors.toList());
	}
	
	public String etiq(int indice) {
		return etiq.get(indice);
	}
	
	public List<Compra> comprasDeEtiq(String etiqueta, List<Compra> compras){
		return compras.stream().filter(compra -> compra.tieneEtiqueta(etiqueta)).collect(Collectors.toList());
	}
	
	public Set<String> etiquetas(List<Compra> compras){
		etiquetas = compras.stream().flatMap(compra -> compra.getEtiquetas().stream()).collect(Collectors.toSet());
		return etiquetas;
	}
	
	public int cantidadDeEtiquetas() {
		return etiquetas.size();
	}
	
	public List<Compra> sonDelMes(List<Compra> compras){
		return compras.stream().filter(compra -> compra.esDelMes(mesActual)).collect(Collectors.toList());
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
	
}
