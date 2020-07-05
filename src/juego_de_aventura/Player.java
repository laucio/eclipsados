package juego_de_aventura;

import java.util.ArrayList;

import juego_de_aventura.*;

public class Player {

	private Location currentLocation;
	private ArrayList<Item> inventory;
	private int hitPoints = 100;

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

	public int getHitPoints() {
		return hitPoints;
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
		String cadena = "No encuentro ese objeto. Tal vez lo tengas en tu inventario";

		if (!action.isUnknownThing()) {
			Item item = adventure.getItem(action.getThing());
			if (item != null && !item.allowsAction(action.getAction())) {
				cadena = "No puedes tomar eso";
			} else {

				if (item != null) {
					item = adventure.giveItem(currentLocation, action.getThing());
					this.addToInventory(item);
					cadena = "Tienes " + item;
					action.setAchieved(true);
				}
			}
		}

		return cadena;
	}

	public void removeItemFromInventory(Item item) {
		inventory.remove(item);
	}

	public void addToInventory(Item item) {
		inventory.add(item);
	}

	public String lookInventory() {
		String cadena = "En tu inventario hay ";

		if (inventory != null && inventory.size() > 0) {

			for (int i = 0; i < inventory.size(); i++) {
				Item item = inventory.get(i);
				cadena += (i > 0 ? i == inventory.size() - 1 ? " y " : ", " : "") + item.toString();
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
				String shootableName = action.IsSelfEffect() ? action.getThing() : action.getTarget();

				Shootable shootable = adventure.findShootable(shootableName);

				if (shootable != null) {
					action.setShooteable(shootableName);
					cadena = shootable.shootTrigger(action, adventure, this);
				}

			} else {
				cadena = "No ha servido de nada.";
			}
		}

		return cadena;
	}

	public String attack(Action action, Adventure adventure) {
		String cadena = "No ha servido de nada.";
		Item item = adventure.getItem(action.getThing());

		if (item != null && item.allowsAction(action.getAction())) {
			cadena = "No tienes ese objeto.";

			if (this.hasItem(item)) {

				if (item.hasEffectsOver(action)) {
					String shootableName = action.getTarget();
					Shootable shootable = adventure.findShootable(shootableName);

					if (shootable != null) {
						action.setShooteable(shootableName);
						cadena = shootable.shootTrigger(action, adventure, this);
					}

				} else {
					cadena = "No ha servido de nada.";
				}
			}

		}

		return cadena;
	}

	public String observeItem(Action action, Adventure adventure) {
		String cadena = "No entiendo que es lo que quieres mirar";

		Item item = adventure.getItem(action.getThing());

		if (item != null && !action.isUnknownThing()) {

			if (this.hasItem(item) || this.isNearItem(action.getThing())) {

				if (item.hasEffectsOver(action) && item.allowsAction(action.getAction())) {
					String shootableName = action.getThing();
					Shootable shootable = adventure.findShootable(shootableName);

					if (shootable != null) {
						cadena = shootable.shootTrigger(action, adventure, this);
					}

				} else {
					cadena = "No tiene nada en especial";
				}
			} else {
				cadena = "No encuentro eso que quieres mirar";
			}
		} else {
			if (action.isConditionAPlace()) {
				Place place = this.currentLocation.getPlace(action.getThing());

				if (place.hasItems()) {
					cadena = "En ";

					cadena += place.toString() + " hay: ";
					for (int j = 0; j < place.getItems().size(); j++) {
						String itemName = place.getItems().get(j);
						Item itemObj = adventure.getItem(itemName);
						cadena += ((j > 0) ? ((j == place.getItems().size() - 1) ? " y " : " , ") : "") + itemObj;

					}
				} else {
					cadena = "No hay nada de raro en " + place.getDescription();
				}

			}
		}

		return cadena;
	}

	private boolean isNearItem(String item) {
		return currentLocation.hasItem(item) || currentLocation.hasObstacle(item);
	}

