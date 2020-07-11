package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.Action;
import juego_de_aventura.Adventure;
import juego_de_aventura.Game;

public class TranslatorVerbBreak implements CommandTranslator {

	private final static String[] VERBO_ROMPER = { " romper ", " rompe ", " destruir ",
			" destruye ", " puveriza ", " pulverizar " };

	private CommandTranslator next = null;

	public TranslatorVerbBreak(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {
		boolean translated = false;
		int i = 0;

		while (!translated && i < VERBO_ROMPER.length) {
			if (command.contains(VERBO_ROMPER[i])) {
				translated = true;
				action.setAction("romper");
			}
			i++;
		}

		if (translated) {
			Adventure adventure = game.getAdventure();
			String npc = adventure.containsNPC(command);
			ArrayList<String> objs = new ArrayList<String>();
			adventure.ItemsOcurrences(command, objs);
			int cantItems = objs.size();
			
			if(npc != null) {
				action.setAction("atacar");
				action.setTarget(npc);
				action.setEffect_over("npcs");
				
				switch(cantItems) {
				case 0:
					action.setCondition("unknown");
					break;
				case 1:
					action.setThing(objs.get(0));
					action.setCondition("item");
					break;
				case 2:
					action.setCondition("item");
					
					if(command.indexOf(objs.get(0)) < command.indexOf(objs.get(1))) {
						action.setThing(objs.get(0));
					}else {
						action.setThing(objs.get(1));
					}
					
					break;
				default:
					break;
				}
			}else {
				switch(cantItems) {
				case 0:
					action.setCondition("unknown");
					action.setEffect_over("unknown");
					break;
				case 1:
					action.setThing(objs.get(0));
					action.setCondition("item");
					action.setTarget("self");
					action.setEffect_over("self");
					break;
				case 2:
					if(command.indexOf(objs.get(0)) > command.indexOf(objs.get(1))) {
						action.setThing(objs.get(0));
						action.setTarget(objs.get(1));
					}else {
						action.setThing(objs.get(1));
						action.setTarget(objs.get(0));
					}
					
					action.setCondition("item");
					action.setEffect_over("item");
					break;
			
				default:
					break;
				}
			}
			
		}
		

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}
}
