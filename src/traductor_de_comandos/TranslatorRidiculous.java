package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorRidiculous implements CommandTranslator {

	private final static String[] VERBOS_RIDICULOS = { " suicidar", " matarse ",
							" matarme ", " envenenar", " bailar ", " bailarse ",
							" bailarme ", " cantar", " besar", 
							" acariciar", " abrazar", " gritar", " lloriquear ",
							" llorar", " danzar ", " escupir", " silbar ", " sonar ",
							" pedir u"};
	
	private CommandTranslator next = null;

	public TranslatorRidiculous(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		boolean translated = false;
		
		int i = 0;
		while(!translated && i<VERBOS_RIDICULOS.length) {
			
			if(command.contains(VERBOS_RIDICULOS[i])) {
				translated = true;
				action.setAction("ridiculous");
			}
			
		i++;
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
		
	}

}