	public String eat(Action action, Adventure adventure) {
		String cadena = "Para comer algo, primero debes tomarlo";

		switch (action.getCondition()) {
		case "unknown":
			cadena = "No entiendo que es lo que quieres comer";
			break;
		case "item":
			Item item = adventure.getItem(action.getThing());
			if (this.hasItem(item)) {
				if (item.allowsAction(action.getAction())) {
					action.setAction("usar");
					action.setShooteable(action.getThing());
					cadena = this.useItem(action, adventure);
				} else {
					cadena = "No puedes comer eso";
				}
			}
			break;
		case "tooManyThings":
			cadena = "Aclarame que quieres comer primero";
			break;
		default:
			break;
		}

		return cadena;
	}

	public String openSomething(Action action, Adventure adventure) {
		String cadena = "No ha servido de nada";

		if (action.isUnknownThing()) {
			cadena = "No se que es lo que quieres abrir";
		} else {

			Item item = adventure.getItem(action.getThing());

			if (item.allowsAction(action.getAction())) {

				switch (action.getEffect_over()) {
				case "self":
					if (this.isNearItem(item.getName()) || this.hasItem(item)) {

						if (item.hasEffectsOver(action)) {
							String shootableName = action.getThing();
							Shootable shootable = adventure.findShootable(shootableName);

							if (shootable != null) {
								action.setShooteable(shootableName);
								cadena = shootable.shootTrigger(action, adventure, this);
							}

						}
					} else {
						cadena = "No encuentro eso que quieres abrir";
					}

					break;

				case "item":
					action.setAction("usar");
					action.setShooteable(action.getTarget());
					cadena = this.useItem(action, adventure);
					break;
				default:
					break;
				}

			} else {
				cadena = "No puedes hacer eso";
			}
		}
		return cadena;
	}

	public String drink(Action action, Adventure adventure) {
		String cadena = "Para beber algo, primero debes tomarlo";

		switch (action.getCondition()) {
		case "unknown":
			cadena = "No entiendo que es lo que quieres beber";
			break;
		case "item":
			Item item = adventure.getItem(action.getThing());
			if (this.hasItem(item)) {
				if (item.allowsAction(action.getAction())) {
					action.setAction("usar");
					action.setShooteable(action.getThing());
					cadena = this.useItem(action, adventure);
				} else {
					cadena = "No puedes beber eso";
				}
			}
			break;
		case "tooManyThings":
			cadena = "Aclarame que quieres beber primero";
			break;
		default:
			break;
		}

		return cadena;
	}

	public String leavelocation(Action action) {
		return currentLocation.isNamed(action.getThing()) ? "Debes decirme hacia donde quieres ir"
				: "No puedes irte de un lugar en el que no te encuentras";
	}

	public String leaveItem(Action action, Adventure adventure) {
		String cadena = "No puedes hacer eso";

		switch (action.getCondition()) {
		case "unknown":
			cadena = "No entiendo que es lo que quieres dejar";
			break;
		case "tooManyThings":
			cadena = "Especifica solamente lo que quieres dejar (solo una cosa)";
			break;

		case "item":

			Item item = adventure.getItem(action.getThing());

			if (this.hasItem(item)) {
				if (item.allowsAction(action.getAction())) {
					currentLocation.placeItem(item);
					removeItemFromInventory(item);
					cadena = "Ya no tienes " + item;
					action.setAchieved(true);
				} else {
					cadena = "Parece que no puedes deshacerte de eso...";
				}
			} else {
				cadena = "No encuentro nada parecido en tu inventario... Deberias revisarlo";
			}
			break;
		}

		return cadena;
	}

	public String restart(Location location) {
		this.hitPoints = 50;
		this.currentLocation = location;
		return "Has vuelto al principio. " + printHitPoints();
	}

	public String alterHitPoints(int points) {
		String retorno = "";
		if ((this.hitPoints += points) <= 0) {
			retorno = "restart";
		} else {
			if (this.hitPoints > 100) {
				this.hitPoints = 100;
			}
			retorno = (points < 0 ? "Has perdido " : "Has ganado ") + Integer.toString(Math.abs(points))
					+ " puntos de vida.\n" + printHitPoints();
		}
		return retorno;
	}

	public String printHitPoints() {
		return "Tus puntos de vida ahora son: " + Integer.toString(this.hitPoints);
	}

}
