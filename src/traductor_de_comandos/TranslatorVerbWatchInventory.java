package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorVerbWatchInventory implements CommandTranslator {

	private final static String[] VERBO_VER_INVENTARIO = { " ver inventario ", " ver el inventario ",
			" mirar inventario "," mirar el inventario", " mira el inventario",
			" checkear inventario ", " checkear el inventario ",
			" inspeccionar inventario ", " inspeccionar el inventario ",
			" revisar inventario ", " revisar el inventario ",
			" examinar inventario ", " examinar el inventario "};
	
	private CommandTranslator next = null;

	public TranslatorVerbWatchInventory(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		boolean translated = false;
		
		int i = 0;
		while(!translated && i<VERBO_VER_INVENTARIO.length) {
			
			if(command.contains(VERBO_VER_INVENTARIO[i])) {
				translated = true;
				action.setAction("ver inventario");
			}
			
		i++;
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
		
	}

}
