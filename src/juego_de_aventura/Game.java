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
	
	private Adventure adventure;
	private Player player;
	private Translator translator;
	private ArrayList<String> log;
	private boolean isEndgame;
	private String userName;
	
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
			FileManager.saveLogTxt(this);
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
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}
	public ArrayList<String> getLog() {
		return log;
	}
	public Translator getTranslator() {
		return translator;
	}
	public void translateCommand(String command, Action action) {
		this.translator.translateCommand(command, action, this);
	}

	public String movePlayer(Action action) {
		String retorno;

		if (player.getCurrentLocation().isNamed(action.getThing())) {
			player.setImageName("Fails/laugh");
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
		player.increaseCommandCounter();
		String respuesta = "No entiendo lo que me dices";
		player.setImageName("Fails/translator");
		this.log.add(userName + " > " + command);
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
            	int index = i-1;
            	player.setImageName("Endgames/"+index);
                retorno = endgames.get(index).getDescription() + "\nFIN.";                                                   
            }
        }
       
    return retorno;
    }
	
	private String makePlayerAttack(Action action) {
		String retorno;
		
		player.setImageName("Fails/confused");
		
		if(action.isUnknownThing() && action.isUnknownTarget()) {
			retorno = "A quien y con que? Debes ser mas claro";
		}else {
			if(action.isUnknownThing()) {
				retorno = "No entiendo con que cosa quieres atacar";
			}else {
				if(action.isUnknownTarget()) {
					retorno = "No entiendo a quien quieres atacar";
				}else {
					retorno = player.attack(action, adventure);
				}
			}
		}
		
		return retorno;
	}
	
	private String makePlayerObserveAnItem(Action action) {
		return player.observeItem(action, adventure); 
	}
	
	private String makePlayerEat(Action action) {
		return player.eat(action, adventure);
	}
	
	private String makePlayerDrink(Action action) {
		return player.drink(action, adventure);
	}
	
	private String makePlayerOpenSomething(Action action) {
		return player.openSomething(action, adventure);
	}
	
	private String makePlayerGoOut() {
		return "Debes decirme hacia donde quieres ir";
	}
	
	private String rejectAction() {
		player.setImageName("Fails/no");
		return "No puedes hacer eso";
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
        case "observar":
            cadena = this.makePlayerObserveAnItem(action);
            break;
        case "ir":
            cadena = this.movePlayer(action);
            break;
        case "salir":
            cadena = this.makePlayerGoOut();
            break;
        case "comer":
            cadena = this.makePlayerEat(action);
            break;
        case "beber":
            cadena = this.makePlayerDrink(action);
            break;
        case "abrir":
            cadena = this.makePlayerOpenSomething(action);
            break;
        case "tomar":
            cadena = this.makePlayerTakeItem(action);
            break;
        case "dejar":
            
        	if(action.isConditionALocation()) {
        		cadena = this.makePlayerLeaveLocation(action);
        	}else {
        		cadena = this.makePlayerLeaveItem(action);
        	}
        	
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
        case "otros":
            cadena = this.rejectAction();
            break;    
        default:
            break;

        }

        return cadena;
    }
	
	private String makePlayerLeaveItem(Action action) {
		return player.leaveItem(action, adventure);
	}
	
	private String makePlayerLeaveLocation(Action action) {
		return player.leavelocation(action);
	}
	
	public String getImageName() {
		return player.getImageName();
	}

	public void setImageName(String imageName) {
		player.setImageName(imageName);
	}
}
