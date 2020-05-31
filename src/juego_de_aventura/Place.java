package juego_de_aventura;

import java.util.ArrayList;
import java.util.Set;

public class Place extends Thing{
	
	private ArrayList<String> items;

	public Place(String name, String gender, String number, ArrayList<String> items) {
		super(name, gender, number);
		this.items = items;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	
	
	public int getItemIndex(String itemName) {///Compara el index del  array items que se encuentra en place
		int i = 0;
		while (i < items.size()) {
			if (items.get(i).equals(itemName)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public void removeItem(String currentItem) { ///saca el elemento del array de items de place
		
		items.remove((items.indexOf(currentItem)));
	}
	
	
	public boolean containsItem(String item) {//verifica si esta el item en el place
		boolean encontrado = false;
		if(getItemIndex(item) >= 0) {
			encontrado = true;
		}
		return encontrado;
	}

	@Override
	public String toString() {
		String cadena = "las ";
		if (this.gender.equals("male") && this.number.equals("singular")) {
			cadena = "el ";
		}

		if (this.gender.equals("male") && this.number.equals("plural")) {
			cadena = "los ";
		}

		if (this.gender.equals("female") && this.number.equals("singular")) {
			cadena = "la ";
		}

		return cadena + this.getName();

	}
	
	@Override
	public String getDescription() {
		return this.toString();
	}

}
