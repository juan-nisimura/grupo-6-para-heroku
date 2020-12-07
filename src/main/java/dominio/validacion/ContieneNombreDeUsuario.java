package dominio.validacion;

import java.util.List;

public class ContieneNombreDeUsuario implements Validacion {

	public void validar(String unaContrasenia, String usuario, List<String> peoresContrasenias) {
		if (unaContrasenia.indexOf(usuario) > -1) {
			throw new ContieneNombreUsuarioException("La contraseña no puede contener el nombre de usuario");
		}
	}
	
}
