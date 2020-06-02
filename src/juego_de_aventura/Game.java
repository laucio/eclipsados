package juego_de_aventura;

import java.io.IOException;
import java.util.ArrayList;
import juego_de_aventura.*;
import traductor_de_comandos.*;

public class Game {
	
	Adventure adventure;
	Player player;
	CommandTranslator translator;

	public Game(String aventuraPath) throws IOException {
		this.adventure = LoadAdventure.cargarArchivo(aventuraPath);

		Location currentLocation = adventure.getInitialLocation();

		ArrayList<Item> initialInventory = adventure.getInitialInventory();

		this.player = new Player(initialInventory, currentLocation);
		
		this.translator = new Translator();
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
	
	public boolean translateCommand(String command, Action action) {
		this.translator.translateCommand(command, action, this);
		return !action.isUnknown();
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
	
	public String processCommand(String command) {
		String respuesta = "No entiendo lo que me dices";
		
		Action action = new Action();
		translateCommand(command, action);
		
		if(!action.isUnknown()) {
			respuesta = processAction(action);
		}
	
	return respuesta;
	}
	
	
	public String processAction(Action action) {
        String retorno = this.chooseAction(action);
       
        if(action.isAchieved()) {
            ArrayList<Endgame> endgames = this.adventure.getEndgames();
            int i=0;
            boolean esEndgame = false;
            while (i < endgames.size() && !esEndgame) {
                esEndgame = endgames.get(i).esEndgame(action);
                i++;
            }
            if (esEndgame) {
                retorno = endgames.get(i-1).getDescription() + "\nFIN.";                                                   
            }
        }
       
    return retorno;
    }
	
	public String chooseAction(Action action) {
        //
        String cadena = "No entiendo que quieres hacer";
        switch (action.getAction()) {
        case "ir":
            cadena = this.movePlayer(action);
            break;
        case "tomar":
            cadena = this.makePlayerTakeItem(action);
            break;
        case "usar":
            cadena = this.makePlayerUseItem(action);
            break;
        case "ver alrededor":
            cadena = this.makePlayerLookAround();
            break;
        case "ver inventario":
            cadena = this.makePlayerWatchInventory();
            break;
        case "hablar":
            cadena = this.makePlayerTalkToClosestNPC(action);
            break;
        default:
            break;

        }

        return cadena;
    }
	
}
