package traductor_de_comandos;

import juego_de_aventura.*;

public class TranslatorDefault implements CommandTranslator{

	@Override
	public void translateCommand(String command, Action action, Game game) {
		action.setAchieved(false);
		action.setAction(null);
	}

	
}
