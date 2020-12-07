package controllers;

import dominio.entidad.TipoEmpresa;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearEmpresa {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearEmpresa.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
		TipoEmpresa tipoEmpresa = toTipoEmpresa(req.queryParams("tipo_empresa"));
		
		RepositorioEntidades.getInstance().crearEmpresa(razonSocial, nombre, cuit, direccionPostal, tipoEmpresa);
		res.redirect("/entidades");
		return null;
	}
	
	private TipoEmpresa toTipoEmpresa(String string) {
		if(string.equals("micro"))		return TipoEmpresa.MICRO;
		if(string.equals("pequena"))	return TipoEmpresa.PEQUENA;
		if(string.equals("mediana_1"))	return TipoEmpresa.MEDIANA_1;
		if(string.equals("mediana_2"))	return TipoEmpresa.MEDIANA_2;
		throw new RuntimeException("No existe el tipo de empresa pasado por la request");
	}
}
