package controllers;

import repositorios.RepositorioCategorias;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearCategoriaDefault {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearCategoriaDefault.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		Long egresosMaximos;
		String nombre = req.queryParams("nombre");
		boolean bloquearNuevasCompras = toBoolean(req.queryParams("bloquearNuevasCompras"));
		boolean bloquearAgregarEntidadesBase = toBoolean(req.queryParams("bloquearAgregarEntidadesBase"));
		boolean bloquearFormarParteEntidadJuridica = toBoolean(req.queryParams("bloquearFormarParteEntidadJuridica"));
		if(req.queryParams("egresosMaximos").equals(""))
			egresosMaximos = (long) 0;
		else
			egresosMaximos = Long.parseLong(req.queryParams("egresosMaximos"));
		RepositorioCategorias.getInstance().crearCategoriaDefault(nombre, bloquearNuevasCompras,
				bloquearAgregarEntidadesBase, bloquearFormarParteEntidadJuridica, egresosMaximos);
		res.redirect("/categorias");
		return null;
	}
	
	public boolean toBoolean(String string) {
		if(string == null)	return false;
		if(string.equals("True"))	return true;
		throw new RuntimeException("El valor enviado no es válido");
	}
}
