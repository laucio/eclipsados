package juego_de_aventura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Adventure {
	private Setting settings;
	private ArrayList<Location> locations = null;
	private ArrayList<NPC> npcs = null;
	private ArrayList<Item> items = null;
	private ArrayList<String> inventory = null;
	private ArrayList<Endgame> endgames = null;

	public Adventure() {

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

	public ArrayList<NPC> getNpcs() {
		return npcs;
	}

	public void setNpcs(ArrayList<NPC> npcs) {
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

	public Location getInitialLocation() {
		return locations.get(0);
	}

	public ArrayList<Item> getInitialInventory() {
		ArrayList<Item> initialInventory = new ArrayList<Item>();

		for (String tool : this.inventory) {
			initialInventory.add(this.getItem(tool));
		}

		return initialInventory;
	}

	public Item getItem(String itemName) {
		Item retorno = null;
		boolean found = false;
		int i = 0;
		while (i < items.size() && !found) {
			if (items.get(i).isNamed(itemName)) {
				retorno = items.get(i);
				found = true;
			}
			i++;
		}
		return retorno;
	}

	public Location getLocation(String locationName) {
		Location retorno = null;
		boolean found = false;
		int i = 0;
		while (i < locations.size() && !found) {
			if (locations.get(i).isNamed(locationName)) {
				retorno = locations.get(i);
				found = true;
			}
			i++;
		}
		return retorno;
	}

	public NPC getNPC(String npcName) {
		NPC retorno = null;
		boolean found = false;
		int i = 0;
		while (i < npcs.size() && !found) {
			if (npcs.get(i).isNamed(npcName)) {
				retorno = npcs.get(i);
				found = true;
			}
			i++;
		}
		return retorno;
	}

	public Obstacle getObstacle(String obstacleName) {
		Obstacle retorno = null;
		boolean found = false;

		int i = 0;
		while (i < npcs.size() && !found) {
			if (npcs.get(i).isNamed(obstacleName)) {
				retorno = npcs.get(i);
				found = true;
			}
			i++;
		}

		if (!found) {
			i = 0;
			while (i < items.size() && !found) {
				if (items.get(i).isNamed(obstacleName)) {
					retorno = items.get(i);
					found = true;
				}
				i++;
			}
		}

		return retorno;
	}


	public Item giveItem(Location currentLocation, String currentItem) {
		Item retorno = null;
		if (currentLocation.removeFromLocation(currentItem)) {
			retorno = this.getItem(currentItem);
		}
		return retorno;
	}

	public String darBienvenida() {
		return this.getSettings().getWelcome();
	}

	/*public void removeNpc(String personaje, Location location) {
		location.removeNpc(personaje);
	}*/

	

	public void eliminarItemDeInventario(String target) {
		this.getInventory().remove(target);

	}

	public Shootable findShootable(String name) {
		Shootable retorno = null;

		boolean found = false;

		int i = 0;
		while (i < npcs.size() && !found) {
			if (npcs.get(i).isNamed(name)) {
				retorno = npcs.get(i);
				found = true;
			}
			i++;
		}

		if (!found) {
			i = 0;
			while (i < items.size() && !found) {
				if (items.get(i).isNamed(name)) {
					retorno = items.get(i);
					found = true;
				}
				i++;
			}
		}

		return retorno;
	}

	public boolean removeItemFromLocation(Action action, Adventure adventure, Location location) {
		String item = action.IsSelfEffect() ? action.getThing() : action.getTarget();
		boolean eliminado = location.removeFromLocation(item);
		return eliminado;
	}

	public String containsNPC(String cadena) {
		String retorno = null;
		boolean found = false;
		int i = 0;
		
		while(i < npcs.size() && !found) {
			if(cadena.contains(npcs.get(i).getName())) {
				retorno = npcs.get(i).getName();
				found = true;
			}
			i++;
		}
		
		return retorno;
	}

	public String containsItem(String cadena) {
		String retorno = null;
		
		boolean found = false;
		int i = 0;
		
		while(i < items.size() && !found) {
			if(cadena.contains(items.get(i).getName())) {
				retorno = items.get(i).getName();
				found = true;
			}
			i++;
		}
		
		return retorno;
	}
	
	public void ItemsOcurrences(String cadena, ArrayList<String> objs) {
		int cant = 0;
		int i = 0;
		
		while(i < items.size() && cant<2) {
			if(cadena.contains(items.get(i).getName())) {
				objs.add(items.get(i).getName());
				cant++;
			}
			i++;
		}
	}

	public Location getMentionedLocation(String cadena) {
		Location retorno = null;
		
		boolean found = false;
		int i = 0;
		
		while(i < locations.size() && !found) {
			if(cadena.contains(locations.get(i).getName())) {
				retorno = locations.get(i);
				found = true;
			}
			i++;
		}
		
		return retorno;
	}

}
