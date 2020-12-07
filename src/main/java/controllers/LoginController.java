package controllers;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.presupuestos.Detalle;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

	public ModelAndView show(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();	
		List<Usuario> usuarios = entityManager
				.createQuery("from Usuario order by id DESC", Usuario.class)
				.getResultList();
		return new ModelAndView(usuarios.get(0), "login.hbs");
	}
	
	public ModelAndView login(Request req, Response res) throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException{
		// Buscar usuario en la base de datos y verificar
		String nombre = req.queryParams("nombre");
		String contrasenia = req.queryParams("contrasenia");
		String boton = req.queryParams("boton");
		String tipoUsuario = req.queryParams("tipousuario");
		
		if(boton.equals("iniciar_sesion")) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario(nombre);
		if(usuario != null && usuario.laContraseniaEs(contrasenia) && getTipo(tipoUsuario).equals(usuario.getTipo())) {
			res.cookie("usuario_logueado", nombre);
			res.redirect("/");
			return null;
		}
		/*EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		transaction.commit();
		*/
		}
		else { //boton.equals("registrarse")
			boolean existente = RepositorioUsuarios.getInstance().existeUsuario(nombre);
			if(!existente) {
				Usuario usuario = RepositorioUsuarios.getInstance().crearUsuario(nombre, contrasenia, getTipo(tipoUsuario));
			}
			else {
				res.redirect("/error/existente");
				//todo no funciona que te lleve a esa pagina pero al menos no se crea el usuario
			}
		}
		res.redirect("/login");
		return null;
	}
	
	public ModelAndView logout(Request req, Response res) {
		res.removeCookie("usuario_logueado");
		res.redirect("/login");
		return null;
	}
	
	public TipoUsuario getTipo(String tipo) {
		TipoUsuario tipoUsuario = null;
		if(tipo.equals("admin"))
			tipoUsuario = TipoUsuario.ADMINISTRADOR;
		if(tipo.equals("estandar"))
			tipoUsuario = TipoUsuario.ESTANDAR;
				
		return tipoUsuario;
	}
}
