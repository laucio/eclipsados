package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorGive implements CommandTranslator {

	private final static String[] VERBO_DAR = { " dar ", " darle ", " dale ", " regalar ", " regalarle ", " regalale ",
			" entregar ", " entregarle ", " entregale ", " obsequiar ", " obsequiarle ", " obsequiale " };

	private CommandTranslator next = null;

	public TranslatorGive(CommandTranslator next) {
		this.next = next;
	}

	@Override
	public void translateCommand(String command, Action action, Game game) {

		boolean translated = false;

		int i = 0;
		while (!translated && i < VERBO_DAR.length) {

			if (command.contains(VERBO_DAR[i])) {
				translated = true;
				action.setAction("dar");
			}

			i++;
		}

		if (translated) {
			Adventure adventure = game.getAdventure();
			String npcName = adventure.containsNPC(command);
			String item = adventure.containsItem(command);

			if (npcName != null && item != null) {

				action.setTarget(npcName);
				action.setEffect_over("npcs");
				action.setCondition("item");
				action.setThing(item);

			} else {
				if (npcName != null) {
					action.setCondition("unknown");
				} else {
					if (item != null) {
						action.setEffect_over("unknown");
					} else {
						action.setEffect_over("unknown");
						action.setCondition("unknown");
					}
				}
			}
		}
		//////////////////////////////////////

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}

}
