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
	private boolean continuarPartida = true;
	private GameWindow gameWindow;
	private PlayerWindow playerWindow;
	private Map<Integer, String> adventuresPath;
	private String userName;

	public GraphicalUserInterface() {
	}

	public void run() {

		gameWindow = new GameWindow(this);
		gameWindow.run();
		
		//Scanner in = new Scanner(System.in);
		//System.out.println("Ingrese su nombre: \n");
		//String userName = in.nextLine();
		//System.out.println();
		String adventurePath = selectAdventure("Adventures");
		System.out.println();
		if (adventurePath == FileManager.StatusGameFilePath) {
			game = new Game();
			game.getSavedProgress();
		} else {

			try {
				game = new Game(adventurePath);
				//game.setUserName(userName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("No se pudo cargar la aventura seleccionada");
			}
		}
		

		String output = null;
		System.out.println(game.getWelcome());
		
		
		String command = "";
	/*	while (!game.isEndgame() && continuarPartida) {
			command = in.nextLine();
			output = handleCommand(command);
			output = output == null ? game.processCommand(command) : output;
			System.out.println(output);
		} */

		this.game.saveProgress();
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

	// return the path of the selected adventure
	public String selectAdventure(String folderPath) {
		File folder = new File(folderPath);
		Integer index = 1;
		adventuresPath = new HashMap<Integer, String>();
		Scanner in = new Scanner(System.in);

		System.out.println("Aventuras Disponibles: ");
		System.out.println("0 - Salir");
		for (final File adventure : folder.listFiles()) {
			String adventureName = index.toString() + " - "
					+ adventure.getName().substring(0, adventure.getName().lastIndexOf('.'));
			adventuresPath.put(index, adventure.getPath());
			this.gameWindow.agregarAventurasComboBox(adventureName);
			//System.out.println(adventureName);
			index++;
		}
		
		
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
	}

	public boolean hasSavedGame() {
		try {
			FileReader file = new FileReader(FileManager.StatusGameFilePath);
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			return false;
		}

		return true;

	}
	
	
	
	public void setPlayerWindow(int adventureIndex,String userName) {
		String adventureName = adventuresPath.get(adventureIndex);
		this.playerWindow = new PlayerWindow(userName,adventureName);
		this.playerWindow.run();
	}
	
	/*
			System.out.println("Bienvenido a Eclipsados.\n");
		System.out.println("Para jugar tu personaje puede realizar acciones como:");
		System.out.println(" - ir a un lugar");
		System.out.println(" - tomar un objeto");
		System.out.println(" - abrir puertas");
		System.out.println(" - atacar con un objeto a otro personaje");
		System.out.println(" - dar un objeto a otro personaje");
		System.out.println(" - hablar con un personaje");
		System.out.println(" - mirar alrededor y mirar tu inventario");
		System.out.println("Y todas las que se te puedan ocurrir...");
		System.out.println();
		System.out.println("Para mas comandos escriba: AYUDA");
		System.out.println();
	*/
}
