package source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	private AventuraZork aventura = null;
	private String currentLocation;

	private final String[] VERBO_IR = { " ir ", " ve ", " camina ", " caminar ", " movete ", " moverse " }; // moverse
																											// de
																											// location
	private final String[] VERBO_TOMAR = { " tomar ", " agarrar ", " levantar ", " guardar ", " toma ", " garra ",
			" levanta ", " guarda ", " recoger ", " recoge " }; // agarrar items
	private final String[] VERBO_USAR = { " usar ", " usa ", " utilizar ", " utiliza " }; // usar barreta por ejemplo

	public Player(String aventuraPath) throws IOException {
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
	
	
	public boolean goTo(Action action) {
		boolean exitoso = false;
		int indexCurrentLocation = aventura.getLocationIndex(this.currentLocation);
		Location currentLocation = aventura.getLocations().get(indexCurrentLocation);
		//
		ArrayList<Connection> con = currentLocation.getConnections();
		int i=0;
		while(i<con.size() && !exitoso) {
			if (action.getCondition().equals("direction") && action.getThing().equals(con.get(i).getDirection())) {
				exitoso = true;
			}else if (action.getCondition().equals("location") && action.getThing().equals(con.get(i).getLocation())) {				
				exitoso = true;
			}
			
			i++;
		}
		
		if( exitoso==true) {
	
		if(con.get(i-1).getObstacles()==null) {
			this.setCurrentLocation(con.get(i-1).getLocation());
            System.out.println(this.getAventura().verAlrededor(this.getCurrentLocation())); 			
		}else {
			int j = this.getAventura().getNPCSIndex((con.get(i-1).getObstacles()));///Buscamos el indice del obsctaculo sea un Npcs
			if(j != -1){//1
				
				action.setMessage("'¡No puedes pasar!' " + this.getAventura().getNpcs().get(j).toString() + " no te dejará pasar" );
			    exitoso=false;
			}//1
			
			if( j == -1 ) {//2
			  j = this.getAventura().getItemIndex((con.get(i-1).getObstacles()));///Buscamos el indice del obsctaculo sea un item
			   if ( j != -1) {//3
					action.setMessage("'¡No puedes pasar!' Hay " + this.getAventura().getItems().get(j).toString());				   
			        exitoso=false;
			   }//3	
			}//2
		}
   }else {
	   action.setMessage("No se a donde quieres ir ");
   }
	   
		return exitoso;
	}

}

/*
 * if(encontrado=esMovimiento?(param))traducido=true if(traducido=false)
 * 
 * boolean traductor(String comando) { boolean traducido ; traducido =
 * esMovimient(param) if(!traducido) traducido = esTomarObjeto; if(!traducido)
 * traducido = esLanzarTrigger retrun traducido } }
 */