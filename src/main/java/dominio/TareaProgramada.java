package dominio;

import java.util.TimerTask;

import dominio.presupuestos.RepositorioComprasPendientes;

import java.lang.System;
import java.util.Timer;

public class TareaProgramada extends TimerTask{
    Timer timer;

    public TareaProgramada(long periodo) {
        timer = new Timer();
        this.setPeriodo(periodo);
    }

	public void setPeriodo(long periodo){
        timer.schedule(this, 0, periodo);
    }

    @Override
    public void run() {
        System.out.println("Se van a validar las compras");
        RepositorioComprasPendientes.getInstance().validarCompras();
    }
}