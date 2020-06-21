package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorVerbRead implements CommandTranslator {

	private final static String[] VERBO_OBSERVAR = { " observar ", " leer ", " inspeccionar ", " ver ", " mirar ", " chusmear "};

	private CommandTranslator next = null;

	public TranslatorVerbRead(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {

		boolean translated = false;

		int i = 0;
		while (!translated && i < VERBO_OBSERVAR.length) {

			if (command.contains(VERBO_OBSERVAR[i])) {
				translated = true;
				action.setAction("observar");
			}

			i++;
		}
		

		if(translated) {
			Adventure adventure = game.getAdventure();
			ArrayList<String> objs = new ArrayList<String>();
			adventure.ItemsOcurrences(command, objs);
			int cant = objs.size();
			switch(cant) {
			case 0:
				//no sabe que observar
				action.setThing("unknown");
				break;
			case 1:
				//observar una cosa
				//Action("observar","espejo", "item","self", "self");
				action.setThing(objs.get(0));
				action.setCondition("item");
				action.setTarget("self");
				action.setEffect_over("self");
				break;
			}
		}

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}

}

