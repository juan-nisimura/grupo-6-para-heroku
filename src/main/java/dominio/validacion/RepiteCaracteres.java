package dominio.validacion;

import java.util.List;

public class RepiteCaracteres implements Validacion {

	public void validar(String unaContrasenia, String usuario, List<String> peoresContrasenias) {
		char[] auxiliar = unaContrasenia.toCharArray();
		for (int i = 0; i < (unaContrasenia.length() - 2); i++) {
			if (auxiliar[i] == auxiliar[i + 1] && auxiliar[i] == auxiliar[i + 2]) {
				throw new RepiteCaracteresException(
						"La contraseña no puede contener más de dos caracteres iguales seguidos");
			}
		}
	}
	
}
