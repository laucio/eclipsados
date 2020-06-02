package traductor_de_comandos;

import juego_de_aventura.*;

public interface CommandTranslator {
	
	public abstract void translateCommand(String command, Action action, Game game);
}
