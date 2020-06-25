package juego_de_aventura;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

	private Game game;
	private boolean continuarPartida = true;

	public void UserInterface() {
	}

	public void run() {
		// to do: definir una carpeta donde se van a poner las aventuras
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese su nombre: \n");
		String userName = in.nextLine();

		String adventurePath = selectAdventure("Adventures");
		if (adventurePath == FileManager.StatusGameFilePath) {
			game = new Game();
			game.getSavedProgress();
		} else {

			try {
				game = new Game(adventurePath);
				game.setUserName(userName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("No se pudo cargar la aventura seleccionada");
			}
		}

		String output = null;
		System.out.println(game.getWelcome());

		String command = "";
		while (!game.isEndgame() && continuarPartida) {
			command = in.nextLine();
			output = handleCommand(command);
			output = output == null ? game.processCommand(command) : output;
			System.out.println(output);
		}

	
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
			output = "Para salir escriba: SALIR\nPara guardar el juego escriba: GUARDAR\nPara ver su inventario escriba 'Ver Inventario'\nPara recuperar un juego guardado escriba: RECUPERAR";																																											// ayuda
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
		Map<Integer, String> adventuresPath = new HashMap<Integer, String>();
		Scanner in = new Scanner(System.in);

		System.out.println("Aventuras Disponibles: ");
		System.out.println("0 - Salir");
		for (final File adventure : folder.listFiles()) {
			String adventureName = index.toString() + " - "
					+ adventure.getName().substring(0, adventure.getName().lastIndexOf('.'));
			adventuresPath.put(index, adventure.getPath());
			System.out.println(adventureName);
			index++;
		}
		
		
		if (hasSavedGame()) {
			System.out.println(index.toString() + " - Recuperar Partida");
			adventuresPath.put(index, FileManager.StatusGameFilePath);
		}

		System.out.println("Por favor ingrese el numero de aventura que desea jugar: ");

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
}
