package hilos;

public class Impresion {
	// Método para imprimir en Exclusión mutua la información de cada hilo.
	public synchronized void imprimir(int id, int[][] array, int[] cont) {
		System.out.println("Hilo " + id);

		System.out.println("Array generado");

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}

		for (int i = 0; i < 10; i++) {
			System.out.println("Contador de " + i + " = " + cont[i]);
		}

		System.out.println("Terminando Hilo " + id);
	}
}
