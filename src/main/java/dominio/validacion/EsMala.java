package dominio.validacion;

import java.util.List;

public class EsMala implements Validacion{

	public void validar(String unaContrasenia, String usuario, List<String> peoresContrasenias) {
		if (peoresContrasenias.contains(unaContrasenia))
			throw new EsMalaException("La contraseña pertenece al TOP 10000 de las peores contrasenias");
	}
	
}
