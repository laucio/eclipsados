package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

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

		if (!translated) {
			next.translateCommand(command, action, game);
		}

	}

}
