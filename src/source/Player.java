package source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	private Adventure aventura = null;
	private String currentLocation;

	public Player(String aventuraPath) throws IOException {
		this.aventura = LoadAdventure.cargarArchivo(aventuraPath);
		this.currentLocation = aventura.getLocations().get(0).getName();
	}

	public Player(Adventure aventura) {
		super();
		this.aventura = aventura;
		this.currentLocation = aventura.getLocations().get(0).getName();
	}

	public Adventure getAventura() {
		return aventura;
	}

	public void setAventura(Adventure aventura) {
		this.aventura = aventura;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getWelcome() {
		return aventura.darBienvenida();
	}

	public String goTo(Action action) {
		
		boolean exitoso = false;
		String retorno = "No se a donde quieres ir.";
		if (action.getThing().equals(this.getCurrentLocation())) {
			retorno = "Ya te encuentras aqui.";
			action.setAchieved(false);
		} else {
			if (aventura.esCaminoExistente(action)) {
				retorno = "No puedes ir hacia alla.";
			}
			exitoso = false;
			int indexCurrentLocation = aventura.getLocationIndex(this.currentLocation);
			Location currentLocation = aventura.getLocations().get(indexCurrentLocation);
			ArrayList<Connection> con = currentLocation.getConnections();
			int i = 0;
			while (i < con.size() && !exitoso) {
				if (action.getCondition().equals("direction") && action.getThing().equals(con.get(i).getDirection())) {
					exitoso = true;
					action.setCondition("location");
					action.setThing(con.get(i).getLocation());
				} else if (action.getCondition().equals("location")
						&& action.getThing().equals(con.get(i).getLocation())) {
					exitoso = true;
				}

				i++;
			}

			if (exitoso == true) {

				if (con.get(i - 1).getObstacles() == null) { // Cuando no hay obstaculos
					this.setCurrentLocation(con.get(i - 1).getLocation());
					retorno = this.getAventura().locationDescription(this.getCurrentLocation());
				} else {
					int j = this.getAventura().getNPCSIndex((con.get(i - 1).getObstacles()));/// Buscamos el indice del
																								/// obsctaculo sea un
																								/// Npcs
					if (j != -1) {// 1

						retorno = this.getAventura().getNpcs().get(j).getDescription();
						exitoso = false;
					} // 1

					if (j == -1) {// 2
						j = this.getAventura().getItemIndex((con.get(i - 1).getObstacles()));/// Buscamos el indice del
																								/// obsctaculo sea un
																								/// item
						if (j != -1) {// 3
							retorno = "¡No puedes pasar! Hay " + this.getAventura().getItems().get(j).toString();
							exitoso = false;
						} // 3
					} // 2
				}
			}
			if (exitoso) { // si no es exitoso, "cancela" la accion.
				//action = new Action();
				action.setAchieved(true);
			}
		}
		return retorno;
	}

	public String detectTriggerNPC(Action accion) {
		// ACTION -> USAR --> remove
		// CONDITION -> ITEM
		// THING -> QUE ITEM -->
		// TARGET -> sobre quien o que
		// EFFECT_OVER -> NPCS,ITEM
		String retorno = "No ha servido de nada.";
		boolean encontrado = false;
		int i = 0;
		int indexCurrentLocation = aventura.getLocationIndex(this.currentLocation);
		if (aventura.getLocations().get(indexCurrentLocation).getNPCS().contains(accion.getTarget())) {
			int indexNpcs = aventura.getNPCSIndex(accion.getTarget());
			ArrayList<Trigger> triggers = aventura.getNpcs().get(indexNpcs).getTriggers();
			if (triggers != null) {
				while (i < triggers.size() && !encontrado) {
					if (triggers.get(i).getType().equals(accion.getCondition())
							&& triggers.get(i).getThing().equals(accion.getThing())) {
						encontrado = true;
						retorno = triggers.get(i).getOn_trigger();
						accion.setAchieved(true);

						switch (triggers.get(i).getAfter_trigger()) {
						case "remove":
							aventura.removeNpc(accion.getTarget(), indexCurrentLocation);
							break;
						default:
							break;
						// case "invalidar":
						}
					}
					i++;
				}

			}

		}
		return retorno;
	}

	public String usarItem(Action action) {
		String cadena = "No tienes ese objeto.";
		boolean encontrado = false;
		ArrayList<String> inventario = this.aventura.getInventory(); // si esta en inventario ya se validó que existe.
		int i = 0;
		while (i < inventario.size() && encontrado == false) {
			if (inventario.get(i).equals(action.getThing())) {
				int itemIndex = aventura.getItemIndex(action.getThing());
				if (itemIndex >= 0) {
					if (aventura.getItems().get(itemIndex).validateAction(action)) {
						switch (action.getEffect_over()) {
						case "npcs":
							cadena = detectTriggerNPC(action);// npcs
							encontrado = true;
							break;
						case "item":
							cadena = detectTriggerItem(action); // item
							encontrado = true;
							break; // G2
						case "self":
							cadena = detectTriggerItemSelf(action); // self
							encontrado = true;
							break;
						default:
							break;
						}
					} else {
						cadena = "No ha servido de nada.";
					}
				}
			}
			i++;
		}
		return cadena;
	}

	public String tomarItem(Action action) {
		String cadena = "No encuentro ese objeto.";
		int currentLocationIndex = this.aventura.getLocationIndex(this.currentLocation);
		if (this.aventura.entregarItem(currentLocationIndex, action.getThing())) {
			int itemIndex = this.aventura.getItemIndex(action.getThing());
			cadena = "Tienes " + this.aventura.getItems().get(itemIndex);
			action.setAchieved(true);
		}
		return cadena;
	}

	public String verAlrededor() {
		return aventura.locationDescription(getCurrentLocation());
	}

	public String verInventario() {
		return aventura.listarInventario();
	}

	public String switchearAction(Action action) {
		//
		String cadena = "No entiendo que quieres hacer";
		switch (action.getAction()) {
		case "ir":
			cadena = this.goTo(action);
			break;
		case "tomar":
			cadena = this.tomarItem(action);
			break;
		case "usar":
			cadena = usarItem(action);
			break;
		case "ver alrededor":
			cadena = this.verAlrededor();
			break;
		case "ver inventario":
			cadena = this.verInventario();
			break;
		case "hablar":
			cadena = this.hablarConNPC(action);
			break;
		default:
			break;

		}

		return cadena;
	}

	private String hablarConNPC(Action action) {
		String retorno = "Nadie te respondera...";
		int currentLocationIndex = this.getAventura().getLocationIndex(this.getCurrentLocation());
		ArrayList<String> npcs = this.getAventura().getLocations().get(currentLocationIndex).getNPCS();
		if (npcs != null && npcs.size() > 0) {
			int i = 0;
			while (i >= 0 && i < npcs.size()) {
				if (npcs.get(i).equals(action.getTarget())) {
					int npcsIndex = getAventura().getNPCSIndex(action.getTarget());
					retorno = getAventura().getNpcs().get(npcsIndex).hablar();
					i = -1;
				} else {
					i++;
				}

			}
		}
		return retorno;
	}

	private String detectTriggerItemSelf(Action action) {
		String retorno = "No ha servido de nada.";
		boolean encontrado = false;
		int i = 0;

		int indexItem = aventura.getItemIndex(action.getThing());
		ArrayList<Trigger> triggers = aventura.getItems().get(indexItem).getTriggers();
		if (triggers != null) {
			while (i < triggers.size() && !encontrado) {
				if (triggers.get(i).getType().equals(action.getCondition())
						&& triggers.get(i).getThing().equals(action.getThing())) {
					encontrado = true;
					retorno = triggers.get(i).getOn_trigger();
					action.setAchieved(true);

					switch (triggers.get(i).getAfter_trigger()) {
					case "remove":
						// Quitar item de inventario y, TAL VEZ, dejarlo en location/place
						/// aventura.getLocations().get(indexCurrentLocation).eliminarItemDePlaces(accion.getTarget());
						break;
					case "default":
						// implica que lo deja en inventario
						break;
					default:
						break;
					// case "invalidar":
					}
				}

				i++;
			}
		}

		return retorno;
	}

	public String detectTriggerItem(Action accion) {
		String retorno = "No ha servido de nada.";
		boolean encontrado = false;
		int i = 0;
		int indexCurrentLocation = aventura.getLocationIndex(this.currentLocation);
		if (aventura.getLocations().get(indexCurrentLocation).hasItem(accion.getTarget())
				|| this.getAventura().getInventory().contains(accion.getTarget())) {
			int indexItem = aventura.getItemIndex(accion.getTarget());
			ArrayList<Trigger> triggers = aventura.getItems().get(indexItem).getTriggers();
			if (triggers != null) {
				while (i < triggers.size() && !encontrado) {
					if (triggers.get(i).getType().equals(accion.getCondition())
							&& triggers.get(i).getThing().equals(accion.getThing())) {
						encontrado = true;
						retorno = triggers.get(i).getOn_trigger();
						accion.setAchieved(true);

						switch (triggers.get(i).getAfter_trigger()) {
						case "remove":
							aventura.getLocations().get(indexCurrentLocation).eliminarItemDePlaces(accion.getTarget());
							aventura.eliminarItemDeInventario(accion.getTarget());
							break;
						default:
							break;
						// case "invalidar":
						}
					}
					i++;
				}
			}

		}

		else {
			retorno = "No encuentro el objeto.";
		}

		return retorno;
	}
	
	public String processAction(Action action) {
		String retorno = this.switchearAction(action);
		
		if(action.isAchieved()) {
			ArrayList<Endgame> endgames = this.aventura.getEndgames();
			int i=0;
			boolean esEndgame = false;
			while (i < endgames.size() && !esEndgame) {
				esEndgame = endgames.get(i).esEndgame(action);
				i++;
			}
			if (esEndgame) {
				retorno = endgames.get(i-1).getDescription() + "\nFIN.";													
			}
		}
		
	return retorno;
	}
	
}
