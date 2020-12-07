package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.compra.Item;
import dominio.entidad.Categoria;
import dominio.entidad.CategoriaDefault;
import dominio.entidad.Empresa;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.EntidadJuridica;
import dominio.entidad.OrganizacionSocial;
import dominio.entidad.TipoEmpresa;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.RepositorioComprasPendientes;
import dominio.usuario.Usuario;

public class RepositorioCategorias {
	private static RepositorioCategorias instance = null;
	
	public static RepositorioCategorias getInstance(){
		if (instance == null) {
			instance = new RepositorioCategorias();
		}
		return instance;
	}
	
	public List<Categoria> getCategorias() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("from Categoria").getResultList();
	}

	public void borrarCategoria(Long id) {
		Categoria categoria = this.getCategoria(id);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(categoria);
		transaction.commit();
	}

	public void crearCategoriaDefault(String nombre, boolean bloquearNuevasCompras,
			boolean bloquearAgregarEntidadesBase, boolean bloquearFormarParteEntidadJuridica, Long egresosMaximos) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		CategoriaDefault categoriaDefault = new CategoriaDefault(nombre, bloquearNuevasCompras, 
				bloquearAgregarEntidadesBase, bloquearFormarParteEntidadJuridica, egresosMaximos);
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(categoriaDefault);
		transaction.commit();
	}
	
	public void editarCategoriaDefault(Long id, String nombre, boolean bloquearNuevasCompras,
			boolean bloquearAgregarEntidadesBase, boolean bloquearFormarParteEntidadJuridica, Long egresosMaximos) {
		Categoria categoriaDefault = getCategoria(id);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		categoriaDefault.setBloquarAgregarEntidadesBase(bloquearAgregarEntidadesBase);
		categoriaDefault.setBloquearFormarParteEntidadJuridica(bloquearFormarParteEntidadJuridica);
		categoriaDefault.setBloquearNuevosEgresos(bloquearNuevasCompras);
		categoriaDefault.setEgresosMaximos(egresosMaximos);
		categoriaDefault.setNombre(nombre);
		transaction.commit();
	}
	
	public Categoria getCategoria(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (Categoria) entityManager
				.createQuery("from Categoria where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	public List<Entidad> getEntidades(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("select entidad "
				+ "from Entidad as entidad "
				+ "left outer join entidad.categorias as categoriaEntidad "
				+ "where categoriaEntidad.id="
				+ String.valueOf(id)).getResultList();
	}

}
