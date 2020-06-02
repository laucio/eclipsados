package traductor_de_comandos;

import juego_de_aventura.Action;
import juego_de_aventura.Game;

public class TranslatorVerbWatchInventory implements CommandTranslator {

	private final static String[] VERBO_VER_INVENTARIO = { " ver inventario ",
			" mirar inventario ", " checkear inventario ", " inspeccionar inventario " };
	
	private CommandTranslator next = null;

	public TranslatorVerbWatchInventory(CommandTranslator next){
		this.next = next;
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		
	}

}
