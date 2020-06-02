package traductor_de_comandos;

import juego_de_aventura.*;

public class TranslatorWithVerbTake implements CommandTranslator{
		
	private final static String[] VERBO_TOMAR = { " tomar ", " agarrar ", " levantar ", " guardar ", " toma ", " agarra ",
			" levanta ", " guarda ", " recoger ", " recoge " };
	
	private CommandTranslator next = null;

	public TranslatorWithVerbTake(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		
	}

}
