package source;

import java.io.IOException;
import java.util.ArrayList;

public class Partida {
	private AventuraZork aventura = null;
	private String currentLocation;

	private final String[] VERBO_IR = { "ir", "ve", "camina", "caminar", "movete", "moverse" }; // moverse de location
	private final String[] VERBO_TOMAR = { "tomar", "agarrar", "levantar", "guardar", "toma", "agarra", "levanta",
			"guarda" }; // agarrar items
	private final String[] VERBO_USAR = { "usar", "usa", "utilizar", "utiliza" }; // usar barreta por ejemplo

	public Partida(String aventuraPath) throws IOException {
		this.aventura = CargaAventura.cargarArchivo(aventuraPath);
		this.currentLocation = aventura.getLocations().get(0).getName();

	}

	public AventuraZork getAventura() {
		return aventura;
	}

	public void setAventura(AventuraZork aventura) {
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

		boolean encontrado = false;
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
				if (encontrado = comando.contains(currConnections.get(i).getDirection())) {
					obj.setCondition("direction");
					obj.setThing(currConnections.get(i).getDirection());
				} else {
					if (encontrado = comando.contains(currConnections.get(i).getLocation())) {
						obj.setCondition("location");
						obj.setThing(currConnections.get(i).getLocation());
					} else {
						obj.setMessage("No entiendo donde quieres ir.");
					}
				}
				i++;
			}
		}else {
			obj.setMessage("No entiendo que quieres decir.");
		}

		return encontrado;
	}

}
