package dominio.validacion;

public class RepiteCaracteresException extends RuntimeException {
	public RepiteCaracteresException(String mensaje) {
		super(mensaje);
	}
}
