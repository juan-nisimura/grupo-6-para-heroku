package controllers;

import dominio.compra.DireccionPostal;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;
import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
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



public class BandejaDeEntradaController implements WithGlobalEntityManager{


	public ModelAndView bandejaDeEntrada(Request req, Response res){
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(req.cookie("usuario_logueado"));
		return new ModelAndView(usuario, "bandeja_entrada.hbs");
	}
	
	public Void limpiar_bandeja(Request req, Response res){	
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(req.cookie("usuario_logueado"));
		usuario.limpiarBandeja();
		res.redirect("/bandeja_de_entrada");
		return null;
	}
}
