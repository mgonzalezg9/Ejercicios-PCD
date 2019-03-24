package hilos;

import elemento.Elemento;
import monitores.Monitor;

public class HiloProductor extends Hilo {

	public HiloProductor(Monitor monitor) {
		super(monitor);
	}

	@Override
	public void accionHilo() {
		// Producir elemento del tipo correspondiente
		Elemento elem = new Elemento((int) (Math.random() * 10), getTipo());

		// Insertar elemento haciendo uso del monitor
		try {
			getMonitor().insertar(elem);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Consumidor - Tipo " + getTipo());
	}

}
