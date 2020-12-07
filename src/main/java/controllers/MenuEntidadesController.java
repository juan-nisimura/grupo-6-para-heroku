package controllers;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Entidad;
import dominio.usuario.Usuario;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class MenuEntidadesController {

	public ModelAndView show(Request req, Response res){
		return new ModelAndView(RepositorioEntidades.getInstance(), "entidades.hbs");
	}

	public ModelAndView mostrarEntidadBase(Request req, Response res){
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id")));
		return new ModelAndView(entidad, "menuEntidadBase.hbs");
	}
	
	public ModelAndView mostrarEmpresa(Request req, Response res){
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id")));
		return new ModelAndView(entidad, "menuEmpresa.hbs");
	}
	
	public ModelAndView mostrarOrganizacionSocial(Request req, Response res){
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id")));
		return new ModelAndView(entidad, "menuOrganizacionSocial.hbs");
	}
	
	public ModelAndView borrarEntidad(Request req, Response res) {
		Long id = new Long(req.params("id"));
		RepositorioEntidades.getInstance().borrarEntidad(id);
		res.redirect("/entidades");
		return null;
	}
}