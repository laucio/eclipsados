package juegotest;

import source.Partida;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import source.CargaAventura;
import source.Item;
import source.Location;
import source.AventuraZork;

public class ZorkTest {


	@Test
	public void getCharacterName() throws IOException {

		/*final Gson gson = new Gson();
		final BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\christian.d.riveros\\eclipse-workspace\\TP Progra\\eclipsados\\Juego.json"));
		final Zork juego = gson.fromJson(br, Zork.class); */
		
	/*	String path = "C:\\Users\\christian.d.riveros\\eclipse-workspace\\TP Progra\\eclipsados\\Juego.json";
		Zork juego = CargaAventura.cargarArchivo(path); */
		
		String path = "Juego.json";
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		 
		assertEquals("Guybrush Threepwood", juego.getSettings().getCharacter());


	}
	
	@Test
	public void getWelcome() throws IOException {
		
		String path = "Juego.json";
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		assertEquals("Te encuentras en un muelle. Es de noche pero la luna ilumina todo el lugar. En el suelo hay algunos objetos, y sientes muchas ganas de ir hacia una taberna.",juego.getSettings().getWelcome() );
	}
	
	@Test
	public void getLocationDescription() throws IOException {
		
		String path = "Juego.json";
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		assertEquals("Estás en un muelle",juego.getLocations().get(0).getDescription());
	}
	
	@Test
	public void getLocationDescriptionNext() throws IOException {
		
		String path = "Juego.json";
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		assertEquals("Estás en una sucia taberna",juego.getLocations().get(1).getDescription());
	}
	
	@Test
	public void getNPCSTalk() throws IOException {

		
		String path = "Juego.json";
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		Item item = juego.getItems().get(2);
		//System.out.println(item);
		//System.out.println(juego.getLocations().get(0));
		System.out.println(juego.verAlrededor("muelle"));
		//System.out.println(juego.getItems().get(0).getNumber());
		//assertEquals("¡No hay nada que me digas que me haga cambiar de opinión!",juego.getNpcs().get(0).getTalk());
	}
	
	@Test
	public void getNPCSTalk1() throws IOException {

		
		String path = "Juego.json";
		Partida partida = new Partida(path);
		//System.out.println(partida.getCurrentLocation());
		//System.out.println(partida.);
		
		//Assert.assertEquals("¡No hay nada que me digas que me haga cambiar de opinión!",juego.getNpcs().get(0).getTalk());
	}
}
