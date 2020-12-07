package dominio.usuario;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	private byte[] hashedPassword;
	private byte[] salt;
	
	@OneToOne(fetch = FetchType.LAZY)
	public BandejaDeMensajes bandejaDeEntrada;
	
	private TipoUsuario tipoUsuario;
	
	public String getNombre() {
		return nombre;
	}
	
	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeEntrada;
	}
	
	public Usuario(){
		
	}

	public Usuario(String usuario, String contrasenia, TipoUsuario tipoUsuario) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		ValidadorDeContrasenias.getInstance().validarContrasenia(contrasenia, usuario);
		inicializarSalt();
		this.nombre = usuario;
		this.hashedPassword = hashearContrasenia(contrasenia);
		this.tipoUsuario = tipoUsuario;
		
		this.bandejaDeEntrada = new BandejaDeMensajes();
	}
	
	private void inicializarSalt() {
		SecureRandom random = new SecureRandom();
		salt = new byte[16];
		random.nextBytes(salt);
	}
	
	private byte[] hashearContrasenia(String contrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(contrasenia.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		return factory.generateSecret(spec).getEncoded();
	}

	public boolean laContraseniaEs(String unaContrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return Arrays.equals(hashedPassword,hashearContrasenia(unaContrasenia));
	}
	
	public void recibirMensaje(Mensaje unMensaje) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(unMensaje);
		bandejaDeEntrada.agregarMensaje(unMensaje);
		transaction.commit();
	}

	public void limpiarBandeja() {
		bandejaDeEntrada.limpiar();
	}
	
	public TipoUsuario getTipo() {
		return this.tipoUsuario;
	}
	
	public String getTipo_string() {
		return this.tipoUsuario.toString();
	}
	
}
