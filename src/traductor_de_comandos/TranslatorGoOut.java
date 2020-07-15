package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorGoOut implements CommandTranslator {

	private final static String[] VERBO_SALIR = { " salir ", " salir de ", " salirse de ", " salirme de ", " sali de ", " sal de ",
			" ir afuera ", " irse afuera ", " rodear ", " atravesar ", " cruzar "," anda afuera "};
	
	private CommandTranslator next = null;

	public TranslatorGoOut(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		boolean translated = false;
		
		int i = 0;
		while(!translated && i<VERBO_SALIR.length) {
			
			if(command.contains(VERBO_SALIR[i])) {
				translated = true;
				action.setAction("salir");
			}
			
		i++;
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
		
	}

}
