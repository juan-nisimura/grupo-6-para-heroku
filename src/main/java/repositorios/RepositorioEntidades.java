package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.compra.Item;
import dominio.entidad.Empresa;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.EntidadJuridica;
import dominio.entidad.OrganizacionSocial;
import dominio.entidad.TipoEmpresa;
import dominio.usuario.Usuario;

public class RepositorioEntidades {
	
	private static RepositorioEntidades instance = null;
	
	public static RepositorioEntidades getInstance(){
		if (instance == null) {
			instance = new RepositorioEntidades();
		}
		return instance;
	}
	
	public void crearEntidadBase(String nombre, String descripcion) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		EntidadBase entidadBase = new EntidadBase(nombre, descripcion);
		transaction.begin();
		entityManager.persist(entidadBase);
		transaction.commit();
	}
	
	public void crearOrganizacionSocial(String razonSocial, String nombre, String cuit, String direccionPostal) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		OrganizacionSocial organizacionSocial = new OrganizacionSocial(razonSocial, nombre, cuit, direccionPostal, entidadesBase);
		transaction.begin();
		entityManager.persist(organizacionSocial);
		transaction.commit();
	}
	
	public void crearEmpresa(String razonSocial, String nombre, String cuit, String direccionPostal, TipoEmpresa tipoEmpresa) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		Empresa empresa = new Empresa(razonSocial, nombre, cuit, direccionPostal, tipoEmpresa, entidadesBase);
		transaction.begin();
		entityManager.persist(empresa);
		transaction.commit();
	}
	
	public List<Entidad> getEntidades() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("from Entidad").getResultList();
	}

	public void borrarEntidad(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Entidad entidad = this.getEntidad(id);
		transaction.begin();
		entityManager.remove(entidad);
		transaction.commit();
	}

	public Entidad getEntidad(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (Entidad) entityManager
				.createQuery("from Entidad where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	public List<EntidadBase> getEntidadesBase() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("from EntidadBase").getResultList();
	}
}
