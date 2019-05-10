package principal;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import hilos.HiloConRunnable;

public class Principal {
	// Atributos
	// Conjunto de arrays de caracteres
	public static HashSet<char[]> palabras = new HashSet<>();
	public static int cont = 0;
	public static Semaphore signal = new Semaphore(0);
	public static Semaphore mutex = new Semaphore(1);

	public static void main(String[] args) {
		ReentrantLock mutex = new ReentrantLock();

		Thread[] hilos = new Thread[30];

		for (int i = 0; i < hilos.length; i++) {
			hilos[i] = new Thread(new HiloConRunnable(mutex));
		}

		for (Thread hilo : hilos) {
			hilo.start();
		}
	}

}
