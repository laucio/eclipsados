package juego_de_aventura;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class LoadAdventure {

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
}
