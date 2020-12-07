package dominio;

public class PreconditionFailed extends RuntimeException {
	PreconditionFailed(String mensaje){
		super(mensaje);
	}
}
