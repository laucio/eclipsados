package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorWithVerbUse implements CommandTranslator {

	private final static String[] VERBO_USAR = { " usar ", " usa ", " utilizar ", " utiliza " };

	private CommandTranslator next = null;

	public TranslatorWithVerbUse(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {

		boolean translated = false;

		int i = 0;
		while (!translated && i < VERBO_USAR.length) {

			if (command.contains(VERBO_USAR[i])) {
				translated = true;
				action.setAction("usar");
			}

			i++;
		}
		
		//SI TRANSLATED = TRUE
		//FALTA CARGAR PARAMETROS DE action CON EL ITEM A USAR, EL TARGET O NPC, ETC...
		///////////////////////////////////
		if(translated) {
			Adventure adventure = game.getAdventure();
			String npcName;
			if((npcName = adventure.containsNPC(command))!= null) {
				//usa sobre npc
				action.setTarget(npcName);
				action.setEffect_over("npcs");
				
				String item = adventure.containsItem(command);
				if(item!=null) {
					//usa item sobre npc
					action.setCondition("item");
					action.setThing(item);
				}
				else {
					//no sabe que usar
					action.setAction(null);
				}
			}else {
				ArrayList<String> objs = new ArrayList<String>();
				adventure.ItemsOcurrences(command, objs);
				int cant = objs.size();
				switch(cant) {
				case 0:
					//no sabe que usar
					action.setAction(null);
					break;
				case 1:
					//usa algo sobre self
					//Action("usar","espejo", "item","self", "self");
					action.setThing(objs.get(0));
					action.setCondition("item");
					action.setTarget("self");
					action.setEffect_over("self");
					break;
				case 2:
					//usa un item sobre otro item
					//Action("usar","barreta","item","rociador con cerveza de raiz", "item");
					action.setThing(objs.get(0));
					action.setCondition("item");
					action.setTarget(objs.get(1));
					action.setEffect_over("item");
					break;
				}
			}
			
			
		}
		
		//////////////////////////////////////

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}

}
