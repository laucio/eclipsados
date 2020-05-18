package source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	private Adventure aventura = null;
	private String currentLocation;

	private final String[] VERBO_IR = { " ir ", " ve ", " camina ", " caminar ", " movete ", " moverse " }; // moverse
																											// de
																											// location
	private final String[] VERBO_TOMAR = { " tomar ", " agarrar ", " levantar ", " guardar ", " toma ", " garra ",
			" levanta ", " guarda ", " recoger ", " recoge " }; // agarrar items
	private final String[] VERBO_USAR = { " usar ", " usa ", " utilizar ", " utiliza " }; // usar barreta por ejemplo

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

	public String darBienvenida() {
		return aventura.darBienvenida();
	}

	/*
	 * public void comandoDeUsuario(String comando) { String action; String thing;
	 * String condition;
	 * intepretarComando(action,thing,condition,aventura.getInventory())
	 * 
	 * }
	 */

	public boolean traducir(String comando, Action obj) {
		/*
		 * //Verificamos que sea un movimienot String = "No entiendo lo que me dices" si
		 * verbo incluido en VERBO_IR SI destino este ARRRAYLIST DE CONEXIONES
		 * 
		 * ELSE IF (verbo_tomar) Si el objecto este en ArrayList De items de place ELSE
		 * IF (verbo_usar) Si el objeto este en el array de Inventario
		 */
		comando = " " + comando + " ";
		comando = comando.toLowerCase();

		boolean encontrado = false;
		boolean traducido = false;
		int i = 0;
		while (!encontrado && i < VERBO_IR.length) {
			encontrado = comando.contains(VERBO_IR[i]);
			i++;
		}
		if (encontrado) {
			obj.setAction("ir");
			encontrado = false;
			i = 0;
			int currentLocationIndex = aventura.getLocationIndex(this.getCurrentLocation());
			ArrayList<Connection> currConnections = aventura.getLocations().get(currentLocationIndex).getConnections();
			while (!encontrado && i < currConnections.size()) {
				if (encontrado = comando.contains(" " + currConnections.get(i).getDirection() + " ")) {
					obj.setCondition("direction");
					obj.setThing(currConnections.get(i).getDirection());
					traducido = true;
				} else {
					if (encontrado = comando.contains(" " + currConnections.get(i).getLocation() + " ")) {
						obj.setCondition("location");
						obj.setThing(currConnections.get(i).getLocation());
					} else {
						obj.setMessage("No entiendo donde quieres ir.");

					}
				}
				i++;
			}
			traducido = true;
		} else {
			obj.setMessage("No entiendo que quieres decir.");
		}
		// Fin de validacion de movimiento

		// Inicio validacion tomar objeto

		if (!traducido) {
			i = 0;
			while (!encontrado && i < VERBO_TOMAR.length) {
				encontrado = comando.contains(VERBO_TOMAR[i]);
				i++;
			}
			if (encontrado) {
				i = 0;
				encontrado = false;
				int currentLocationIndex = aventura.getLocationIndex(this.getCurrentLocation());
				ArrayList<Place> currPlaces = aventura.getLocations().get(currentLocationIndex).getPlaces();
				if (currPlaces != null) {
					while (!encontrado && i < currPlaces.size()) {
						int j = 0;
						ArrayList<String> currItems = currPlaces.get(i).getItems();
						while (!encontrado && j < currItems.size()) {
							encontrado = comando.contains(" " + currItems.get(j) + " "); // agrego espacios por si busca
																							// pala y escribio paladar
							if (encontrado) {
								obj.setCondition("item");
								obj.setAction("tomar");
								obj.setThing(currItems.get(j));
							}
							j++;
						} // while(!encontrado && j<currPlaces.get(i).getItems())
						i++;
					} // end of while
					if (!encontrado) {
						obj.setMessage("No encuentro eso que quieres tomar.");
					}
				} else {// if
					obj.setMessage("No hay nada que puedas tomar aqui.");
				} // if (currPlaces != null)

			} // if (encontrado)
		}
		/// Fin buscar tomar objeto

		/// Inicio usar objeto
		if (!traducido) {
			i = 0;
			while (!encontrado && i < VERBO_USAR.length) {
				encontrado = comando.contains(VERBO_USAR[i]);
				i++;
			}
			if (encontrado) {
				obj.setAction("usar");
				encontrado = false;
				i = 0;

			}
		}

		return encontrado;
	}

	public String goTo(Action action) {
		String retorno = "No se a donde quieres ir.";
		boolean exitoso = false;
		int indexCurrentLocation = aventura.getLocationIndex(this.currentLocation);
		Location currentLocation = aventura.getLocations().get(indexCurrentLocation);
		//
		ArrayList<Connection> con = currentLocation.getConnections();
		int i = 0;
		while (i < con.size() && !exitoso) {
			if (action.getCondition().equals("direction") && action.getThing().equals(con.get(i).getDirection())) {
				exitoso = true;
			} else if (action.getCondition().equals("location") && action.getThing().equals(con.get(i).getLocation())) {
				exitoso = true;
			}

			i++;
		}

		if (exitoso == true) {

			if (con.get(i - 1).getObstacles() == null) { // Cuando no hay obstaculos
				this.setCurrentLocation(con.get(i - 1).getLocation());
				retorno = this.getAventura().locationDescription(this.getCurrentLocation());
				// System.out.println(this.getAventura().verAlrededor(this.getCurrentLocation()));
			} else {
				int j = this.getAventura().getNPCSIndex((con.get(i - 1).getObstacles()));/// Buscamos el indice del
																							/// obsctaculo sea un Npcs
				if (j != -1) {// 1

					retorno = this.getAventura().getNpcs().get(j).getDescription();
					exitoso = false;
				} // 1

				if (j == -1) {// 2
					j = this.getAventura().getItemIndex((con.get(i - 1).getObstacles()));/// Buscamos el indice del
																							/// obsctaculo sea un item
					if (j != -1) {// 3
						retorno = "¡No puedes pasar! Hay " + this.getAventura().getItems().get(j).toString();
						exitoso = false;
					} // 3
				} // 2
			}
		}
		if (!exitoso) { // si no es exitoso, "cancela" la accion.
			action = new Action();
		}
		return retorno;
	}
	/*
	 * public String detectTriggerItem(Action accion) { String retorno =
	 * "No ha servido de nada."; boolean encontrado = false; int i = 0; int
	 * indexCurrentLocation = aventura.getLocationIndex(this.currentLocation); if()
	 * }
	 */

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

						switch (triggers.get(i).getAfter_trigger()) {
						case "remove":
							aventura.removeNpc(accion.target, indexCurrentLocation);
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

	public String detectTriggerItem(Action accion) {
		// ACTION -> USAR --> remove
		// CONDITION -> ITEM
		// THING -> QUE ITEM -->
		// TARGET -> sobre quien o que
		// EFFECT_OVER -> NPCS,ITEM
		String retorno = "No ha servido de nada.";
		boolean encontrado = false;
		int i = 0;
		int indexCurrentLocation = aventura.getLocationIndex(this.currentLocation);
		if (aventura.getLocations().get(indexCurrentLocation).hasItem(accion.getTarget())) {
			int indexItem = aventura.getItemIndex(accion.getTarget());
			ArrayList<Trigger> triggers = aventura.getItems().get(indexItem).getTriggers();
			if (triggers != null) {
				while (i < triggers.size() && !encontrado) {
					if (triggers.get(i).getType().equals(accion.getCondition())
							&& triggers.get(i).getThing().equals(accion.getThing())) {
						encontrado = true;
						retorno = triggers.get(i).getOn_trigger();

						switch (triggers.get(i).getAfter_trigger()) {
						case "remove":
							aventura.getLocations().get(indexCurrentLocation).eliminarItemDePlaces(accion.getTarget());
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
			retorno = "No encuentro el objeto."; // agregar lo ver el inventario
		}
		return retorno;
	}

	public String tomarItem(String item) {
		String cadena = "No encuentro ese objeto.";
		int currentLocationIndex = this.aventura.getLocationIndex(this.currentLocation);
		if (this.aventura.entregarItem(currentLocationIndex, item)) {
			int itemIndex = this.aventura.getItemIndex(item);
			cadena = "Tienes " + this.aventura.getItems().get(itemIndex);
		}
		return cadena;
	}
	/*
	 * for (Trigger x : ) { if(x.getType().equals(accion.getCondition()) &&
	 * x.getThing().equals(accion.getThing())) {
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

	public String verAlrededor() {
		return aventura.locationDescription(getCurrentLocation());
	}

	public ResultadoInstruccion recibirInstruccion(String comando) { // Devuelve false cuando sea ENDGAME
		String cadena;
		Action action = new Action();
		if (!Translator.traducir(comando, action, this)) {
			cadena = (action.getMessage());
		} else {
			cadena = switchearAction(action); // ACÁ EMPIEZA LA PARTE DE ENDGAME
		}
		ArrayList<Endgame> endgames = aventura.getEndgames();
		boolean esEndgame = false;
		int i = 0;
		while (i < endgames.size() && !esEndgame) {
			esEndgame = endgames.get(i).esEndgame(action);
			i++; // G2
		}
		if (esEndgame) {
			cadena += "\n" + endgames.get(i).getDescription();
		}
		return new ResultadoInstruccion(esEndgame, cadena); /*
															 * devuelve si es fin de juego o no, junto con el mensaje
															 * que se muestra por pantalla.
															 */

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
			cadena = this.tomarItem(action.getThing());
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

	public String usarItem(Action action) {
		String cadena = "No tienes ese objeto.";
		boolean encontrado = false;
		ArrayList<String> inventario = this.aventura.getInventory(); // si esta en inventario ya se validó que existe.
		int i = 0;
		while (i < inventario.size() && encontrado == false) {
			if (inventario.get(i).equals(action.getThing())) {
				int itemIndex = aventura.getItemIndex(action.thing);
				if (itemIndex >= 0 && aventura.getItems().get(itemIndex).validateAction(action)) {
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
				}
			}
			i++;
		}
		return cadena;
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

					switch (triggers.get(i).getAfter_trigger()) {
					case "remove":
						// Quitar item de inventario y, TAL VEZ, dejarlo en location/place
						/// aventura.getLocations().get(indexCurrentLocation).eliminarItemDePlaces(accion.getTarget());
						break;
					case "default":
						//implica que lo deja en inventario
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
}