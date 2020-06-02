package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorVerbSeeAround implements CommandTranslator {
	
	private final static String[] VERBO_VER_ALREDEDOR = { " ver alrededor ", " observar alrededor ",
			" mirar alrededor ", " inspeccionar alrededor ", " ver alrededores ",
			" observar alrededores ", " mirar alrededores ", " inspeccionar alrededores " };
	
	private CommandTranslator next = null;

	public TranslatorVerbSeeAround(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		
	}

}
