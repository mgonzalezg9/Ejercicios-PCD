package monitores;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import elemento.Elemento;

public class Monitor {
	private static final int NUM_ELEMENTOS = 10;

	private LinkedList<Elemento> buffer;
	private volatile int t1;
	private volatile int t2;

	private Lock l = new ReentrantLock();
	private Condition lleno1 = l.newCondition();
	private Condition lleno2 = l.newCondition();
	private Condition hueco = l.newCondition();

	public Monitor() {
		buffer = new LinkedList<>();
		t1 = t2 = 0;
	}

	public void insertar(Elemento elem) throws InterruptedException {
		l.lock();
		try {
			// CS3: Esperar a que haya espacio en el buffer
			while (buffer.size() >= NUM_ELEMENTOS)
				hueco.await();

			// Insertar el elemento en el buffer
			buffer.add(elem);

			if (elem.getTipo() == 1) {
				t1++;
				// CS1: Mandar señal
				lleno1.signal();
			} else {
				t2++;
				// CS2: Mandar señal
				lleno2.signal();
			}

			// Llamada a imprimir para visualizar el estado del buffer
			imprimirBuffer();

		} finally {
			l.unlock();
		}

	}

	public Elemento extraer(int tipo) throws InterruptedException {
		l.lock();
		try {
			Elemento elem = null;
			if (tipo == 1) {
				// CS1: Esperar a que haya elementos de tipo 1
				while (t1 <= 0)
					lleno1.await();

			} else {
				// CS2: Esperar a que haya elementos de tipo 2
				while (t2 <= 0) {
					lleno2.await();
				}
			}

			// Extraer elemento del tipo pasado como parámetro
			for (Iterator<Elemento> iterator = buffer.iterator(); iterator.hasNext();) {
				elem = (Elemento) iterator.next();
				if (elem.getTipo() == tipo) {
					iterator.remove();
					switch (tipo) {
					case 1:
						t1--;
						break;

					case 2:
						t2--;
						break;

					}
				}
			}

			// CS3: Mandar señal
			hueco.signal();

			// Llamada a imprimir para visualizar el estado del buffer
			imprimirBuffer();

			return elem;
		} finally {
			l.unlock();
		}

	}

	private void imprimirBuffer() {
		// TODO Recorrer el buffer e imprimir cada uno de sus elementos
		System.out.println("t1 = " + t1 + "\tt2 = " + t2);
	}
}
