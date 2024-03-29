package hilos;

import monitores.Monitor;

// Clase abstracta con la funcionalidad b�sica de un hilo
public abstract class Hilo extends Thread {
	// Contador del numero de hilos creados
	private static int cont = 0;

	// Todos los hilos tienen acceso al mismo monitor
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
	
	// Metodo plantilla
	public abstract void accionHilo();
	
	@Override
	public void run() {
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
