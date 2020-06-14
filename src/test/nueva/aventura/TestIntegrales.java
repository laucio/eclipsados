package test.nueva.aventura;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import juego_de_aventura.*;


public class TestIntegrales {
	String path;
	Game game;
	Adventure adventure;
	Player player;
	Action action;
	String expected;
	String actual;

	@Before
	public void setup() throws IOException {
		path = "aventura_prueba.json";
		game = new Game(path);
	}
	
	///////////////// NUEVA BIENVENIDA ////////////////////////////////////
	
	@Test
	public void RecibirBienvenida() {
		expected = "Te encuentras en una cabania. Luego de una larga siesta, te levantas con ganas de ir a un bar.";
		actual = game.getWelcome();
		assertEquals(expected, actual);
	}
	
///////////////// CURRENT LOCATION CORRECTA ////////////////////////////////////

	@Test
	public void CurrentLocationCorrecta() {
		expected = "Estas en una cabania. En el comedor hay: un sanguche de atun. Hay una tu vieja. Al oeste hay un bar. Al este hay un mercado.";
		actual = game.makePlayerLookAround();
		assertEquals(expected, actual);
	}
	
	/////////// TOMAR NUEVO ITEM EN JSON ///////////////////////////
	
	@Test
	public void TomarSanguche() {
		expected = "En tu inventario hay un sanguche de atun.";
		Action action = new Action("tomar","sanguche de atun","item",null,null);
		game.makePlayerTakeItem(action);
		actual = game.makePlayerWatchInventory();
		assertEquals(expected, actual);
	}
	
	//////////////// COMER SANGUCHE ///////////////
	
	@Test
	public void ComerSanguche() {
		expected = "Mmmm rikoooo, ahora tengo mas energia.";
		Action action = new Action("tomar","sanguche de atun","item",null,null);
		game.makePlayerTakeItem(action);
		action = new Action("usar","sanguche de atun","item","self","self");
		actual = game.makePlayerUseItem(action);
		assertEquals(expected, actual);
	}
	
}
