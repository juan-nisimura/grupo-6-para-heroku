package controllers;

import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearOrganizacionSocial {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearOrganizacionSocial.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
		
		RepositorioEntidades.getInstance().crearOrganizacionSocial(razonSocial, nombre, cuit, direccionPostal);
		res.redirect("/entidades");
		return null;
	}
}
