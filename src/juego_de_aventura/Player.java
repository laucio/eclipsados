package juego_de_aventura;

import java.util.ArrayList;

import juego_de_aventura.*;

public class Player {

	private Location currentLocation;
	private ArrayList<Item> inventory;
	private int hitPoints = 100;
	private int commandCounter = 0;
	private String imageName;

	public Player(ArrayList<Item> initialInventory, Location currentLocation) {

		this.currentLocation = currentLocation;
		this.inventory = initialInventory;
		this.imageName = "Locations/"+currentLocation.getName();
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
	
	public int getCommandCounter() {
		return commandCounter;
	}

	public String goTo(Action action, Adventure adventure) {

		boolean exitoso = false;
		String retorno = "No puedes ir hacia alla.";
		this.setImageName("Fails/goto");

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
				this.setImageName("Locations/"+this.currentLocation.getName());
				retorno = currentLocation.getDescription();

			} else {

				Obstacle obstacle = adventure.getObstacle(connections.get(i - 1).getObstacles());
				this.setImageName("Obstacles/"+obstacle.getName());
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
		this.setImageName("Locations/"+currentLocation.getName());
		return currentLocation.describeItself(adventure);
	}

	public String takeItem(Action action, Adventure adventure) {
		this.setImageName("Fails/confused");
		String cadena = "No encuentro ese objeto. Tal vez lo tengas en tu inventario";

		if (!action.isUnknownThing()) {
			Item item = adventure.getItem(action.getThing());
			if (item != null && !item.allowsAction(action.getAction())) {
				this.setImageName("Fails/no");
				cadena = "No puedes tomar eso";
			} else {

				if (item != null) {
					this.setImageName("Items/"+item.getName()+"/"+item.getName());
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
		this.setImageName("inventario");
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
		this.setImageName("Fails/laugh");

		NPC personaje = adventure.getNPC(action.getTarget());

		if (personaje != null && currentLocation.containsNpc(personaje.getName())) {
			retorno = personaje.getTalk(this);
			action.setAchieved(true);
		}

		return retorno;
	}

	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	public String useItem(Action action, Adventure adventure) {
		String cadena = "No tienes ese objeto.";
		this.setImageName("Fails/laugh");

		Item item = adventure.getItem(action.getThing());

		if (item != null && this.hasItem(item)) {
			this.setImageName("Items/"+item.getName()+"/"+item.getName());

			if (item.hasEffectsOver(action)) {
				String shootableName = action.IsSelfEffect() ? action.getThing() : action.getTarget();

				Shootable shootable = adventure.findShootable(shootableName);

				if (shootable != null) {
					action.setShooteable(shootableName);
					cadena = shootable.shootTrigger(action, adventure, this);
				}

			} else {
				cadena = "No ha servido de nada.";
				this.setImageName("Fails/trigger-fail");
			}
		}

		return cadena;
	}

	public String attack(Action action, Adventure adventure) {
		String cadena = "No ha servido de nada.";
		Item item = adventure.getItem(action.getThing());
		this.setImageName("Fails/laugh");

		if (item != null && item.allowsAction(action.getAction())) {
			this.setImageName("Fails/laugh");
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
					this.setImageName("Fails/trigger-fail");
				}
			}

		}

		return cadena;
	}
	
	public String observeNpc(Action action, Adventure adventure) {
		String retorno;
		NPC npc = adventure.getNPC(action.getThing());
		
		if(currentLocation.containsNpc(npc.getName())) {
			this.setImageName("NPCs/"+npc.getName()+"/"+npc.getName());
			retorno = npc.getDescription();	
		}else {
			this.setImageName("Fails/notFoundNPC");
			retorno = "No encuentro a quien buscas... Se mas claro!";
		}
		
	return retorno;
	}

	public String observeItem(Action action, Adventure adventure) {
		String cadena = "No entiendo que es lo que quieres mirar";
		this.setImageName("Fails/clueless");

		Item item = adventure.getItem(action.getThing());

		if (item != null && !action.isUnknownThing()) {
			action.setShooteable(item.getName());

			if (this.hasItem(item) || this.isNearItem(action.getThing())) {

				if (item.hasEffectsOver(action) && item.allowsAction(action.getAction())) {
					String shootableName = action.getThing();
					Shootable shootable = adventure.findShootable(shootableName);

					if (shootable != null) {
						this.setImageName("Items/"+item.getName()+"/"+item.getName());
						cadena = shootable.shootTrigger(action, adventure, this);
					}

				} else {
					this.setImageName("Items/"+item.getName()+"/"+item.getName());
					action.setAchieved(true);
					cadena = "No tiene nada en especial";
				}
			} else {
				this.setImageName("Fails/confused");
				cadena = "No encuentro eso que quieres mirar";
			}
		} else {
			if (action.isConditionAPlace()) {
				Place place = this.currentLocation.getPlace(action.getThing());
				setImageName("Locations/"+currentLocation.getName());
				action.setAchieved(true);

				if (place.hasItems()) {
					cadena = "En ";

					cadena += place.toString() + " hay: ";
					for (int j = 0; j < place.getItems().size(); j++) {
						String itemName = place.getItems().get(j);
						Item itemObj = adventure.getItem(itemName);
						cadena += ((j > 0) ? ((j == place.getItems().size() - 1) ? " y " : " , ") : "") + itemObj;

					}
				} else {
					this.setImageName("Fails/laugh");
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
		this.setImageName("Fails/think");

		switch (action.getCondition()) {
		case "unknown":
			this.setImageName("Fails/confused");
			cadena = "No entiendo que es lo que quieres comer";
			break;
		case "item":
			Item item = adventure.getItem(action.getThing());
			if (this.hasItem(item)) {
				if (item.allowsAction(action.getAction())) {
					action.setAction("usar");
					action.setShooteable(action.getThing());
					this.setImageName("Items/"+action.getThing()+action.getThing());
					cadena = this.useItem(action, adventure);
				} else {
					this.setImageName("Fails/trigger-fail");
					cadena = "No puedes comer eso";
				}
			}
			break;
		case "tooManyThings":
			this.setImageName("Fails/confused");
			cadena = "Aclarame que quieres comer primero";
			break;
		default:
			break;
		}

		return cadena;
	}

	public String openSomething(Action action, Adventure adventure) {
		String cadena = "No ha servido de nada";
		this.setImageName("Fails/laugh");

		if (action.isUnknownThing()) {
			this.setImageName("Fails/confused");
			cadena = "No se que es lo que quieres abrir";
		} else {

			if(action.isConditionAPlace()) {
				this.setImageName("Fails/takeItem");
				cadena = "Ya toma un maldito item!";
				
			}else {
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
							this.setImageName("Fails/confused");
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
					this.setImageName("Fails/no");
					cadena = "No puedes hacer eso";
				}
			}
		}
		return cadena;
	}

	public String drink(Action action, Adventure adventure) {
		String cadena = "Para beber algo, primero debes tomarlo";
		this.setImageName("Fails/think");

		switch (action.getCondition()) {
		case "unknown":
			this.setImageName("Fails/confused");
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
					this.setImageName("Fails/no");
					cadena = "No puedes beber eso";
				}
			}
			break;
		case "tooManyThings":
			this.setImageName("Fails/confused");
			cadena = "Aclarame que quieres beber primero";
			break;
		default:
			break;
		}

		return cadena;
	}

	public String leavelocation(Action action) {
		this.setImageName("Fails/where");
		return currentLocation.isNamed(action.getThing()) ? "Debes decirme hacia donde quieres ir"
				: "No puedes irte de un lugar en el que no te encuentras";
	}

	public String leaveItem(Action action, Adventure adventure) {
		String cadena = "No puedes hacer eso";
		this.setImageName("Fails/no");
		switch (action.getCondition()) {
		case "unknown":
			this.setImageName("Fails/confused");
			cadena = "No entiendo que es lo que quieres dejar";
			break;
		case "tooManyThings":
			this.setImageName("Fails/confused");
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
					this.setImageName("Items/dejar_objeto");
				} else {
					this.setImageName("Fails/no");
					cadena = "Parece que no puedes deshacerte de eso...";
				}
			} else {
				this.setImageName("Fails/confused");
				cadena = "No encuentro nada parecido en tu inventario... Deberias revisarlo";
			}
			break;
		}

		return cadena;
	}
	
