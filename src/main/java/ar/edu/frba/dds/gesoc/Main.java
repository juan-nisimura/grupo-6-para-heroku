package ar.edu.frba.dds.gesoc;

import java.util.TimerTask;
import java.util.Timer;
import java.lang.System;
import dominio.TareaProgramada;

public class Main {
	
	//se crea el ejecutable en la carpeta target luego de "mvn clean install"
	//ejecutar con "java -jar PruebaConcepto-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
public static void main (String[] args) {
	
	System.out.println("hola soy el programa ejecutable");
	TareaProgramada tareaProgramada = new TareaProgramada(2000);

	}

}