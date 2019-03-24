package hilos;

import monitores.Monitor;

public abstract class Hilo extends Thread {
	private static int cont = 0;

	private Monitor monitor;
	private final int tipo;
	
	public Hilo(Monitor monitor) {
		this.monitor = monitor;
		this.tipo = (cont++) % 2 + 1;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public Monitor getMonitor() {
		return monitor;
	}
	
	// Método plantilla
	public abstract void accionHilo();
	
	@Override
	public void run() {
		//System.out.println(toString());
		for (int i = 0; i < 20; i++) {
			// Dependiendo de si es productor o consumidor realiza una accion u otra
			accionHilo();
		}
	}
	
	@Override
	public String toString() {
		return "Tipo: " + tipo + " " + getClass().getSimpleName();
	}
}
