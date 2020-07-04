package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorWithVerbTake implements CommandTranslator {

	private final static String[] VERBO_TOMAR = { " tomar ", " agarrar ", " levantar ", " guardar ", " toma ",
			" agarra ", " levanta ", " guarda ", " recoger ", " recoge " };

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
						encontrado = command.contains(" " + currItems.get(j) + " "); // agrego espacios por si busca
																						// pala y escribio paladar
						if (encontrado) {
							action.setCondition("item");
							action.setThing(currItems.get(j));
						}
						j++;
					} // while(!encontrado && j<currPlaces.get(i).getItems())
					i++;
				} // end of while
			}
			
			if(!encontrado) {
				action.setCondition("unknown");
			}

		} else {
			next.translateCommand(command, action, game);
		}

	}

}
