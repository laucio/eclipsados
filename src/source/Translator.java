package source;

import java.util.ArrayList;

public class Translator {
	private final static String[] VERBO_IR = { " ir ", " ve ", " camina ", " caminar ", " movete ", " moverse " }; // moverse
	// de
	// location
	private final static String[] VERBO_TOMAR = { " tomar ", " agarrar ", " levantar ", " guardar ", " toma ", " garra ",
			" levanta ", " guarda ", " recoger ", " recoge " }; // agarrar items
	private final static String[] VERBO_USAR = { " usar ", " usa ", " utilizar ", " utiliza " }; // usar barreta por ejemplo

	
	public static boolean traducir(String comando, Action obj,Player player) {
		/*
		 * //Verificamos que sea un movimienot String = "No entiendo lo que me dices" si
		 * verbo incluido en VERBO_IR SI destino este ARRRAYLIST DE CONEXIONES
		 * 
		 * ELSE IF (verbo_tomar) Si el objecto este en ArrayList De items de place ELSE
		 * IF (verbo_usar) Si el objeto este en el array de Inventario
		 */
		comando = " " + comando + " ";
		comando = comando.toLowerCase();
		
		Adventure aventura = player.getAventura();

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
			int currentLocationIndex = aventura.getLocationIndex(player.getCurrentLocation());
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
				int currentLocationIndex = aventura.getLocationIndex(player.getCurrentLocation());
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
/*		if (!traducido) {
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
		}*/

		return encontrado;
	}
}
