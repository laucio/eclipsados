package juego_de_aventura;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

	Game game;
	boolean continuarPartida=true;
	String userName;

	public void UserInterface() {
	}

	public void run() {
		// to do: definir una carpeta donde se van a poner las aventuras
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese su nombre: \n");
		userName=in.nextLine();
		
		String adventurePath = selectAdventure("Adventures");
		try {
			game = new Game(adventurePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No se pudo cargar la aventura seleccionada");
		}

		String output=null;
		System.out.println(game.getWelcome());
		
		String command="";
		while (!game.isEndgame() && continuarPartida){
			command=in.nextLine();
			output=handleCommand(command);
			output = output == null ? game.processCommand(command) : output;
			System.out.println(output);
		}
		
		
		//imprimir gano el juego
		//jugar de nuevo
		//no poder seguir poniendo comandos
	}
	
	public String handleCommand(String command) {
		String output=null;
		Scanner in = new Scanner(System.in);
		switch (command) {
		case "SALIR":
			System.out.println("Estas seguro de que deseas salir? SI/NO");
			String confirmado=in.nextLine();
			if(confirmado.equals("SI")) {
				output="Hasta luego";				
				continuarPartida=false;
			}
			break;
		case "GUARDAR":
			this.game.saveProgress();
			output="OK, guardado";
			break;
		case "AYUDA":
			output="Para salir escriba: SALIR\nPara guardar el juego escriba: GUARDAR\nPara ver su inventario escriba 'Ver Inventario'\n";//imprimir la ayuda
			break;
		default:
			output=null;
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
