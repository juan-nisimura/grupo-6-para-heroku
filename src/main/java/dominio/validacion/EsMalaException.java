package dominio.validacion;

public class EsMalaException extends RuntimeException {
	public EsMalaException(String mensaje) {
		super(mensaje);
	}
}
