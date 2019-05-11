package hilos;

import java.util.LinkedList;

import messagepassing.Selector;
import principal.Principal;

public class HiloControlador extends Thread {
	// Atributos
	private boolean pantallaLibre;
	private boolean[] impresorasLibres;
	private LinkedList<Integer[]>[] colas;
	
	// Se suprime el warning del casting no comprobado
	@SuppressWarnings("unchecked")
	// Constructor
	public HiloControlador() {
		pantallaLibre = true;
		impresorasLibres = new boolean[2];
		colas = new LinkedList[2];
		for (int i = 0; i < impresorasLibres.length; i++) {
			impresorasLibres[i] = true;
			colas[i] = new LinkedList<>();
		}
	}

	@Override
	public void run() {
		// Se crea el selector con las distintas opciones
		Selector s = new Selector();
		s.addSelectable(Principal.pedirImpresora, false);
		s.addSelectable(Principal.liberarImpresora, false);
		s.addSelectable(Principal.pedirPantalla, false);
		s.addSelectable(Principal.liberarPantalla, false);
		// Se atienden tantas peticiones como peticiones realizan los hilos que hemos lanzado
		for (int trabajos = 0; trabajos < Principal.NUM_CLIENTES * 20; trabajos++) {
			// Guarda que para recibir peticiones de pantalla solo si la pantalla está libre
			Principal.pedirPantalla.setGuardValue(pantallaLibre);
			switch (s.selectOrBlock()) {
			// Peticiones de impresora
			case 1:
				int i = (int) Principal.pedirImpresora.receive();
				
				int tiempoImpresion = (int) (Math.random() * 10 + 1);
				int impresoraAsignada = tiempoImpresion >= 5 ? 0 : 1;
				
				if (impresorasLibres[impresoraAsignada]) {
					impresorasLibres[impresoraAsignada] = false;
					Integer[] respuesta = {tiempoImpresion, impresoraAsignada};
					Principal.buzonesRespuesta[i].send(respuesta);
				} else {
					Integer[] respuesta = {i, tiempoImpresion};
					colas[impresoraAsignada].add(respuesta);
				}
				break;
				
			// Liberaciones de impresora
			case 2:
				int impresoraUsada = (int) Principal.liberarImpresora.receive();
				impresorasLibres[impresoraUsada] = true;
				if (!colas[impresoraUsada].isEmpty()) {
					Integer[] respuesta = colas[impresoraUsada].removeFirst();
					Integer[] nuevaRespuesta = {respuesta[1], impresoraUsada};
					Principal.buzonesRespuesta[respuesta[0]].send(nuevaRespuesta);
				} else 
					impresorasLibres[impresoraUsada] = true;
				break;
				
			// Peticiones de pantalla
			case 3:
				int i1 = (int) Principal.pedirPantalla.receive();
				Principal.buzonesRespuesta[i1].send(null);
				break;

			// Liberaciones de pantalla
			case 4:
				Principal.liberarPantalla.receive();
				pantallaLibre = true;
			}
		}
	}
}
