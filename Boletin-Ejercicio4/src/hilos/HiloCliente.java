package hilos;

import messagepassing.MailBox;
import principal.Principal;

public class HiloCliente extends Thread {
	public static int cont = 0;

	private int id;
	private MailBox buzonRespuesta;

	public HiloCliente(MailBox buzonRespuesta) {
		this.id = cont++;
		this.buzonRespuesta = buzonRespuesta;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			// Genera un trabajo
			try {
				Thread.sleep((long) (Math.random() * 10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Pide una impresora y un tiempo de uso de la impresora
			Principal.pedirImpresora.send(id);
			// Formato respuesta: {tiempoAsignado, impresora}
			Integer[] respuesta = (Integer[]) buzonRespuesta.receive();
			
			// Utiliza la impresora el tiempo asignado
			try {
				Thread.sleep(respuesta[0]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Libera la impresora que le han asignado
			Principal.liberarImpresora.send(respuesta[1]);
			
			// Pide la pantalla para imprimir la información de mi hilo
			Principal.pedirPantalla.send(id);
			buzonRespuesta.receive();
			
			// Imprime su información
			char impresora = respuesta[1] == 0 ? 'A' : 'B';
			System.out.println("Hilo "+ id + " ha usado la impresora " + impresora);
			System.out.println("Tiempo de uso = " + respuesta[0]);
			System.out.println("Thread.sleep(" + respuesta[0] + ")");
			System.out.println("Hilo " + id + " liberando impresora " + impresora);
			System.out.println();
			
			// Libera la pantalla
			Principal.liberarPantalla.send(null);
		}
	}
}
