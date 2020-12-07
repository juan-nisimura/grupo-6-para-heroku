package prueba;

import dominio.compra.*;
import dominio.presupuestos.*;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Assert;

public class Test_Entrega2 {

    private APImercado requester;
	private CompraPendiente compraSinPresupuestos, compraBarata;
	private Proveedor proveedor, PROVEEDOR_CARO, PROVEEDOR_BARATO;
	private Presupuesto presupuesto, PRESUPUESTO_BARATO, PRESUPUESTO_CARO;
	private Usuario usuario;
	private Detalle detalle;
	private DireccionPostal direccionPostal = new DireccionPostal();
	private String A_CON_TILDE = "\u00e1";
	private String O_CON_TILDE = "\u00f3";

	@Before
    public void setUp() throws Exception {
        this.requester = new APImercado();
		compraSinPresupuestos = new CompraPendiente();
		compraBarata = new CompraPendiente();
		compraSinPresupuestos.setCantidadPresupuestosRequeridos(3);
		compraBarata.setCantidadPresupuestosRequeridos(3);
		proveedor = new Proveedor("juancito", "45127845", direccionPostal);
		PROVEEDOR_BARATO = new Proveedor("pancho", "59364958", direccionPostal);
		PROVEEDOR_CARO = new Proveedor("mirtha legrand", "12345678", direccionPostal);
		PRESUPUESTO_BARATO = new Presupuesto(compraBarata, PROVEEDOR_BARATO)
								.agregarItem(new Item("El Mono Liso",2,1));
		PRESUPUESTO_CARO = new Presupuesto(compraBarata, PROVEEDOR_CARO)
								.agregarItem(new Item("Una ferrari",10000000,1));
		compraBarata.setCriterioDeSeleccion(CriterioDeSeleccionPresupuesto.PresupuestoMasBarato);
		usuario = new Usuario("anonnymous", "dds",TipoUsuario.ESTANDAR);
		detalle = new Detalle();
		detalle.agregarItem(new Item("Patrones de Disenio", 1, 200));
	}

    @Test
    public void obtengoLaMonedaDelPais() throws Exception {
        String moneda = requester.obtenerMonedaPais("AR");
        assertEquals(moneda, "ARS");
    }
	
    @Test
    public void DolarValeMasDe60() throws Exception {
        Double valor = requester.convertirMoneda(1, "USD", "ARS");
        assertTrue("Dolar vale mas de 60", valor > 60);
    }
	
    @Test
    public void zip5000QuedaEnCordoba() throws Exception {
        String provincia = requester.obtenerProvinciaDeZip("5000");
        assertEquals(provincia, "C"+O_CON_TILDE+"rdoba");
    }

    @Test
    public void elNombreDeLaCiudadEsCorrecto(){
        String ciudad = requester.obtenerCiudadDeID("TUxBQ1ZBTDM4MGFj");
        assertEquals(ciudad, "Valeria del Mar");
    }
  
  @Test
  public void laProvinciaDeLaCiudadEsCorrecto(){
      String provincia = requester.obtenerProvinciaDeCiudad("TUxBQ1ZBTDM4MGFj");
      assertEquals(provincia, "Bs.As. Costa Atl"+A_CON_TILDE+"ntica");
  }
  
  @Test
  public void elPaisDeLaCiudadEsCorrecto(){
      String pais = requester.obtenerPaisDeCiudad("TUxBQ1ZBTDM4MGFj");
      assertEquals(pais, "Argentina");
  }
  
	@Test
	public void noHaySuficientesPresupuestos() {
		Assert.assertFalse(compraSinPresupuestos.verificarCantidadPresupuestos());
	}

	@Test
	public void haySuficientesPresupuestos() {
		new Presupuesto(compraSinPresupuestos, proveedor);
		new Presupuesto(compraSinPresupuestos, proveedor);
		new Presupuesto(compraSinPresupuestos, proveedor);
		Assert.assertTrue(compraSinPresupuestos.verificarCantidadPresupuestos());
	}
	
	@Test
	public void compraEstaBasadaEnPresupuesto() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",15000,1));
		compraSinPresupuestos.agregarItem(new Item("Turron",15000,1))
			.setProveedor(proveedor);
		Assert.assertTrue(compraSinPresupuestos.verificarDetallePresupuesto());
	}
	
	@Test
	public void presupuestoTieneMasItemsQueCompra() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",30,2))
			.agregarItem(new Item("Alfajor",300,12));
		compraSinPresupuestos.agregarItem(new Item("Turron",30,2))
			.setProveedor(proveedor);
		Assert.assertFalse(compraSinPresupuestos.verificarDetallePresupuesto());
	}
	
	@Test
	public void compraTieneMasItemsQuePresupuesto() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Turron",30,2));
		compraSinPresupuestos.agregarItem(new Item("Turron",30,2))
			.agregarItem(new Item("Alfajor",300,12))
			.setProveedor(proveedor);
		Assert.assertFalse(compraSinPresupuestos.verificarDetallePresupuesto());
	}
	
	@Test
	public void compraTieneItemsDistintosAlPresupuesto() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		unPresupuesto.agregarItem(new Item("Alfajor",100,4));
		compraSinPresupuestos.agregarItem(new Item("Alfajor",300,12))
			.setProveedor(proveedor);
		Assert.assertFalse(compraSinPresupuestos.verificarDetallePresupuesto());
	}
	
	@Test
	public void proveedorSeleccionadoTienePresupuestoMasBarato() {
		compraBarata.setProveedor(PROVEEDOR_BARATO);
		Assert.assertTrue(compraBarata.verificarCriterioDeSeleccion());
	}
	
	@Test
	public void proveedorSeleccionadoNoTienePresupuestoMasBarato() {
		compraBarata.setProveedor(PROVEEDOR_CARO);
		Assert.assertFalse(compraBarata.verificarCriterioDeSeleccion());
	}
	
	@Test
	public void LasExcepcionesSeMandanALaBandejaDelUsuario() {
		Presupuesto unPresupuesto = new Presupuesto(compraSinPresupuestos, proveedor);
		compraSinPresupuestos.setCantidadPresupuestosRequeridos(4);
		unPresupuesto.agregarItem(new Item("Turron",30,2))
			.agregarItem(new Item("Alfajor",300,12));
		compraSinPresupuestos.agregarItem(new Item("Turron",30,2))
			.setProveedor(proveedor);
		MedioPago unMedioDePago = new MedioPago(TipoPago.EFECTIVO,"2193829183928");
		compraSinPresupuestos.agregarUsuarioRevisor(usuario);
		compraSinPresupuestos.setMedioPago(unMedioDePago);
		compraSinPresupuestos.validarCompra();
		assertEquals(usuario.bandejaDeEntrada.cantidadMensajes(),1);
	}
}









