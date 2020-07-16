package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorSenses implements CommandTranslator {

	private final static String[] VERBOS_SENTIDOS = { " oir ", " oye ", " huele ", " olfatear ",
			" olfatea ", " oler ", " ole ",
			" escuchar ", " escucha "};
	
	private CommandTranslator next = null;

	public TranslatorSenses(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		boolean translated = false;
		
		int i = 0;
		while(!translated && i<VERBOS_SENTIDOS.length) {
			
			if(command.contains(VERBOS_SENTIDOS[i])) {
				translated = true;
				action.setAction("senses");
			}
			
		i++;
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
		
	}

}

