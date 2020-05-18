package juegotest;
import source.*;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class EndgameTest {

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
	
	///////// PRUEBA SI EVALUA BIEN LA CONDICION DE ENDGAME ////////////
	
	@Test
	public void esEndgameEspejo() {
		expected = "Te ves horrible!\n¡Oh, no! Acabas de descubrir que tú también eres un pirata fantasma... ¡el horror!";
		boolean expectedBoolean = true;
		jugador.tomarItem("espejo");
		Action accion = new Action("usar","espejo","item","self","self");
		ResultadoInstruccion result = jugador.recibirInstruccion(accion);
		actuals = result.getResultado();
		assertEquals(expected, actuals);
		assertEquals(expectedBoolean, result.isEsEndgame());
	}
	
	@Test
	public void esEndgameTaberna() {
		expected = "Estas en una sucia taberna. Al norte hay un muelle.\n¡Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.";
		boolean expectedBoolean = true;
		Action accion = new Action("tomar","rociador con cerveza de raiz","item");
		jugador.switchearAction(accion);
		accion = new Action("usar","rociador con cerveza de raiz","item","pirata fantasma","npcs");
		jugador.switchearAction(accion);
		accion = new Action("ir","taberna","location");
		ResultadoInstruccion result = jugador.recibirInstruccion(accion);
		actuals = result.getResultado();
		assertEquals(expected, actuals);
		assertEquals(expectedBoolean, result.isEsEndgame());
	}
	

}
