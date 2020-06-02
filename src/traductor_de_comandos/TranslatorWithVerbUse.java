package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorWithVerbUse implements CommandTranslator {
	
	private final static String[] VERBO_USAR = { " usar ", " usa ", " utilizar ", " utiliza " };
	
	private CommandTranslator next = null;

	public TranslatorWithVerbUse(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		
	}

}
