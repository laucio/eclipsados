package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;

public class AventuraZork {
	private Setting settings;
	private ArrayList<Location> locations = null;
	private ArrayList<NonPlayableCharacter> npcs = null;
	private ArrayList<Item> items = null;
	private ArrayList<String> inventory = null;
	private ArrayList<Endgame> endgames = null;

	public AventuraZork() {

	}

	public Setting getSettings() {
		return settings;
	}

	public void setSettings(Setting settings) {
		this.settings = settings;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public ArrayList<NonPlayableCharacter> getNpcs() {
		return npcs;
	}

	public void setNpcs(ArrayList<NonPlayableCharacter> npcs) {
		this.npcs = npcs;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<String> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<String> inventory) {
		this.inventory = inventory;
	}

	public ArrayList<Endgame> getEndgames() {
		return endgames;
	}

	public void setEndgames(ArrayList<Endgame> endgames) {
		this.endgames = endgames;
	}

	public String verAlrededor(String currentLocation) {
		String cadena;
		int indiceLocation = getLocationIndex(currentLocation);
		Location currentLocationObj = this.locations.get(indiceLocation);
		// Como primera parte de donde estoy, es la descripcion de la location (ej:
		// Estas en un muelle)
		cadena = currentLocationObj.getDescription();

		if (currentLocationObj.getPlaces() != null) {
			for (int i = 0; i < currentLocationObj.getPlaces().size(); i++) {
				Place place = currentLocationObj.getPlaces().get(i);
				if (place.getItems().size() > 0) {
					// Despues mencionamos el place (ej: el piso)
					cadena += ". En ";

					cadena += place.toString() + " hay: ";
					for (int j = 0; j < place.getItems().size(); j++) {
						// Comenzamos a agregar los items que hay en cada place de la location
						String item = place.getItems().get(j);
						int itemIndex = getItemIndex(item);
						cadena += ((j > 0) ? ((j == place.getItems().size() - 1) ? " y " : " , ") : "")
								+ this.getItems().get(itemIndex).toString();

					} // end of for (int j = 0; j < place.getItems().size(); j++)
				} // end of if (place.getItems().size() > 0)
			} // for (int i = 0; i < currentLocationObj.getPlaces().size(); i++)
		} // if (currentLocationObj.getPlaces() != null)

		// Agregamos NPCS
		ArrayList<String> curNpcs = currentLocationObj.getNPCS();

		if (curNpcs != null) {
			cadena += ". Hay ";
			for (int i = 0; i < curNpcs.size(); i++) {
				int npcsIndex = getNPCSIndex(curNpcs.get(i));
				cadena += ((i > 0) ? ((i == curNpcs.size() - 1) ? " y " : " , ") : "")
						+ this.getNpcs().get(npcsIndex).toString();

			}
		}

		ArrayList<Connection> currentConnetions = currentLocationObj.getConnections();
		for (int i = 0; i < currentConnetions.size(); i++) {
			String locationConnection = currentConnetions.get(i).getLocation();
			int locationIndex = getLocationIndex(locationConnection);
			cadena += ". Al " + currentConnetions.get(i).getDirection() + " hay "
					+ this.getLocations().get(locationIndex).toString();
		}

		cadena += ".";
		return cadena;
	}

	public int getItemIndex(String itemName) {
		int i = 0;
		while (i < items.size()) {
			if (items.get(i).getName().equals(itemName)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public String verInventario() {
		String cadena = "En tu inventario hay ";
		ArrayList<String> inventory = this.getInventory();
		for (String item : inventory) {
			int itemIndex = getItemIndex(item);
			Item currentItem = this.items.get(itemIndex);
			cadena += (inventory.indexOf(item) > 0 ? inventory.indexOf(item) == inventory.size() - 1 ? ", y " : ", "
					: "") + currentItem.toString();
		}
		cadena += ".";
		return cadena;
	}

	public boolean entregarItem(int currentLocationIndex, String currentItem) {
		Location currentLocationObj = this.locations.get(currentLocationIndex);
		int i = 0;
		boolean agregado = false;
		if(currentLocationObj.eliminarItemDePlaces(currentItem)) {
			agregarItemInventario(currentItem);
			agregado = true;
		}
		return agregado;
}

	public void agregarItemInventario(String item) {
		this.inventory.add(item);
	}

	public int getLocationIndex(String locationName) {
		int i = 0;
		while (i < locations.size()) {
			if (locations.get(i).getName().equals(locationName)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public int getNPCSIndex(String nPCSName) {
		int i = 0;
		while (i < npcs.size()) {
			if (npcs.get(i).getName().equals(nPCSName)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public String darBienvenida() {
		return this.getSettings().getWelcome();
	}

	public void removeNpc(String personaje, int indexCurrentLocation) {
		// TODO Auto-generated method stub

		locations.get(indexCurrentLocation).getNPCS().remove(personaje);
		if (locations.get(indexCurrentLocation).getNPCS().size() == 0) {
			locations.get(indexCurrentLocation).setNPCS(null);
		}

		for (Connection conection : locations.get(indexCurrentLocation).getConnections()) {
			if (conection.getObstacles().equals(personaje)) {
				conection.setObstacles(null);

			}
		}

	}

}
