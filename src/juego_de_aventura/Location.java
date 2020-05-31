package juego_de_aventura;

import java.util.ArrayList;
import java.util.Set;

public class Location extends Thing{
	private String description;
	private ArrayList<Place> places;
	private ArrayList<String> npcs;
	private ArrayList<Connection> connections;

	public Location(String name, String gender, String number, String description) {
		super(name, gender, number);
		this.description = description;
		this.setPlaces(null);
		this.places = new ArrayList<Place>();
		this.npcs = new ArrayList<String>();
		this.connections = new ArrayList<Connection>();
		
	}

	@Override
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
	
	public boolean removeFromLocation(String item) {
		int i = 0;
		boolean eliminado = false;
		while(i < places.size() && eliminado == false) {
			Place currentPlace = places.get(i);
			if(currentPlace.containsItem(item)) {
				currentPlace.removeItem(item);
				eliminado = true;
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
	
	public boolean hasItem(String item) {
      boolean retorno = false;
        int i = 0 ; 
        ArrayList<Place> places = this.places;
        while(!retorno && i<places.size() ) {
        	retorno = places.get(i).containsItem(item);
        	i++;
        }
       
		return retorno ;
	}
	
	public boolean isNamed(String locationName) {
		return this.name.contentEquals(locationName);
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

	public void removeNpc(String personaje) {
		for(Connection connection : connections) {
			connection.removeNPC(personaje);
		}
	}


}
