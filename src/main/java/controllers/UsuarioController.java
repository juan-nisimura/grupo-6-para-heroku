package controllers;

import dominio.compra.DireccionPostal;
import dominio.compra.DocumentoComercial;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.compra.TipoPago;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.RepositorioComprasPendientes;
import dominio.usuario.Mensaje;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;



public class UsuarioController implements WithGlobalEntityManager{
	/*
	public ModelAndView menuUsuario(Request req, Response res) {
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(req.cookie("usuario_logueado"));
		return new ModelAndView(usuario, "menu_usuario.hbs");
	}*/
	
	
	public ModelAndView crear(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Map<String, Object> model = new HashMap<>();		
		List<Detalle> detalles = entityManager
				.createQuery("from Detalle order by id DESC", Detalle.class)
				.getResultList();
		List<Item> items = entityManager
				.createQuery("from Item order by id DESC", Item.class)
				.getResultList();
		List<Proveedor> proveedores = entityManager
				.createQuery("from Proveedor order by id DESC", Proveedor.class)
				.getResultList();
		List<DireccionPostal> direcciones = entityManager
				.createQuery("from DireccionPostal order by id DESC", DireccionPostal.class)
				.getResultList();
		List<MedioPago> mediospago = entityManager
				.createQuery("from MedioPago order by id DESC", MedioPago.class)
				.getResultList();
		List<DocumentoComercial> documentos = entityManager
				.createQuery("from DocumentoComercial order by id DESC", DocumentoComercial.class)
				.getResultList();
		//no era necesario hacer traerse la base completa de cada tabla pero si era facil
		
		model.put("detalle", detalles.get(0));
		model.put("item", items.get(0));
		model.put("proveedor", proveedores.get(0));
		model.put("direccionpostal", direcciones.get(0));
		model.put("mediopago", mediospago.get(0));
		model.put("documentocomercial", documentos.get(0));


		return new ModelAndView(model, "crear_compra.hbs");
	}
	
	public Void creacion(Request req, Response res){	//los queryparam salen del campo name
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();

		if(req.queryParams("detalle") != null) {

			Item itemNuevo = new Item(req.queryParams("desc_item"), new Float(req.queryParams("val_item")), new Integer(req.queryParams("cant_item")));
			Detalle detalle = em.find(Detalle.class, new Long(req.queryParams("detalle")));
			if(detalle == null) {
				res.redirect("/usuario/crear/erroritem");
				return null;
			}
	
			transaction.begin();
			em.persist(itemNuevo);
			detalle.agregarItem(itemNuevo);
			transaction.commit();
						
		}

		if(req.queryParams("moneda") != null) {

			Detalle detalle = new Detalle();
			detalle.setMoneda(req.queryParams("moneda"));

			transaction.begin();
			em.persist(detalle);
			transaction.commit();
			
		}
		
		if(req.queryParams("razon_social") != null) {

			Proveedor proveedor = new Proveedor();
			proveedor.setRazon_social(req.queryParams("razon_social"));
			proveedor.setDni_cuil_cuit(req.queryParams("dni"));

			transaction.begin();
			em.persist(proveedor);
			transaction.commit();
			
		}
		
		if(req.queryParams("id_proveedor") != null) {

			Proveedor proveedor = em.find(Proveedor.class, new Long(req.queryParams("id_proveedor")));
			if(proveedor == null) {
				res.redirect("/usuario/crear/errordp");
				return null;
			}
			DireccionPostal direccionPostal = new DireccionPostal();
			direccionPostal.setDireccion(req.queryParams("direccion"));
			direccionPostal.setterCiudad(req.queryParams("ciudad"));
			direccionPostal.setterProvincia(req.queryParams("provincia"));
			direccionPostal.setterPais(req.queryParams("pais"));

			transaction.begin();
			em.persist(direccionPostal);
			transaction.commit();
		}
		
		if(req.queryParams("identificador_medio_pago") != null) {

			//tiene constructor para validaciones el mediopago asi que hubo que copiar el enum aca
			Long tipo_pago =  new Long(req.queryParams("tipo_pago"));
			TipoPago tipoDePago;
	    	tipoDePago = null;
	    	
	        if(tipo_pago == 0)
	        	tipoDePago = TipoPago.TARJETA_CREDITO;
	        if(tipo_pago == 1) 
	        	tipoDePago = TipoPago.TARJETA_DEBITO;
	        if(tipo_pago == 2)
	        	tipoDePago = TipoPago.EFECTIVO;
	        if(tipo_pago == 3)
	        	tipoDePago = TipoPago.CAJERO_AUTOMATICO;
	        if(tipo_pago == 4)
	        	tipoDePago = TipoPago.DINERO_CUENTA;
						
			MedioPago medioPago = new MedioPago(tipoDePago ,req.queryParams("identificador_medio_pago"));

			transaction.begin();
			em.persist(medioPago);
			transaction.commit();
			
		}
		
		if(req.queryParams("numero_documento") != null) {
						
	        DocumentoComercial docComercial = new DocumentoComercial();
	        
	        docComercial.setNumDocumento(new Integer(req.queryParams("numero_documento")));
	        docComercial.setTipoDocumento(new Long(req.queryParams("tipo_doc")));

			transaction.begin();
			em.persist(docComercial);
			transaction.commit();
			
		}
		
		
		res.redirect("/usuario/crear");
		return null;		
	}
	

	
}
