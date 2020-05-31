package juegotest2;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import juego_de_aventura.*;

public class GoToTest {

	String path;
	Game game;
	Adventure adventure;
	Player player;
	Action action;
	String expected;
	String actuals;

	@Before
	public void setup() throws IOException {
		path = "Juego.json";
		game = new Game(path);
	}

///////////MOVERSE//////////////////////////////////////

	@Test
	public void moverseATabernaConObstaculo() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";

		// Ir a taberna
		action = new Action("ir", "taberna", "location", null, null);
		actuals = game.movePlayer(action);
		assertEquals(expected, actuals);

	}

}
