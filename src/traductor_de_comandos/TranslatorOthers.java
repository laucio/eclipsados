package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorOthers implements CommandTranslator {

	private final static String[] VERBO_OTROS = { " subir", " cerrar", " bajar", " volar",
							" enterrar", " suicidar", " matarse ",
							" matarme ", " envenenar", " trepar", " escalar", " cocinar", " quemar",
							" tirar", " rostizar ", };
	
	private CommandTranslator next = null;

	public TranslatorOthers(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		boolean translated = false;
		
		int i = 0;
		while(!translated && i<VERBO_OTROS.length) {
			
			if(command.contains(VERBO_OTROS[i])) {
				translated = true;
				action.setAction("otros");
			}
			
		i++;
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
		
	}

}
