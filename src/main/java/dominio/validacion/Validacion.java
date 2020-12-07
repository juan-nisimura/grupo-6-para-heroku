package dominio.validacion;

import java.util.List; 

public interface Validacion {

	public void validar(String unaContrasenia, String usuario, List<String> peoresContrasenias);
	
}
