package dominio.validacion;

public class ContieneNombreUsuarioException extends RuntimeException {
	public ContieneNombreUsuarioException(String mensaje) {
		super(mensaje);
	}
}
