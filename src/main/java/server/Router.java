package server;

import controllers.Home;
import controllers.UsuarioController;

import org.apache.commons.lang3.StringUtils;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import controllers.BandejaDeEntradaController;
import controllers.Compras;
import controllers.ComprasController;
import controllers.CrearCategoriaDefault;
import controllers.CrearEmpresa;
import controllers.CrearEntidadBase;
import controllers.CrearOrganizacionSocial;
import controllers.EditarCategoriaDefault;
import controllers.EditarCategoriasDeEntidad;
import controllers.EditarEntidadesBaseDeEntidad;
import controllers.Presupuestos;
import controllers.MenuEntidadesController;
import controllers.LoginController;
import controllers.MenuCategorias;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();
		
		Spark.staticFiles.location("/public");
		
		UsuarioController usuario = new UsuarioController();
		MenuEntidadesController entidad = new MenuEntidadesController();
		ComprasController comprascontr = new ComprasController();
		Compras compras = new Compras();
		Presupuestos presupuestos = new Presupuestos();
		
		// Login
		LoginController loginController = new LoginController();
		
		// Bandeja de Entrada
		BandejaDeEntradaController bandejaDeEntrada = new BandejaDeEntradaController();
		
		// Entidades
		CrearEntidadBase crearEntidadBase = new CrearEntidadBase();
		CrearOrganizacionSocial crearOrganizacionSocial = new CrearOrganizacionSocial();
		CrearEmpresa crearEmpresa = new CrearEmpresa();
		EditarCategoriasDeEntidad editarCategoriasDeEntidad = new EditarCategoriasDeEntidad();
		EditarEntidadesBaseDeEntidad editarEntidadesBaseDeEntidad = new EditarEntidadesBaseDeEntidad();
		
		// Categorias
		MenuCategorias menuCategorias = new MenuCategorias();
		CrearCategoriaDefault crearCategoriaDefault = new CrearCategoriaDefault();
		EditarCategoriaDefault editarCategoriaDefault = new EditarCategoriaDefault();
		
		/*
		Spark.before((request, response)-> {
			if(!request.pathInfo().equals("/login") &&
					StringUtils.isEmpty(request.cookie("usuario_logueado"))) {
				response.redirect("/login");
			}
			//PerThreadEntityManagers.getEntityManager();
		});
				
		Spark.after((request, response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
		});
		*/
		
		Spark.get("/", Home::home, engine);
		
		Spark.get("/login", loginController::show, engine);
		Spark.post("/login", loginController::login, engine);
		Spark.get("logout", loginController::logout, engine);
		
		Spark.get("/entidades", entidad::show, engine);
		Spark.get("/bandeja_de_entrada", bandejaDeEntrada::bandejaDeEntrada,engine);
		Spark.post("/bandeja_de_entrada/limpiar", bandejaDeEntrada::limpiar_bandeja);
		
		//Compras
		Spark.get("/compras", comprascontr::compras,engine);
		Spark.post("/compras", comprascontr::crear_compra);
		Spark.get("/compras/:idCompra", comprascontr::menu_compra,engine);
		Spark.post("/compras/:idCompra", comprascontr::update_compra);
		Spark.get("/compras/:idCompra/presupuestos", comprascontr::editar_presup,engine);
		Spark.post("/compras/:idCompra/presupuestos", comprascontr::agregar_presupuesto);
		Spark.post("/compras/:idCompra/presupuestos/:idPresup/borrar", comprascontr::borrar_presupuesto);
		Spark.post("/compras/validar", comprascontr::validar_compras);
		Spark.post("/compras/delete/:idBorrado", comprascontr::borrar_compra);
		
		//componentes de una compra
		Spark.get("/compra/editar", compras::editarCompra, engine);
		Spark.get("/compra/editar/presupuestos", compras::presupuestos, engine);
		Spark.get("/compra/editar/etiquetas", compras::etiquetas, engine);
		
		Spark.get("/presupuesto/editar", presupuestos::editarPresupuesto, engine);
		
		Spark.get("/usuario/crear", usuario::crear,engine);
		Spark.post("/usuario/crear", usuario::creacion);
	
		// Menu entidades
		Spark.get("/entidades/crear_empresa", crearEmpresa::show,engine);
		Spark.get("/entidades/crear_organizacion_social", crearOrganizacionSocial::show,engine);
		Spark.get("/entidades/crear_entidad_base", crearEntidadBase::show,engine);
		Spark.post("/entidades/crear_empresa", crearEmpresa::crear,engine);
		Spark.post("/entidades/crear_organizacion_social", crearOrganizacionSocial::crear,engine);
		Spark.post("/entidades/crear_entidad_base", crearEntidadBase::crear,engine);
		
		Spark.get("/entidades/:id/delete", entidad::borrarEntidad, engine);
		
		Spark.get("/entidades/organizaciones_sociales/:id/", entidad::mostrarOrganizacionSocial, engine);
		Spark.get("/entidades/empresas/:id/", entidad::mostrarEmpresa,engine);
		Spark.get("/entidades/entidades_base/:id/", entidad::mostrarEntidadBase,engine);
		
		// Menu categorias
		Spark.get("/categorias", menuCategorias::show, engine);
		Spark.get("/categorias/:id/delete", menuCategorias::borrarCategoria, engine);
		Spark.get("categorias/categorias_default/:id", menuCategorias::mostrarCategoriaDefault, engine);
		
		Spark.get("/categorias/categorias_default/:id/editar", editarCategoriaDefault::show, engine);
		Spark.post("/categorias/categorias_default/:id/editar", editarCategoriaDefault::editar, engine);
		
		Spark.get("/categorias/crear_categoria_default", crearCategoriaDefault::show, engine);
		Spark.post("/categorias/crear_categoria_default", crearCategoriaDefault::crear, engine);
		
		
		Spark.get("/entidades/:tipo_entidad/:id_entidad/editar_categorias", editarCategoriasDeEntidad::show, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/agregar_categoria/:id_categoria", editarCategoriasDeEntidad::agregarCategoria, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/quitar_categoria/:id_categoria", editarCategoriasDeEntidad::quitarCategoria, engine);
		
		Spark.get("/entidades/:tipo_entidad/:id_entidad/editar_entidades_base", editarEntidadesBaseDeEntidad::show, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/agregar_entidad_base/:id_entidad_base", editarEntidadesBaseDeEntidad::agregarEntidadBase, engine);
		Spark.get("/entidades/:tipo_entidad/:id_entidad/quitar_entidad_base/:id_entidad_base", editarEntidadesBaseDeEntidad::quitarEntidadBase, engine);
		
		
		Spark.get("/usuario/crear/erroritem", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/usuario/crear/errordp", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/compras/error/errordetalle", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/compras/error/errorproveedor", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/compras/error/errormediopago", (request, response) -> {
			return "no existe medioDePago con ese ID";
		});
		Spark.get("/compras/error/errorentidad", (request, response) -> {
			return "no existe entidad con ese ID";
		});
		
		Spark.get("/presupuestos/error/errordetalle", (request, response) -> {
			return "no existe detalle con ese ID";
		});
		Spark.get("/presupuestos/error/errorproveedor", (request, response) -> {
			return "no existe proveedor con ese ID";
		});
		Spark.get("/presupuestos/error/errordocumento", (request, response) -> {
			return "no existe documento con ese ID";
		});
		Spark.get("/error/existente", (request, response) -> {
			return "ya existe usuario con ese nombre";
		});
	}
}