package hilos;

public class HiloConRunnable implements Runnable {
	private static int contadorHilos;
	private static Impresion impresora = new Impresion();
	
	private int[][] A;
	private int[] cont;
	private int id;
	
	public HiloConRunnable() {
		A = new int[10][10];
		cont = new int[10];
		this.id = ++contadorHilos;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			// Rellena el Array A y cuenta sus elementos
			for (int j = 0; j < A.length; j++) {
				for (int k = 0; k < A.length; k++) {
					A[j][k] = (int) (Math.random() * 10);
					cont[A[j][k]]++;
				}
			}
			
			// Imprime la información
			impresora.imprimir(id, A, cont);
			
			for (int j = 0; j < cont.length; j++) {
				cont[j] = 0;
			}
			
		}
		
	}

}
