package prueba;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.PreconditionFailed;
import dominio.compra.*;
import dominio.presupuestos.*;
import dominio.entidad.*;
import dominio.usuario.*;
import dominio.validacion.*;


public class Test_Entrega1 {

	public Entidad entidad;
	
	public String razonSocial = "razonSocial";
	String cuit= "cuit";
	String direccionPostal = "direccionPostal";
	String nombreFicticio = "nombreFicticio";
	List<EntidadBase> entidades = new ArrayList<EntidadBase>();
	
	public Proveedor proveedor;
	public MedioPago medioPago;
	public List<Item> items = new ArrayList<Item>();
	public Item item;
	public List<Presupuesto> presupuestos;
	public Detalle detalle = new Detalle();
	public Usuario usuario;
	public List<Usuario> usuariosRevisores = new ArrayList<>();
	public DireccionPostal direccion_postal = new DireccionPostal();
	public Compra compra;
	public Categoria categoria = new CategoriaDefault();
	ValidadorDeContrasenias validador;
	
	
	@Before
	public void initialize() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		entidad = new OrganizacionSocial(razonSocial, nombreFicticio, cuit, direccionPostal, entidades);
		
		proveedor = new Proveedor("juancito", "45127845", direccion_postal);
		medioPago = new MedioPago(TipoPago.EFECTIVO, "Identificador");
		item = new Item("cuaderno", 500, 2);
		validador = new ValidadorDeContrasenias();
		usuario = new Usuario("Carlitos","ContraseniaVerdadera",TipoUsuario.ESTANDAR);
		validador.agregarValidacion(new EsMala())
			.agregarValidacion(new EsMuyCorta())
			.agregarValidacion(new RepiteCaracteres())
			.agregarValidacion(new ContieneNombreDeUsuario());
	}
	
	
	@Test
	public void entidadCompraYquedaRegistrado() throws PreconditionFailed{
		int size_compras = entidad.compras.size();
		items.add(item);
		entidad.agregarCompra(compra);
		
		Assert.assertEquals("Error al agregar compra", size_compras + 1, entidad.compras.size());
	}
	
	@Test(expected=EsMalaException.class)
	public void laContraseniaEsUnaDeLas10000Peores() {
		validador.validarContrasenia("1234","Nombre de Usuario");
	}
	
	@Test(expected=EsMuyCortaException.class)
	public void laContraseniaEsCorta() {
		validador.validarContrasenia("Holanda","Nombre de Usuario");
	}
	
	@Test(expected=RepiteCaracteresException.class)
	public void laContraseniaRepiteCaracteres() {
		validador.validarContrasenia("zaaazaaazaaa","Nombre de Usuario");
	}
	
	@Test(expected=ContieneNombreUsuarioException.class)
	public void laContraseniaContieneElNombreDeUsuario() {
		validador.validarContrasenia("nombreUsuario","nombreUsuario");
	}
	
	@Test
	public void laContraseniaEsCorrecta() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.assertTrue(usuario.laContraseniaEs("ContraseniaVerdadera"));
	}
	
	@Test
	public void laContraseniaEsIncorrecta() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Assert.assertFalse(usuario.laContraseniaEs("ContraseniaEquivocada"));
	}
}
