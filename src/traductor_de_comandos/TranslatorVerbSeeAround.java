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
		boolean translated = false;
		int i=0;
		
		while(!translated && i < VERBO_VER_ALREDEDOR.length) {
			if(command.contains(VERBO_VER_ALREDEDOR[i])) {
				translated = true;
				action.setAction("ver alrededor");
			}
		i++;
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
	}

}
