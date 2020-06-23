package traductor_de_comandos;

import juego_de_aventura.*;

public class Translator implements CommandTranslator{
	
	CommandTranslator translatorDefault;
	CommandTranslator verbToGo;
	CommandTranslator verbToTake;
	CommandTranslator verbToObserve;
	CommandTranslator verbToTalk;
	CommandTranslator verbToUse;
	CommandTranslator verbToOpen;
	CommandTranslator verbToDrink;
	CommandTranslator verbToEat;
	CommandTranslator verbToAttack;
	CommandTranslator verbToWatchInventory;
	CommandTranslator verbToSeeAround;
	
	public Translator() {
		translatorDefault = new TranslatorDefault();
		verbToGo = new TranslatorWithVerbGo(translatorDefault);
		verbToTake = new TranslatorWithVerbTake(verbToGo);
		verbToObserve = new TranslatorVerbObserve(verbToTake);
		verbToUse = new TranslatorWithVerbUse(verbToObserve);
		verbToOpen = new TranslatorVerbOpen(verbToUse);
		verbToDrink = new TranslatorVerbDrink(verbToOpen);
		verbToEat = new TranslatorVerbEat(verbToDrink);
		verbToTalk = new TranslatorVerbTalkToNPC(verbToEat);
		verbToAttack = new TranslatorVerbAttack(verbToTalk);
		verbToWatchInventory = new TranslatorVerbWatchInventory(verbToAttack);
		verbToSeeAround = new TranslatorVerbSeeAround(verbToWatchInventory);
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		command = " " + command.toLowerCase() + " ";
		
		//Poner el mayor en jerarquia: seria verbToSeeAround
		verbToSeeAround.translateCommand(command, action, game);
	}
	
		
	/*	public boolean traducir(String comando, Action obj) {
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
	 */
	
}
