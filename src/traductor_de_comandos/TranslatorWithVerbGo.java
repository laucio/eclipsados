package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorWithVerbGo implements CommandTranslator {

	private final static String[] VERBO_IR = { " ir ", " ve ", " camina ", " caminar ", " desplazarse ", " desplazarme ", " desplazate ",
			" movete ", " moverse ", " moverme ", " correr ", " corre ", " entrar ", " entra ", " ingresar ", " ingresa "};
	private final static String[] POSSIBLE_DIRECTIONS = { " noreste ", " noroeste ", " sureste ", " suroeste ",
			" sudoeste ", " sudeste ", " sur ", " este ", " norte ", " oeste " };

	private CommandTranslator next = null;

	public TranslatorWithVerbGo(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {
		Player player = game.getPlayer();
		
		boolean found = false;
		boolean translated = false;

		int i = 0;
		while (!translated && i < VERBO_IR.length) {
			if(command.contains(VERBO_IR[i])) {
				translated = true;
				action.setAction("ir");
			}
			
		i++;
		}

		if (translated) {
			found = false;
			i = 0;
			ArrayList<Connection> currConnections = player.getCurrentLocation().getConnections();
			while (!found && i < currConnections.size()) {
				if (found = command.contains(" " + currConnections.get(i).getDirection() + " ")) {
					action.setCondition("direction");
					action.setThing(currConnections.get(i).getDirection());

				} else {
					if (found = command.contains(" " + currConnections.get(i).getLocation() + " ")) {
						action.setCondition("location");
						action.setThing(currConnections.get(i).getLocation());
					}
				}
				i++;
			}
			
			
			if (!found) {
				Adventure adventure = game.getAdventure();
				ArrayList<Location> locations = adventure.getLocations();
				i = 0;
				while (!found && i < locations.size()) {
					found = command.contains(locations.get(i).getName().toLowerCase());
					i++;
				}

				if (found) {
					action.setCondition("location");
					action.setThing(locations.get(i-1).getName());
				}
			}
			
			
			if (!found) {
				i = 0;
				while (!found && i < POSSIBLE_DIRECTIONS.length) {
					found = command.contains(POSSIBLE_DIRECTIONS[i]);
					i++;
				}

				if (found) {
					action.setCondition("direction");
					action.setThing(POSSIBLE_DIRECTIONS[i - 1].trim());
				}
			}

		} else {
			next.translateCommand(command, action, game);
		}
	}

}
