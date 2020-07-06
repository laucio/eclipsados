package juego_de_aventura;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import game_frontend.GameWindow;
import game_frontend.PlayerWindow;

public class GraphicalUserInterface {

	private Game game;
	private GameWindow gameWindow;
	private PlayerWindow playerWindow;
	
	private boolean continuarPartida = true;
	private Map<Integer, String> adventuresPath;

	public GraphicalUserInterface() {
		gameWindow = new GameWindow(this);
	}
	

	public void run() {
		
		loadAdventuresPaths("Adventures");
		loadAdventuresInComboBox("Adventures");
		
		gameWindow.run();	
		
		
	/*	while (!game.isEndgame() && continuarPartida) {
			command = in.nextLine();
			output = handleCommand(command);
			output = output == null ? game.processCommand(command) : output;
			System.out.println(output);
		} */

		//this.game.saveProgress();
	}

	public String handleCommand(String command) {
		String output = null;
		Scanner in = new Scanner(System.in);
		switch (command) {
		case "SALIR":
			System.out.println(game.getUserName() + ",estas seguro de que deseas salir? SI/NO");
			String confirmado = in.nextLine();
			if (confirmado.equals("SI")) {
				output = "Hasta luego " + game.getUserName();
				continuarPartida = false;
			}
			break;
		case "GUARDAR":
			this.game.saveProgress();
			output = "OK, guardado";
			break;
		case "AYUDA":
			output = "Para salir escriba: SALIR\nPara guardar el juego escriba: GUARDAR\nPara ver su inventario escriba 'Ver Inventario'";
			break;
		case "RECUPERAR":
			this.game.getSavedProgress();
			output = "Partida recuperada";
			break;
		default:
			output = null;
		}
		return output;

	}

	public void loadAdventuresPaths(String folderPath) {
		File folder = new File(folderPath);
		Integer index = 1;
		adventuresPath = new HashMap<Integer, String>();

		for (final File adventure : folder.listFiles()) {
			adventuresPath.put(index, adventure.getPath());
			index++;
		}
		
		/*
		if (hasSavedGame()) {
			System.out.println(index.toString() + " - Recuperar Partida");
			adventuresPath.put(index, FileManager.StatusGameFilePath);
		}

		//System.out.println("Por favor ingrese el numero de aventura que desea jugar: ");

		int selectedAdventure = in.nextInt();
		
		while (selectedAdventure!=0 && !adventuresPath.containsKey(selectedAdventure)) {
			System.out.println("Numero invalido Ingrese otro: ");
			selectedAdventure = in.nextInt();
		}
		
		if(selectedAdventure==0) {
			System.exit(0);
		}

		return adventuresPath.get(selectedAdventure);
		*/
	}

	public boolean hasSavedGame() {
		try {
			FileReader file = new FileReader(FileManager.StatusGameFilePath);
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			return false;
		}

		return true;

	}
	
	public String getUserName() {
		return this.game.getUserName();
	}
	
	public String getCurrentLocation() {
		return game.getPlayer().getCurrentLocation().getName();
	}
	
	public String getHitPoints() {
		return Integer.toString(game.getPlayer().getHitPoints());
	}
	
	public String getCommandCounter() {
		return Integer.toString(game.getPlayer().getCommandCounter());
	}
	
	public void setPlayerWindow(int adventureIndex,String userName) {
		String adventurePath = adventuresPath.get(adventureIndex);
		
		if (adventurePath == FileManager.StatusGameFilePath) {
			game = new Game();
			game.getSavedProgress();
		} else {

			try {
				game = new Game(adventurePath);
				game.setUserName(userName);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("No se pudo cargar la aventura seleccionada");
			}
		}
		
		this.playerWindow = new PlayerWindow(this, adventurePath);
		this.playerWindow.addTextToTextArea(game.getWelcome()+"\n");
		this.gameWindow.setVisible(false);
		this.playerWindow.run();
	}
	
	public void loadAdventuresInComboBox(String folderPath) {
		File folder = new File(folderPath);
		Integer index = 1;
		
		for (final File adventure : folder.listFiles()) {
			String adventureName = index.toString() + " - "
					+ adventure.getName().substring(0, adventure.getName().lastIndexOf('.'));
			this.gameWindow.agregarAventurasComboBox(adventureName);
			index++;
		}
		
	}


	public void openLobby() {
		this.gameWindow.setVisible(true);
	}
	
	public String processCommand(String command) {
		return game.processCommand(command);
	}


	public boolean isEndgame() {
		return game.isEndgame();
	}
	
	public void saveProgress() {
		game.saveProgress();
	}
	
	public String getImageName() {
		return game.getImageName();
	}
	
}
