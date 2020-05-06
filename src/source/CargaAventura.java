package source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class CargaAventura {

	public static AventuraZork cargarArchivo(String pathAventura) throws IOException {
		BufferedReader br = null;
		AventuraZork juego = null;
		final Gson gson = new Gson();
		try {

			br = new BufferedReader(new FileReader(pathAventura));
			juego = gson.fromJson(br, AventuraZork.class);
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.close();
		return juego;

	}
}
