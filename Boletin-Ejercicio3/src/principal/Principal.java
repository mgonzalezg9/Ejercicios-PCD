package principal;

import hilos.Hilo;
import hilos.HiloConsumidor;
import hilos.HiloProductor;
import monitores.Monitor;

public class Principal {
	private static final int NUM_HILOS = 6;

	public static void main(String[] args) {
		Monitor m = new Monitor();
		Hilo[] consumidores = new Hilo[NUM_HILOS];
		Hilo[] productores = new Hilo[NUM_HILOS];
		
		// Creación de los hilos
		for (int i = 0; i < NUM_HILOS; i++) {
			consumidores[i] = new HiloConsumidor(m);
		}
		
		for (int i = 0; i < NUM_HILOS; i++) {
			productores[i] = new HiloProductor(m);
		}
		
		// Ejecución de los hilos
		for (int i = 0; i < NUM_HILOS; i++) {
			consumidores[i].start();
			productores[i].start();
		}
		
	}

}
