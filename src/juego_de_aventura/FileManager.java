package juego_de_aventura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class FileManager {
	final static String StatusGameFilePath = "./StatusGame.txt";
	final static String AdventureFilePath = "./Adventure.txt";
	
	public static Adventure cargarArchivo(String pathAventura) throws IOException {
		BufferedReader br = null;
		Adventure adventure = null;
		final Gson gson = new Gson();
		try {

			br = new BufferedReader(new FileReader(pathAventura));
			adventure = gson.fromJson(br, Adventure.class);
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br.close();
		return adventure;
	}
	
	public static void saveGameProgress(Game game) throws IOException {
		final Gson gson = new Gson();
		String jsonGame = gson.toJson(game);
		BufferedWriter bw = new BufferedWriter(new FileWriter(StatusGameFilePath));
		bw.write(jsonGame);
		bw.close();
	}
	
	public static Game getGameProgress() throws IOException {
		Game game = new Game();
		final Gson gson = new Gson();
		BufferedReader br = null;
		
		br = new BufferedReader(new FileReader(StatusGameFilePath));
		game = gson.fromJson(br, Game.class);
		br.close();
		
		return game;
	}
	
	public static void saveAdventureProgress(Adventure adventure) throws IOException {
		final Gson gson = new Gson();
		String jsonAdventure = gson.toJson(adventure);
		BufferedWriter bw = new BufferedWriter(new FileWriter(AdventureFilePath));
		bw.write(jsonAdventure);
		bw.close();
	}
}
