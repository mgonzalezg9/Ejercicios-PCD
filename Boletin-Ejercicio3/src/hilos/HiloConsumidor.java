package hilos;

import elemento.Elemento;
import monitores.Monitor;

public class HiloConsumidor extends Hilo {

	public HiloConsumidor(Monitor monitor) {
		super(monitor);
	}

	@Override
	public void accionHilo() {
		// Extraer elemento haciendo uso del monitor
		try {
			Elemento elem = getMonitor().extraer(getTipo());
			//System.out.println("Tipo: " + getTipo() + "Elemento: " + elem);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// Consumir elemento
		try {
			Thread.sleep((long) (Math.random() * 10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Consumidor - Tipo " + getTipo());

	}
}
