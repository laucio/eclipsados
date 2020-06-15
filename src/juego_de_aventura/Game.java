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
	
	public Game() {}
	public Game(String aventuraPath) throws IOException {
		this.adventure = FileManager.cargarArchivo(aventuraPath);

		Location currentLocation = adventure.getInitialLocation();

		ArrayList<Item> initialInventory = adventure.getInitialInventory();

		this.player = new Player(initialInventory, currentLocation);
		
		this.translator = new Translator();
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
	
	// return the path of the selected adventure
	public String selectAdventure(String folderPath) {
		File folder = new File(folderPath);
		Integer index = 1;
		Map<Integer, String> adventuresPath = new HashMap<Integer, String>();
		Scanner in = new Scanner(System.in);
		
		System.out.println("Aventuras Disponibles: ");
		for (final File adventure : folder.listFiles()) {
			String adventureName = index.toString() + " - " + adventure.getName().substring(0, adventure.getName().lastIndexOf('.'));
			adventuresPath.put(index, adventure.getPath());
			System.out.println(adventureName);
			index ++;
		}
		
		System.out.println("Por favor ingrese el numero de aventura que desea jugar: ");
		
        int selectedAdventure = in.nextInt();
        
        while (!adventuresPath.containsKey(selectedAdventure)) {
        	System.out.println("Numero invalido Ingrese otro: ");
        	selectedAdventure = in.nextInt();
        }
        
        return adventuresPath.get(selectedAdventure);
	}
}
