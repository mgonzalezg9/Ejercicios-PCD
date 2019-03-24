package elemento;

public class Elemento {
	private int item; // valor del ítem producido que podrá ser cualquier valor entero
	private int tipo; // puede tomar valores 1 ó 2 según el tipo del que sea

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
	
	@Override
	public String toString() {
		return String.valueOf(item) + "(" + tipo + ')';
	}
}
