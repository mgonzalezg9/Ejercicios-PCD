package principal;

import hilos.HiloCliente;
import hilos.HiloControlador;
import messagepassing.MailBox;

public class Principal {
	// Constante con el n�mero de clientes a generar
	public static final int NUM_CLIENTES = 30;

	// Buzones
	public static MailBox pedirImpresora, liberarImpresora, pedirPantalla, liberarPantalla;
	public static MailBox[] buzonesRespuesta = new MailBox[NUM_CLIENTES];
	
	public static void main(String[] args) {
		// Creaci�n de los buzones
		pedirImpresora = new MailBox();
		liberarImpresora = new MailBox();
		pedirPantalla = new MailBox();
		liberarPantalla = new MailBox();
		
		// Creaci�n del hilo Controlador y el array con los clientes
		Thread controlador = new HiloControlador();
		Thread[] clientes = new HiloCliente[NUM_CLIENTES];
		
		// Creaci�n los clientes con sus respectivos buzones de respuesta
		for (int i = 0; i < clientes.length; i++) {
			buzonesRespuesta[i] = new MailBox();
			clientes[i] = new HiloCliente(buzonesRespuesta[i]);
		}
		
		// Lanzo el proceso controlador y los procesos clientes
		controlador.start();
		
		for (Thread cliente : clientes) {
			cliente.start();
		}

	}
}
