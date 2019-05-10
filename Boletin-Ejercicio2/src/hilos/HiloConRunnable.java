package hilos;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import principal.Principal;
import java.util.LinkedList;

public class HiloConRunnable implements Runnable {
	// Constante
	public static int contador = 0;

	// Atributos
	private int id;
	// La palabra la guardaremos como una array de caracteres para poder generar de
	// forma aleatoria cada caracter.
	private char[] palabra;
	private ReentrantLock cerrojo;

	// Constructor
	public HiloConRunnable(ReentrantLock cerrojo) {
		id = contador++;
		this.cerrojo = cerrojo;
	}

	@Override
	public void run() {
		// Generar palabra entre 1 y 10 caracteres
		int longitud = (int) (Math.random() * 10) + 1;
		palabra = new char[longitud];

		for (int i = 0; i < palabra.length; i++) {
			palabra[i] = (char) ('a' + Math.random() * 100 % 26);
		}

		// Esperar un tiempo aleatorio
		try {
			Thread.sleep((long) (Math.random() * 10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Guardar palabra correspondiente a mi hilo
		principal.Principal.palabras.add(this.palabra);

		// PE: Exclusi�n mutua contador
		cerrojo.lock();

		// Incrementar n�mero de palabras generadas
		Principal.cont++;

		if (Principal.cont == 30) {
			// Manda la se�al de sincronizaci�n
			Principal.signal.release();
		}

		// PS: Liberar la exclusi�n mutua del contador
		cerrojo.unlock();

		// CS: Esperar la generaci�n de todas las palabras
		try {
			Principal.signal.acquire();
			// Despertar encadenado
			Principal.signal.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Buscar las palabras que empiezan por la misma letra que mi palabra
		List<char[]> primLetra = new LinkedList<>();

		principal.Principal.palabras.stream().filter(s -> String.valueOf(s).startsWith(String.valueOf(palabra[0])))
				.forEach(s -> primLetra.add(s));

		// PE: Pedir exclusi�n mutua
		try {
			Principal.mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Imprimir
		System.out.println("Hilo " + id);

		System.out.print("Todas las palabras = ");

		for (char[] cadena : Principal.palabras) {
			System.out.print(String.valueOf(cadena) + " ");
		}

		System.out.println();

		System.out.println("Palabra hilo " + id + " = " + String.valueOf(palabra));

		System.out.print("Palabras que empiezan con mi letra = ");

		for (char[] cs : primLetra) {
			System.out.print(String.valueOf(cs) + " ");
		}

		System.out.println();

		System.out.println("Terminando Hilo " + id + "\n");

		// PS: Liberar exclusi�n mutua
		Principal.mutex.release();
	}

}
