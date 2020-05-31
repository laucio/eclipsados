package juego_de_aventura;

import java.util.ArrayList;

import juego_de_aventura.*;

public class Player {

	Location currentLocation;
	ArrayList<Item> inventory;

	public Player(ArrayList<Item> initialInventory, Location currentLocation) {

		this.currentLocation = currentLocation;
		this.inventory = initialInventory;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	public String goTo(Action action, Adventure adventure) {

		boolean exitoso = false;
		String retorno = "No puedes ir hacia alla.";

		ArrayList<Connection> connections = currentLocation.getConnections();
		int i = 0;
		while (i < connections.size() && !exitoso) {
			if (action.IsConditionADirection() && action.hasSameDirection(connections.get(i).getDirection())) {
				exitoso = true;
				action.setCondition("location");
				action.setThing(connections.get(i).getLocation());
				
			} else if (action.isConditionALocation() && action.hasSameLocation(connections.get(i).getLocation())) {
				exitoso = true;
			}

			i++;
		}

		if (exitoso == true) {

			if (connections.get(i - 1).getObstacles() == null || connections.get(i - 1).getObstacles().equals("")) {

				Location newLocation = adventure.getLocation(connections.get(i - 1).getLocation());
				this.setCurrentLocation(newLocation);
				retorno = currentLocation.getDescription();

			} else {

				Obstacle obstacle = adventure.getObstacle(connections.get(i - 1).getObstacles());
				retorno = obstacle.getDescription();
				exitoso = false;
			}
		}

		if (exitoso) {
			action.setAchieved(true);
		}

		return retorno;
	}

	public String lookAround(Adventure adventure) {
		return currentLocation.describeItself(adventure);
	}

	public String takeItem(Action action, Adventure adventure) {
		String cadena = "No encuentro ese objeto.";

		if (currentLocation.hasItem(action.getThing())) {
			Item item = adventure.giveItem(currentLocation, action.getThing());

			if (item != null) {
				this.addToInventory(item);
				cadena = "Tienes " + item;
				action.setAchieved(true);
			}
		}

		return cadena;
	}

	public void addToInventory(Item item) {
		inventory.add(item);
	}

	public String lookInventory() {
		String cadena = "En tu inventario hay ";

		if (inventory != null && inventory.size() > 0) {

			for (int i = 0; i < inventory.size(); i++) {
				Item item = inventory.get(i);
				cadena += (i > 0 ? i == inventory.size() - 1 ? ", y " : ", " : "") + item.toString();
			}
		} else {
			cadena = "Tu inventario esta vacio";
		}

		cadena += ".";
		return cadena;
	}

	public String talkToNPC(Action action, Adventure adventure) {
		String retorno = "Nadie te respondera...";

		NPC personaje = adventure.getNPC(action.getTarget());

		if (personaje != null && currentLocation.containsNpc(personaje.getName())) {
			retorno = personaje.getTalk();
			action.setAchieved(true);
		}

		return retorno;
	}

	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	public String useItem(Action action, Adventure adventure) {
		String cadena = "No tienes ese objeto.";

		Item item = adventure.getItem(action.getThing()); 
		if (item != null && this.hasItem(item)) {

			if (item.hasEffectsOver(action)) {
				String shootableName = action.IsSelfEffect()?action.getThing():
					action.getTarget();
				Shootable shootable = adventure.findShootable(shootableName);
				
				if(shootable != null) {
					cadena = shootable.shootTrigger(action, adventure, currentLocation);
				}

			} else {
				cadena = "No ha servido de nada.";
			}
		}

		return cadena;
	}

}
