package controllers;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.usuario.Usuario;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearEntidadBase {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearEntidadBase.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		// Agregar entidad base a la base de datos
		String nombre = req.queryParams("nombre");
		String descripcion = req.queryParams("descripcion");
		RepositorioEntidades.getInstance().crearEntidadBase(nombre,descripcion);
		res.redirect("/entidades");
		return null;
	}
}
