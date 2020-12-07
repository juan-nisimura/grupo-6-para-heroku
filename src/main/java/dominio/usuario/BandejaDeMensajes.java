package dominio.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

@Entity
public class BandejaDeMensajes {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
	private List<Mensaje> mensajes = new ArrayList<>();
		
	
	public void agregarMensaje(Mensaje unMensaje) {
		mensajes.add(unMensaje);
	}
	
	public List<Mensaje> listaDeMensajes() {
		return mensajes;
	}
	
	public int cantidadMensajes() {
		return mensajes.size();
	}
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void limpiar() {
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		mensajes.forEach(mensaje -> {
			em.remove(mensaje);
		});
		mensajes.clear();
		transaction.commit();
	}
}
