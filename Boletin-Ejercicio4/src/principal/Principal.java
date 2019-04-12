package principal;

import hilos.HiloCliente;
import hilos.HiloControlador;
import messagepassing.MailBox;

public class Principal {
	public static final int NUM_CLIENTES = 30;
	public static MailBox pedirImpresora, liberarImpresora, pedirPantalla, liberarPantalla;
	public static MailBox[] buzonesRespuesta = new MailBox[NUM_CLIENTES];
	
	public static void main(String[] args) {
		pedirImpresora = new MailBox();
		liberarImpresora = new MailBox();
		pedirPantalla = new MailBox();
		liberarPantalla = new MailBox();
		
		Thread controlador = new HiloControlador();
		Thread[] clientes = new HiloCliente[NUM_CLIENTES];
		
		for (int i = 0; i < clientes.length; i++) {
			buzonesRespuesta[i] = new MailBox();
			clientes[i] = new HiloCliente(buzonesRespuesta[i]);
		}
		
		controlador.start();
		
		for (Thread cliente : clientes) {
			cliente.start();
		}

	}
}
