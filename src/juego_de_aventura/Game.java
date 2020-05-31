package juego_de_aventura;

import java.io.IOException;
import java.util.ArrayList;
import juego_de_aventura.*;

public class Game {
	
	Adventure adventure;
	Player player;
	Translator translator;

	public Game(String aventuraPath) throws IOException {
		this.adventure = LoadAdventure.cargarArchivo(aventuraPath);

		Location currentLocation = adventure.getInitialLocation();

		ArrayList<Item> initialInventory = adventure.getInitialInventory();

		this.player = new Player(initialInventory, currentLocation);
	}
	
	public Adventure getAdventure() {
		return adventure;
	}

	public void setAdventure(Adventure adventure) {
		this.adventure = adventure;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public String movePlayer(Action action) {
		String retorno ="No se a donde quieres ir."; //tarea del traductor

		if (player.getCurrentLocation().isNamed(action.getThing())) {
			retorno = "Ya te encuentras aqui.";
			action.setAchieved(false);
		}
		else {
			retorno = player.goTo(action, adventure);
		}
		
	return retorno;
	}

	public String getWelcome(){
		return adventure.darBienvenida();
	}
	
	public String makePlayerLookAround() {
		return player.lookAround(adventure);
	}
	
	public String makePlayerTakeItem(Action action) {
		return player.takeItem(action, adventure);
	}

	public String makePlayerWatchInventory() {
		return player.lookInventory();
	}
	
	public String makePlayerTalkToClosestNPC(Action action) {
		return player.talkToNPC(action, adventure);
	}
	
	public String makePlayerUseItem(Action action) {
		return player.useItem(action, adventure);
	}
	
}
