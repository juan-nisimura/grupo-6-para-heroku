package dominio.usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dominio.validacion.Validacion;

public class ValidadorDeContrasenias {
	
	private static ValidadorDeContrasenias INSTANCE = null;
	private List<String> peoresContrasenias = new ArrayList<String>();
	private List<Validacion> validaciones = new ArrayList<Validacion>();

	public ValidadorDeContrasenias() throws FileNotFoundException {
		inicializarPeoresContrasenias();
	}

	private void inicializarPeoresContrasenias() throws FileNotFoundException {
		File file = new File("peoresContrasenas.txt");
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			peoresContrasenias.add(scan.nextLine());
		}
		scan.close();
	}

	static public ValidadorDeContrasenias getInstance() throws FileNotFoundException {
		if (INSTANCE == null) {
			INSTANCE = new ValidadorDeContrasenias();
		}
		return INSTANCE;
	}
	
	public ValidadorDeContrasenias agregarValidacion(Validacion unaValidacion) {
		validaciones.add(unaValidacion);
		return this;
	}

	public void validarContrasenia(String unaContrasenia, String usuario) {
		validaciones.stream().forEach(validacion -> validacion.validar(unaContrasenia, usuario, peoresContrasenias));
	}
	
}
