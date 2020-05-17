package juegotest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;


import source.*;

public class SavedGameTest {


	String path ;
	Adventure juego;
	Player jugador; 
	Action action;
	String expected;
	String actuals;
	
	@Before
	public void setup() throws IOException {
		path = "Juego.json";
		juego = LoadAdventure.cargarArchivo(path);
		jugador = new Player(juego);
	}
	@Test
    public void guardarPartida() throws JsonIOException, IOException {
		 ArrayList<String> targets = new ArrayList<String>();
		 ArrayList<Trigger> triggers = new ArrayList<Trigger>();
		targets.add("espejo");
		triggers.add(new Trigger("item","barreta","El espejo se ha rompido","remove"));
		jugador.getAventura().getItems().get(0).setTargets(targets);
		jugador.getAventura().getItems().get(2).setTriggers(triggers);
		
		
        String filePath = "partidaGuardada.json";
        expected = "No encuentro ese objeto."; 
       
        SavedGame.saveStatus(jugador.getAventura(), filePath);
        
       
    }
}
