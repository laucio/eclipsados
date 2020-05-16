package source;

import java.util.ArrayList;
import java.util.Set;

public class Location {
	private String name;
	private String gender;
	private String number;
	private String description;
	private ArrayList<Place> places;
	private ArrayList<String> npcs;
	private ArrayList<Connection> connections;

	public Location(String name, String gender, String number, String description) {
		super();
		this.name = name;
		this.gender = gender;
		this.number = number;
		this.description = description;
		this.setPlaces(null);
		this.places = new ArrayList<Place>();
		this.npcs = new ArrayList<String>();
		this.connections = new ArrayList<Connection>();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public ArrayList<String> getNPCS() {
		return npcs;
	}

	public void setNPCS(ArrayList<String> nPCS) {
		npcs = nPCS;
	}
	
	public boolean eliminarItemDePlaces(String item) {
		int i = 0;
		boolean eliminado = false;
		while(i < places.size() && eliminado == false) {
			Place currentPlace = places.get(i);
			if(currentPlace.buscarItemEnArray(item)) {
				currentPlace.quitarItemDeArray(item);
				eliminado = true;
				if(currentPlace.getItems().size() == 0) {
					//currentPlace.setItems(null);
				}
			}
			i++;
		}
		return eliminado;
	}

	public int getPlaceIndex(String placeName) {//me trae el indice del array de places en location
		int i = 0;
		while (i < places.size()) {
			if (places.get(i).getName().equals(placeName)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		String cadena = "unas ";
		if (this.gender.equals("male") && this.number.equals("singular")) {
			cadena = "un ";
		}

		if (this.gender.equals("male") && this.number.equals("plural")) {
			cadena = "unos ";
		}

		if (this.gender.equals("female") && this.number.equals("singular")) {
			cadena = "una ";
		}

		return cadena + this.getName();

	}

}
