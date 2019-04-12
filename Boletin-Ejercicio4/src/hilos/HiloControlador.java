package hilos;

import java.util.LinkedList;

import messagepassing.Selector;
import principal.Principal;

public class HiloControlador extends Thread{
	private boolean pantallaLibre;
	private boolean[] impresorasLibres;
	private LinkedList<Integer[]>[] colas;
	
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
		Selector s = new Selector();
		s.addSelectable(Principal.pedirImpresora, false);
		s.addSelectable(Principal.liberarImpresora, false);
		s.addSelectable(Principal.pedirPantalla, false);
		s.addSelectable(Principal.liberarPantalla, false);
		for (int trabajos = 0; trabajos < Principal.NUM_CLIENTES * 20; trabajos++) {
			Principal.pedirPantalla.setGuardValue(pantallaLibre);
			switch (s.selectOrBlock()) {
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
				
			case 3:
				int i1 = (int) Principal.pedirPantalla.receive();
				Principal.buzonesRespuesta[i1].send(null);
				break;

			case 4:
				Principal.liberarPantalla.receive();
				pantallaLibre = true;
			}
		}
	}
}
