package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorVerbObserve implements CommandTranslator {

	private final static String[] VERBO_OBSERVAR = { " observar ", " leer ", " inspeccionar ", " ver ",
			" mirar ", " chusmear ", " mirarse ", " reflejarse "," analizar "," analiza "," verse ",
			" observarme ", " leerme ", " inspeccionarme ", " verme ", " mirarme ", " chusmearme ", " reflejarme "};

	private CommandTranslator next = null;

	public TranslatorVerbObserve(CommandTranslator next) {
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
				
				Player player = game.getPlayer();
				Location location = player.getCurrentLocation();
				Place place;
				if((place = location.getMentionedPlace(command))!= null) {
					action.setThing(place.getName());
					action.setCondition("place");
				}else {
					String npcName;
					if( (npcName = adventure.containsNPC(command) )!= null ) {
						action.setThing(npcName);
						action.setCondition("npcs");
					}else {
						action.setThing("unknown");
					}
				}
				
				break;
			case 1:
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

