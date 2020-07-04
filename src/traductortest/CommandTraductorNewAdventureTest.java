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
		path = "Adventures\\jsonLocal.json";
		game = new Game(path);
	}
	
	@Test
	public void RecibirBienvenida() {
		expected = "Te encuentras en el living de una casa. Luego de una larga siesta, te levantas con ganas de ir a un bar.";
		actuals = game.getWelcome();
		assertEquals(expected, actuals);
	}
	
	////////////DISPARAR TRIGGERS
	
	@Test
	public void dispararTriggerDeTrollConCuchilloVerboUsar() {
		
		expected = "Atacas al troll con el cuchillo. Luego de un ligero corte, tu contrincante huye corriendo entre sollozos. Al parecer no era tan temible...";
		
		command = "ir cocina";
		actuals = game.processCommand(command);
		
		command = "tomar cuchillo";
		actuals = game.processCommand(command);
		
		command = "ir living";
		actuals = game.processCommand(command);
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "ir oeste";
		actuals = game.processCommand(command);
		
		command = "usar cuchillo con troll";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	@Test
	public void dispararTriggerDeTrollSinCuchilloVerboUsar() {
		
		expected = "No tienes ese objeto.";
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "ir oeste";
		actuals = game.processCommand(command);
		
		command = "usar cuchillo con troll";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
		
	}
	
	///////////VERBO ATACAR A NPC
	
	@Test
	public void dispararTriggerDeTrollConCuchilloVerboAtacar() {
		
		expected = "Atacas al troll con el cuchillo. Luego de un ligero corte, tu contrincante huye corriendo entre sollozos. Al parecer no era tan temible...";
			
		command = "ir cocina";
		actuals = game.processCommand(command);
		
		command = "tomar cuchillo";
		actuals = game.processCommand(command);
		
		command = "ir living";
		actuals = game.processCommand(command);
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "ir oeste";
		actuals = game.processCommand(command);
		
		command = "atacar troll con cuchillo";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	@Test
	public void dispararTriggerDeTrollSinCuchilloVerboAtacar() {
		
		expected = "No tienes ese objeto.";
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "ir oeste";
		actuals = game.processCommand(command);
		
		command = "atacar troll con cuchillo";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void noSabeAQuienAtacarNiConQue() {
		
		expected = "A quien y con que? Debes ser mas claro";
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "atacar";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	@Test
	public void noSabeConQueAtacar() {
		
		expected = "No entiendo con que cosa quieres atacar";
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "atacar troll";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	@Test
	public void noSabeAQuienAtacar() {
		
		expected = "No entiendo a quien quieres atacar";
		
		command = "ir sur";
		actuals = game.processCommand(command);
		
		command = "atacar con cuchillo";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	/////////VERBO OBSERVAR
	
	@Test
	public void observarObjetoEnInventarioConTrigger() {
		expected = "Oh... soy hermoso...";
		
		command = "tomar espejo";
		actuals = game.processCommand(command);
		
		command = "observar espejo";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void observarObjetoEnInventarioSinTrigger() {
		expected = "No tiene nada en especial";
		
		command = "Ir a la cocina";
		actuals = game.processCommand(command);
		
		command = "agarrar el cuchillo";
		actuals = game.processCommand(command);
		
		command = "observar cuchillo";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}


}

