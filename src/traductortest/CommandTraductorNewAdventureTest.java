package traductortest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import juego_de_aventura.*;
import traductor_de_comandos.*;

public class CommandTraductorNewAdventureTest {
	
	String path;
	String command;
	Game game;
	Adventure adventure;
	Player player;
	Action action;
	String expected;
	String actuals;

	@Before
	public void setup() throws IOException {
		path = "C:\\Users\\Noblex\\Desktop\\jsonLocal.json";
		game = new Game(path);
	}
	
	@Test
	public void RecibirBienvenida() {
		expected = "Te encuentras en el living de una casa. Luego de una larga siesta, te levantas con ganas de ir a un bar.";
		actuals = game.getWelcome();
		assertEquals(expected, actuals);
	}
	
	/*
	@Test
	public void () {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";
		
		// Ir a taberna
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	*/

}

