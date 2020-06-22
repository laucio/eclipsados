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
	
	@Test
	public void dispararTriggerDeTrollConCuchillo() {
		
		expected = "Atacas al troll con el cuchillo. Luego de un ligero corte, tu contrincante huye corriendo entre sollozos. Al parecer no era tan temible...";
		//Ir a cocina	
		command = "ir cocina";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		
		//tomar cuchillo
		command = "tomar cuchillo";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		
		//ir living
		command = "ir living";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		
		//ir sur (no hay puerta)
		command = "ir sur";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		
		
		//ir oeste (bar)
		command = "ir oeste";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		
		
		/*
		//atacar troll con cuchillo
		command = "usar cuchillo con troll";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		*/
		/*
		command = "hablar con troll";
		actuals = game.processCommand(command);
		System.out.println(actuals);
		*/
		assertEquals(expected, actuals);
		
	}


}

