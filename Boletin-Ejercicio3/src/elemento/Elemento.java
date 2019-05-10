package elemento;

public class Elemento {
	private int item; // valor del �tem producido que podr� ser cualquier valor entero
	private int tipo; // puede tomar valores 1 � 2 seg�n el tipo del que sea

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
	
	// Método para imprimir por pantalla un elemento y detallar sus campos
	@Override
	public String toString() {
		return item + "(" + tipo + ')';
	}
}
