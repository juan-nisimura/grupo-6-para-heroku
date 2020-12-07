package dominio.entidad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Organizacion {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany
	private List<Entidad> entidades = new ArrayList<Entidad>();

}
