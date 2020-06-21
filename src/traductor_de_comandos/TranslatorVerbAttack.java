package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.Action;
import juego_de_aventura.Adventure;
import juego_de_aventura.Game;

public class TranslatorVerbAttack implements CommandTranslator {

	private final static String[] VERBO_ATACAR = { " atacar ", " golpear ", " cortar ", " herir ", " pelear ",
			" matar ", " destruir ", " abatir ", " derrotar " };

	private CommandTranslator next = null;

	public TranslatorVerbAttack(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {
		boolean translated = false;
		int i = 0;

		while (!translated && i < VERBO_ATACAR.length) {
			if (command.contains(VERBO_ATACAR[i])) {
				translated = true;
				action.setAction("atacar");
			}
			i++;
		}

		if (translated) {
			Adventure adventure = game.getAdventure();
			String npcName;
				
				ArrayList<String> objs = new ArrayList<String>();
				adventure.ItemsOcurrences(command, objs);
				int cant = objs.size();
				switch(cant) {
				case 0:
					//no sabe con que atacar, no hay item
					action.setCondition("unknown");
					break;
				case 1:
					//ataca con un item 
					//Action("atacar","espada", "item","dragon", "npcs");
					action.setThing(objs.get(0));
					action.setCondition("item");
					
					//verifica a quien quiere atacar
					if ((npcName = adventure.containsNPC(command)) != null) {
						//  ataca a npc
						action.setTarget(npcName);
						action.setEffect_over("npcs");
					}
					else {
						//sabe con que, pero no sabe a quien atacar
						action.setEffect_over("unknown");
					}
					
					break;
				case 2:
					//atacar un item sobre otro item
					//Action("usar","barreta","item","rociador con cerveza de raiz", "item");
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
				}
				
			}
		

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}
}
