package db;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import dominio.compra.APImercado;
import dominio.compra.DireccionPostal;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.compra.TipoPago;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.CriterioDeSeleccionPresupuesto;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.presupuestos.RepositorioComprasPendientes;
import dominio.usuario.Usuario;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	private CompraPendiente compraValida;
	private Proveedor proveedor;
	private Presupuesto unPresupuesto;
	private DireccionPostal direccionPostal = new DireccionPostal();
	
	@Before
	public void setUp() throws Exception {
		compraValida = new CompraPendiente();
		compraValida.setCantidadPresupuestosRequeridos(1);
		proveedor = new Proveedor("juancito", "45127845", direccionPostal);
		compraValida.setCriterioDeSeleccion(CriterioDeSeleccionPresupuesto.SinCriterioDeSeleccion);
		unPresupuesto = new Presupuesto(compraValida, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",15000,1));
		compraValida.agregarItem(new Item("Turron",15000,1));
		compraValida.setProveedor(proveedor);
		compraValida.setFecha(LocalDate.now());
		compraValida.setMedioPago(new MedioPago(TipoPago.DINERO_CUENTA, "identificador"));
		compraValida.setEntidad(new EntidadBase("nombre falso", "descripcion falsa"));
		
	}
	
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
	@Test
	public void agregarCompraPendienteAlRepositorio() {
		CompraPendiente compraPendiente = new CompraPendiente();
		RepositorioComprasPendientes.getInstance().agregar(compraPendiente);
	}
	
	@Test
	public void validarComprasPendientesNoValidas() {
		CompraPendiente compraPendiente1 = new CompraPendiente();
		CompraPendiente compraPendiente2 = new CompraPendiente();
		RepositorioComprasPendientes.getInstance().agregar(compraPendiente1);
		RepositorioComprasPendientes.getInstance().agregar(compraPendiente2);
		RepositorioComprasPendientes.getInstance().validarCompras();
		assertEquals(2,RepositorioComprasPendientes.getInstance().todas().size());
	}
	
	@Test
	public void validarComprasPendientesConUnaValida() {
		CompraPendiente compraInvalida = new CompraPendiente();
		RepositorioComprasPendientes.getInstance().agregar(compraInvalida);
		RepositorioComprasPendientes.getInstance().agregar(compraValida);
		RepositorioComprasPendientes.getInstance().validarCompras();
		assertEquals(1,RepositorioComprasPendientes.getInstance().todas().size());
	}
	
	@Test
	public void esCompraValida() {
		assertTrue(compraValida.verificarQueEsValida());
	}
}
