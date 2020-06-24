package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorVerbLeave implements CommandTranslator {

	private final static String[] VERBO_DEJAR = { " dejar ", " abandonar ", " soltar "};

	private CommandTranslator next = null;

	public TranslatorVerbLeave(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {

		boolean translated = false;

		int i = 0;
		while (!translated && i < VERBO_DEJAR.length) {

			if (command.contains(VERBO_DEJAR[i])) {
				translated = true;
				action.setAction("dejar");
			}

			i++;
		}
		
		
		if(translated) {
				ArrayList<String> objs = new ArrayList<String>();
				Adventure adventure = game.getAdventure();
 				adventure.ItemsOcurrences(command, objs);
				int cant = objs.size();
				switch(cant) {
				case 0:
					Location location = adventure.getMentionedLocation(command);
					if(location!=null) {
						action.setCondition("location");
						action.setThing(location.getName());
					}else {
						action.setCondition("unknown");
					}
					break;
				case 1:
					action.setCondition("item");
					action.setThing(objs.get(0));
					action.setTarget("self");
					action.setEffect_over("self");
					break;
				case 2:
					Player player = game.getPlayer();
					if(player.hasItem(adventure.getItem(objs.get(0)))||player.hasItem(adventure.getItem(objs.get(1)))) {
						action.setCondition("tooManyThings");
					}else {
						action.setCondition("unknown");
					}
					
					break;
				}
			}
			

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}

}

