package principal;

import hilos.HiloConRunnable;

public class Principal {

	public static void main(String[] args) {
		Runnable a = new HiloConRunnable();
		Runnable b = new HiloConRunnable();

		Thread t1 = new Thread(a);
		Thread t2 = new Thread(b);

		t1.start();
		t2.start();
	}

}