	public String breakSomething(Action action, Adventure adventure) {
		String cadena = "No ha servido de nada.";
		Item item = adventure.getItem(action.getThing());
		this.setImageName("Fails/trigger-fail");
		
		if(action.isUnknownTarget() && action.isUnknownTarget()) {
			this.setImageName("Fails/confused");
			cadena = "No entiendo que quieres romper";
		}else {
			if (item != null && item.allowsAction(action.getAction())) {
				
				if (this.isNearItem(item.getName()) || this.hasItem(item)) {

					if (item.hasEffectsOver(action)) {
						String shootableName = action.getTarget();
						Shootable shootable = adventure.findShootable(shootableName);
						

						if (shootable != null) {
							this.setImageName("Fails/laugh");
							action.setShooteable(shootableName);
							cadena = shootable.shootTrigger(action, adventure, this);
						}

					} else {
						cadena = "No ha servido de nada.";
						this.setImageName("Fails/laugh");
					}
				}

			}

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

	public void increaseCommandCounter() {
		commandCounter++;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public boolean isNearMentionedPlace(String cadena) {
		boolean found = false;
		ArrayList<Place> places = currentLocation.getPlaces();
		int i=0;
		
		while(i<places.size() && !found) {
			if(cadena.contains(places.get(i).getName())) {
				found = true;
			}
		i++;
		}
		
	return found;
	}

}
