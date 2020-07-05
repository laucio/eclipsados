package test.hit.points;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import juego_de_aventura.Action;
import juego_de_aventura.Adventure;
import juego_de_aventura.Game;
import juego_de_aventura.Player;

public class TestHitPoints {

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
		
				

}

