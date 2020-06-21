package juego_de_aventura;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import juego_de_aventura.*;
import traductor_de_comandos.*;

public class Game {
	
	Adventure adventure;
	Player player;
	Translator translator;
	ArrayList<String> log;
	boolean isEndgame;
	
	// Constructor que se usa para  "Continuar" la aventura.
	public Game() {}
	public Game(String aventuraPath) throws IOException {
		this.adventure = FileManager.cargarArchivo(aventuraPath);

		Location currentLocation = adventure.getInitialLocation();

		ArrayList<Item> initialInventory = adventure.getInitialInventory();

		this.player = new Player(initialInventory, currentLocation);
		this.log = new ArrayList<String>();
		this.log.add(this.adventure.darBienvenida());
		this.translator = new Translator();
		this.isEndgame=false;
	}
	public void saveProgress() {
		try {
			FileManager.saveGameProgress(this);
		} catch (IOException e) {
			System.out.println("Error al guardar progreso del juego");
			e.printStackTrace();
		}
	}
	
	public void getSavedProgress() {
		try {
			Game savedGame = FileManager.getGameProgress();
			this.adventure = savedGame.adventure;
			this.player = savedGame.player;
			this.translator = savedGame.translator;
			this.log = savedGame.log;
		} catch (IOException e) {
			System.out.println("Error al guardar progreso");
			e.printStackTrace();
		}
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
	
	public void translateCommand(String command, Action action) {
		this.translator.translateCommand(command, action, this);
	}

	public String movePlayer(Action action) {
		String retorno;

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
		this.log.add("> " + command);
		Action action = new Action();
		translateCommand(command, action);
		
		if(!action.isUnknown()) {
			respuesta = processAction(action);
		}
	this.log.add(respuesta);
	return respuesta;
	}
	
	
	public String processAction(Action action) {
        String retorno = this.chooseAction(action);
       
        if(action.isAchieved()) {
            ArrayList<Endgame> endgames = this.adventure.getEndgames();
            int i=0;
            this.isEndgame = false;
            while (i < endgames.size() && !this.isEndgame) {
            	this.isEndgame = endgames.get(i).esEndgame(action);
                i++;
            }
            if (this.isEndgame) {
                retorno = endgames.get(i-1).getDescription() + "\nFIN.";                                                   
            }
        }
       
    return retorno;
    }
	
	private String makePlayerAttack(Action action) {
		String retorno = "";
		
		if(action.isUnknownTarget()) {
			retorno = "No entiendo a quien quieres atacar";
		}else {
			if(action.isUnknownThing()) {
				retorno = "No entiendo con que cosa quieres atacar";
			}else {
				retorno = player.attack(action, adventure);
			}
		}
		
		return retorno;
	}
	
	public boolean isEndgame() {
		return isEndgame;
	}
	
	public String chooseAction(Action action) {
        //
        String cadena = "No entiendo que quieres hacer";
        switch (action.getAction()) {
        case "atacar":
            cadena = this.makePlayerAttack(action);
            break;
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
