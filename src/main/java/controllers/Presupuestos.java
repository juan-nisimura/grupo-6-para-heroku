package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Presupuestos {
	public ModelAndView editarPresupuesto(Request req, Response res){
		return new ModelAndView(null, "editar_presupuesto.hbs");
	}
}
