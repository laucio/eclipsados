package juego_de_aventura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.google.gson.Gson;

import traductor_de_comandos.Translator;

public class FileManager {
	final static String StatusGameFilePath = "./StatusGame.txt";
	final static String LogFile = "./Log.txt";
	
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
		game.setTranslator(new Translator());
		return game;
	}
	
	public static void saveLogTxt (Game game) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(LogFile));
		PrintWriter out = new PrintWriter(bw);
		for(String linea : game.getLog()) {
			out.println(linea);
		}
		out.close();
		bw.close();
		
	}
	
	public static void saveLogTxtGraphical (Game game,String info,String user) throws IOException {
		String log = "";
		for(String linea : game.getLog()) {
			log += (linea + "\n");
		}
		log += "\n" + info;
		JFileChooser f = new JFileChooser();
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showSaveDialog(null);
		if(f.getSelectedFile() != null) {
			String path = f.getSelectedFile().toString();
			grabarHistorial(path,log,user);		
			game.setSuccessfullySaved(true);
		}else {
			game.setSuccessfullySaved(false);
		}
	}

	
	private static void grabarHistorial(String path, String historial,String user) {
		BufferedWriter bw = null;
		path += "\\"+ user + " - " + horaYFechaActual() + ".txt";
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(historial);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String horaYFechaActual() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
}
