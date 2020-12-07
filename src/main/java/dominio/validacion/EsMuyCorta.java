package dominio.validacion;

import java.util.List;

public class EsMuyCorta implements Validacion {

	public void validar(String unaContrasenia, String usuario, List<String> peoresContrasenias) {
		if (unaContrasenia.length() < 8) {
			throw new EsMuyCortaException("La contraseña debe tener al menos 8 caracteres");
		}
	}
	
}
