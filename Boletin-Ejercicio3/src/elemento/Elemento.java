package elemento;

// Clase con la representación de un elemento
public class Elemento {
	private int item; // valor del item producido que podria ser cualquier valor entero
	private int tipo; // puede tomar valores 1 o 2 segun el tipo del que sea

	public Elemento(int item, int tipo) {
		this.item = item;
		this.tipo = tipo;
	}
	
	public int getItem() {
		return item;
	}

	public int getTipo() {
		return tipo;
	}
	
	// Metodo para imprimir por pantalla un elemento y detallar sus campos
	@Override
	public String toString() {
		return item + "(" + tipo + ')';
	}
}
