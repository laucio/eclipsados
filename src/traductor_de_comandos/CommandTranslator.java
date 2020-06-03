package traductor_de_comandos;

import juego_de_aventura.*;

public interface CommandTranslator {
	//Patron
	public abstract void translateCommand(String command, Action action, Game game);
}
