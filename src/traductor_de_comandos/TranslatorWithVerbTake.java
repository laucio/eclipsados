package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorWithVerbTake implements CommandTranslator {

	private final static String[] VERBO_TOMAR = { " tomar ", " agarrar ", " levantar ", " guardar ", " toma ",
			" agarra ", " levanta ", " guarda ", " recoger ", " recoge ", " levanta ", " colecta ", " colectar " };

	private CommandTranslator next = null;

	public TranslatorWithVerbTake(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {
		boolean translated = false;

		int i = 0;
		while (!translated && i < VERBO_TOMAR.length) {
			if (command.contains(VERBO_TOMAR[i])) {
				translated = true;
				action.setAction("tomar");
			}
			i++;
		}

		if (translated) {
			boolean encontrado = false;
			Player player = game.getPlayer();
			ArrayList<Place> currPlaces = player.getCurrentLocation().getPlaces();

			if (currPlaces != null) {
				i = 0;
				while (!encontrado && i < currPlaces.size()) {
					int j = 0;
					ArrayList<String> currItems = currPlaces.get(i).getItems();
					while (!encontrado && j < currItems.size()) {
						encontrado = command.contains(" " + currItems.get(j) + " ");
																						
						if (encontrado) {
							action.setCondition("item");
							action.setThing(currItems.get(j));
						}
						j++;
					} 
					i++;
				} 
			}
			
			if(!encontrado) {
				Adventure adventure = game.getAdventure();
				if(adventure.containsNPC(command)!= null) {
					action.setCondition("npcs");
					encontrado = true;
				}
			}
			
			if(!encontrado) {
				Location currentLocation = player.getCurrentLocation();
				if(currentLocation.getMentionedPlace(command) != null ) {
					action.setCondition("place");
					encontrado = true;
				}
			}
			
			if(!encontrado) {
				action.setCondition("unknown");
			}

		} else {
			next.translateCommand(command, action, game);
		}

	}

}
