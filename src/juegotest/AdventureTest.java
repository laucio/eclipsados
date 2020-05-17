package juegotest;

import source.*;
import static org.junit.Assert.*;


import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;

public class AdventureTest {

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
	public void verAlrededor() {
		action = new Action("ver alrededor",null, null,null, null);
		expected = "Estas en un muelle. En el suelo hay: una barreta , un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void accionNoReconocida() {
		action = new Action("ver netflix",null, null,null, null);
		expected = "No entiendo que quieres hacer";
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void verInventarioVacio() {
		action = new Action("ver inventario",null, null,null, null);
		expected = "Tu inventario esta vacio.";
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	
	// action, thing,condition, target,  effect_over
	@Test
	public void verInventarioConUnElemento() {
		action = new Action("tomar","barreta", "item",null, null);
		expected = "Tienes una barreta";
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void tomarDosVecesUnElemento() {
		expected = "No encuentro ese objeto.";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = jugador.switchearAction(action);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void mirarAlrededorSacandoUnItem() {
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";	
		//Tomamos la barreta
		action = new Action("tomar","barreta", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Miramos alrededor habiendo sacado la barreta	
		action = new Action("ver alrededor",null, null,null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void mirarAlrededorSinItems() {
		expected = "Estas en un muelle. Hay un pirata fantasma. Al sur hay una taberna.";	
		//Tomamos la barreta
		action = new Action("tomar","barreta", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Tomamos rociador
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Tomamos espejo
		action = new Action("tomar","espejo", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Miramos alrededor habiendo sacado la barreta	
		action = new Action("ver alrededor",null, null,null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void tomarItemCuandoNoHayNinguno() {
		expected = "No encuentro ese objeto.";	
		//Tomamos la barreta
		action = new Action("tomar","barreta", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Tomamos rociador
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Tomamos espejo
		action = new Action("tomar","espejo", "item",null, null);
		actuals = jugador.switchearAction(action);
		//Miramos alrededor habiendo sacado la barreta	
		action = new Action("tomar","telefono", "item",null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}

	@Test
	public void moverseATabernaConObstaculo() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void dispararTrigger() {
		
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		//Eliminar obstaculo
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	 /*@Test
	 public void  NuevoDispTrigger() throws JsonIOException, IOException {
		 ArrayList<String> targets = new ArrayList<String>();
		targets.add("pirata fantasma");
		jugador.getAventura().getItems().get(1).setTargets(targets);
        		
		
        String filePath = "Juego.json";
        expected = "No encuentro ese objeto."; 
       
        SavedGame.saveStatus(jugador.getAventura(), filePath);
       
    }*/
	
	
	@Test
	public void eliminarObstaculoConTrigger() {
		expected = "Estas en una sucia taberna. Al norte hay un muelle.";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		//Eliminar obstaculo
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void eliminarObstaculoDosVeces() {
		expected = "No ha servido de nada.";	
		//Eliminar obstaculo
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		actuals = jugador.switchearAction(action);
		//Ir a taberna	
		//action = new Action("ir","taberna", "location",null, null);
		//actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void irALocationNoExistente() {
		expected = "No se a donde quieres ir.";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		//Eliminar obstaculo
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void hablarConNpcVigente() {
		expected = "No hay nada que me digas que me haga cambiar de opinion!";	
		
		//Ir a taberna	
		action = new Action("hablar",null, null,"pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void hablarConNpcBorrado() {
		expected = "Nadie te respondera...";	
		
		jugador.tomarItem("rociador con cerveza de raiz");
		//Eliminar obstaculo
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		
		//Ir a taberna	
		action = new Action("hablar",null, null,"pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void usarItemSobrePirata() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarItemSobreItem() {
		expected = "Usar item sobre item.";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta", "item","espejo", "item");
		actuals = jugador.switchearAction(action);
		
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarItemSobreSelf() {
		expected = "Usar item sobre uno mismo.";
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo", "item",null, "self");
		actuals = jugador.switchearAction(action);
		
		assertEquals(expected,actuals);
	}
	
	@Test
	public void esEndgame() {
		boolean esperado = true;
		action = new Action("move","taberna","location",null,null);
		Endgame fin = juego.getEndgames().get(0);
		esperado = fin.esEndgame(action);
		assertEquals(true,esperado);
	}
	
	
	

}
