package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorRidiculous implements CommandTranslator {

	private final static String[] VERBOS_RIDICULOS = { " suicidar", " matarse ",
							" matarme ", " envenena", " bailar ", " bailarse ", " baila ",
							" bailarme ", " cantar", " besar", " besa ", 
							" acariciar", " acaricia ", " abrazar", " abraza ", " gritar", " grita ", " lloriquear ", " lloriquea ",
							" llorar"," llora ", " danzar ", " escupir", " escupe ", " silbar "," silba ", " sonar ", " suena ", " sona ",
							" pedir u", " pide u"};
	
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
