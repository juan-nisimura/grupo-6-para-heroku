package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Home {
	public static ModelAndView home(Request req, Response res){
		return new ModelAndView(null, "home.hbs");
	}
	
}