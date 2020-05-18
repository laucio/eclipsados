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
		expected = "El espejo se ha roto";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta", "item","espejo", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		
		assertEquals(expected,actuals);
	}
	
	@Test
	public void verAfterTriggerDeItem() {
		//Luego del trigger, el espejo ya no deberia esar en el muelle
		
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz. Hay un pirata fantasma. Al sur hay una taberna.";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta", "item","espejo", "item");
		jugador.switchearAction(action);
		
		//Luego del trigger, con remove, el espejo ya no deberia estar en el muelle
		actuals = jugador.verAlrededor();
		
		assertEquals(expected,actuals);//si el trigger de espejo tuviera remove en lugar de default
		//assertNotEquals(expected,actuals);//si en trigger self de espejo hay default
	}
	
	
	@Test
	public void usarItemSobreSelf() {
		expected = "Te ves muy bello hoy";
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo", "item","self", "self");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
		
	}
	
	@Test
	public void usarRociadorSobreSelf() {
		expected = "Que delicia de cerveza!";
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","self", "self");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
		
	}
	
	@Test
	public void usarBarretaSobreRociador() {
		//tenemos barreta, pero rociador esta en el place
		
		expected = "Se ha abollado la lata";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta","item","rociador con cerveza de raiz", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FUNCIONA
	}
	
	@Test
	public void usarBarretaSobreRociador_AmbosEnInventario_SePuedeUsar() {
		//tenemos barreta y rociador en el inventario
		
		expected = "Se ha abollado la lata";
		jugador.tomarItem("barreta");
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","barreta","item","rociador con cerveza de raiz", "item");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FALLA: CUANDO SE TIENE EL OBJETO Q REALIZA ACCION EN INVENTARIO Y EL Q LA RECIBE ESTA EN EL PLACE, NO DICE QUE NO ENCUENTRA EL SEGUNDO
	}
	
	
	
	@Test
	public void usarBarretaSobreSelf() {
		expected = "Se ha abollado la lata";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta","item","self", "self");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//REVISAR--- DEVUELVE "No tienes el objeto, cuando ya tienes la barreta
	}
	
	@Test
	public void usarEspejoSobreBarreta_AmbosEnInventario() {
		expected = "Se ha abollado la lata";
		jugador.tomarItem("espejo");
		jugador.tomarItem("barreta");
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//REVISAR--- DEVUELVE "No tienes ese objeto" y en realidad tiene ambos
	}

	@Test
	public void usarEspejoSobreBarreta_NingunoEnInventario() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreEspejo_SoloTieneEspejoEnInventario_SePuedeUsar() {
		expected = "No tienes ese objeto";
		jugador.tomarItem("espejo");
		action = new Action("usar","barreta","item","espejo", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejoSobreBarreta_SoloTienesBarretaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		jugador.tomarItem("barreta");
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//Da "Bien"... quiere usar algo que no tiene
	}
	
	@Test
	public void usarEspejoSobreBarreta_TieneAmbosEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		jugador.tomarItem("barreta");
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//Revisar...Deberia fallar...Tiene ambos... pero deberia decir que no puede usar espejo sobre barreta
	}
	
	@Test
	public void usarEspejoSobrePirata_EstaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//REVISAR
		//Dice que no tiene objeto, pero si lo tiene... el problema es que no puede usar el objeto sobre un npcs
	}
	
	@Test
	public void usarBarretaSobrePirata_EstaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//REVISAR, FALLA
		//Dice que no tiene objeto, pero si lo tiene... el problema es que no puede usar el objeto sobre un npcs
	}
	
	
	
	@Test
	public void usarRociadorSobrePirata_EstaEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FUNCIONA
		
	}

	@Test
	public void usarBarretaSobrePirata_NoEstaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";

		action = new Action("usar","barreta","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FUNCIONA... al vez deberia aclarar que es algo que no se puede hacer
		
	}
	
	
	
	
	/*
	@Test
	public void esEndgame() {
		boolean esperado = true;
		action = new Action("move","taberna","location",null,null);
		Endgame fin = juego.getEndgames().get(0);
		esperado = fin.esEndgame(action);
		assertEquals(true,esperado);
	}
	*/
	
	

}
