package prueba;

import static org.junit.Assert.*;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;

import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;
import dominio.compra.*;
import dominio.entidad.*;

public class Test_entrega_3 {
	private MedioPago unMedioDePago;
	private MedioPago otroMedioDePago;
	private LocalDate fechaEstaMes, otraFechaEsteMes, fechaEnero;
	private Presupuesto presupuesto, PRESUPUESTO_BARATO, PRESUPUESTO_CARO;
	private List<Presupuesto> listaPresupuestos = new ArrayList<>();
	private List<Usuario> usuariosRevisores = new ArrayList<>();
	private List<Compra> listaDeCompras = new ArrayList<>();
	private String etiquetaAmoblamiento;
	private String etiquetaIndumentaria;
	private Detalle detalle = new Detalle();
	private DireccionPostal direccionPostal;
	private Proveedor proveedor;
	private GeneradorReporte unReporte;
	private Compra compraAmoblamientoUno, compraAmoblamientoDos, compraIndumentariaUno;
	private Set<String> etiquetas = new HashSet<String>();
	private Categoria unaCategoria = new CategoriaDefault();
	private EntidadBase unaEntidadBase;
	private List<EntidadBase> listaEntidadesBase = new ArrayList<>();
	private EntidadJuridica unaEntidadJuridica;
	private Item tresCamas;
	
	
	
	@Before
	public void setUp() throws Exception {
		unMedioDePago = new MedioPago(TipoPago.EFECTIVO,"2193829183928");
		otroMedioDePago = new MedioPago(TipoPago.TARJETA_CREDITO,"2193829183928");
		fechaEstaMes = LocalDate.of( 2020, LocalDate.now().getMonth(), 9 );
		otraFechaEsteMes = LocalDate.of( 2020, LocalDate.now().getMonth(), 12 );
		fechaEnero = LocalDate.of( 2020, Month.JANUARY, 23 );
		etiquetaAmoblamiento = "Amoblamiento";
		etiquetaIndumentaria = "Indumentaria";
		direccionPostal = new DireccionPostal();
		proveedor = new Proveedor("juancito", "45127845", direccionPostal);
		unReporte = new GeneradorReporte();
		listaPresupuestos.add(presupuesto);
		listaPresupuestos.add(PRESUPUESTO_BARATO);
		listaPresupuestos.add(PRESUPUESTO_CARO);
		compraAmoblamientoUno = new Compra(proveedor ,unMedioDePago, fechaEstaMes, listaPresupuestos, detalle, usuariosRevisores, new ArrayList<String>());
		compraAmoblamientoUno.agregarEtiqueta(etiquetaAmoblamiento);
		compraAmoblamientoDos = new Compra(proveedor, otroMedioDePago, fechaEnero, listaPresupuestos, detalle, usuariosRevisores, new ArrayList<String>());
		compraAmoblamientoDos.agregarEtiqueta(etiquetaAmoblamiento);
		compraIndumentariaUno = new Compra(proveedor ,unMedioDePago, otraFechaEsteMes, listaPresupuestos, detalle, usuariosRevisores, new ArrayList<String>());
		compraIndumentariaUno.agregarEtiqueta(etiquetaIndumentaria);
		listaDeCompras.add(compraAmoblamientoUno);
		listaDeCompras.add(compraAmoblamientoDos);
		listaDeCompras.add(compraIndumentariaUno);
		etiquetas.add("Amoblamiento");
		etiquetas.add("Indumentaria");
		unaEntidadBase = new EntidadBase("Empresa 123", "Es una entidad base");
		unaEntidadBase.agregarCategoria(unaCategoria);
		unaEntidadJuridica = new Empresa("La Entidad", "La Entidad Ficticia", "2913J923", "OD2N9D", TipoEmpresa.MICRO, listaEntidadesBase);
		unaEntidadJuridica.agregarCategoria(unaCategoria);
		tresCamas = new Item("Cama", 3, 2500);
		detalle.agregarItem(tresCamas);
		
	}
	
	@Test
	public void testReporteDevuelveEtiquetas() {
		final Set<String> etiquetasReporte = new GeneradorReporte().etiquetas(listaDeCompras);
		assertEquals(etiquetasReporte, etiquetas);
	}
	
	@Test
	public void testSonDelMes() {
		List<Compra> comprasDelMes = new ArrayList<>();
		comprasDelMes = unReporte.sonDelMes(listaDeCompras);
		List<Compra> comprasMesActual = new ArrayList<>();
		comprasMesActual.add(compraAmoblamientoUno);
		comprasMesActual.add(compraIndumentariaUno);
		assertEquals(comprasMesActual,comprasDelMes);
	}
	
	@Test
	public void categoraNoPuedeAgregarCompraSiTieneNuevosEgresosBloqueados() throws RuntimeException{
		unaCategoria.setBloquearNuevosEgresos(true);
		unaCategoria.validarAgregarCompra(unaEntidadBase, compraIndumentariaUno);
	}
	
	@Test
	public void noPuedeAgregarEntidadBase() throws RuntimeException{
		unaCategoria.setBloquarAgregarEntidadesBase(false);
		unaCategoria.validarAgregarEntidadBase(unaEntidadJuridica, unaEntidadBase);
	}
	
	@Test
	public void noPuedeAgregarEntidadJuridica() throws RuntimeException{
		unaCategoria.setBloquearFormarParteEntidadJuridica(false);
		unaCategoria.validarAgregarEntidadBase(unaEntidadJuridica, unaEntidadBase);
	}
	
	@Test
	public void noPuedeAgregarCompraSiExcedeLosEgresosMaximos() throws RuntimeException{
		unaCategoria.setEgresosMaximos(1000);
		unaCategoria.validarAgregarCompra(unaEntidadBase, compraAmoblamientoUno);
	}
	
	@Test
	public void puedeAgregarCompraSiNoExcedeLosEgresosMaximos(){
		unaCategoria.setEgresosMaximos(10000);
		unaCategoria.validarAgregarCompra(unaEntidadBase, compraAmoblamientoUno);
	}
	
}








