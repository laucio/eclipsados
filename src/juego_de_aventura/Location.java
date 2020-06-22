package juego_de_aventura;

import java.util.ArrayList;
import java.util.Set;

public class Location extends Thing {
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
		while (i < places.size() && eliminado == false) {
			Place currentPlace = places.get(i);
			if (currentPlace.containsItem(item)) {
				currentPlace.removeItem(item);
				eliminado = true;
			}
			i++;
		}
		return eliminado;
	}



	public boolean hasItem(String item) {
		boolean retorno = false;
		int i = 0;
		ArrayList<Place> places = this.places;
		while (!retorno && i < places.size()) {
			retorno = places.get(i).containsItem(item);
			i++;
		}

		return retorno;
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
		for (Connection connection : connections) {
			if(connection.removeNPC(personaje)) {
				this.getNPCS().remove(personaje);
			}
		}
	}

	public String describeItself(Adventure adventure) {
		String cadena;

		cadena = this.getDescription();

		if (this.getPlaces() != null) {
			for (int i = 0; i < this.getPlaces().size(); i++) {
				Place place = this.getPlaces().get(i);
				if (place.getItems().size() > 0) {
					cadena += ". En ";

					cadena += place.toString() + " hay: ";
					for (int j = 0; j < place.getItems().size(); j++) {
						String item = place.getItems().get(j);
						Item itemObj = adventure.getItem(item);
						cadena += ((j > 0) ? ((j == place.getItems().size() - 1) ? " y " : " , ") : "") + itemObj;

					}
				}
			}
		}

		ArrayList<String> curNpcs = this.getNPCS();

		if (curNpcs != null) {
			cadena += ". Hay ";
			for (int i = 0; i < curNpcs.size(); i++) {
				String npc = curNpcs.get(i);
				NPC npcObj = adventure.getNPC(npc);
				cadena += ((i > 0) ? ((i == curNpcs.size() - 1) ? " y " : " , ") : "") + npcObj;

			}
		}

		ArrayList<Connection> currentConnetions = this.getConnections();
		for (int i = 0; i < currentConnetions.size(); i++) {
			String locationConnection = currentConnetions.get(i).getLocation();
			Location location = adventure.getLocation(locationConnection);
			cadena += ". Al " + currentConnetions.get(i).getDirection() + " hay " + location;
		}

		cadena += ".";
		return cadena;
	}

	public boolean containsNpc(String name) {
		return this.getNPCS().contains(name);
	}

	public Place getMentionedPlace(String command) {
		Place place = null;
		boolean found = false;
		int i = 0;
		
		while( i < places.size() && !found) {
			if(command.contains(places.get(i).getName())) {
				place = places.get(i);
				found = true;
			}
			
			i++;
		}
	return place;
	}

	public Place getPlace(String placeName) {
		Place place = null;
		boolean found = false;
		int i = 0;
		
		while( i < places.size() && !found) {
			if(placeName.equals(places.get(i).getName())) {
				place = places.get(i);
				found = true;
			}
			
			i++;
		}
		
	return place;
	}

}
