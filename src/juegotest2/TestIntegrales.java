package juegotest2;

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
	String actuals;

	@Before
	public void setup() throws IOException {
		path = "Adventures/Aventura_Pirata_Fantasma.json";
		game = new Game(path);
	}

///////////MOVERSE//////////////////////////////////////

	@Test
	public void moverseATabernaConObstaculo() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";

		// Ir a taberna
		action = new Action("ir", "taberna", "location", null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);

	}
	
//////BIENVENIDA DE INICIO DE JUEGO/////////////////////////////////
	
	@Test
	public void RecibirBienvenida() {
		expected = "Te encuentras en un muelle. Es de noche pero la luna ilumina todo el lugar. En el suelo hay algunos objetos, y sientes muchas ganas de ir hacia una taberna.";
		actuals = game.getWelcome();
		assertEquals(expected, actuals);
	}
	
	


	
	/////////VER INVENTARIO////////////////////////////////////////////////
	
	@Test
	public void verInventarioVacio() {
		expected = "Tu inventario esta vacio.";
		action = new Action("ver inventario",null, null,null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
	}
	
	
	// action, thing,condition, target,  effect_over
	@Test
	public void verInventarioConUnElemento() {
		expected = "En tu inventario hay una barreta.";
		
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		action = new Action("ver inventario",null, null,null, null);
		actuals = game.processAction(action);
		
		assertEquals(expected, actuals);
	}
	
	@Test
	public void verInventarioConVariosElementos() {
		expected = "En tu inventario hay una barreta, un espejo y un rociador con cerveza de raiz.";
		
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("ver inventario",null, null,null, null);
		actuals = game.processAction(action);

		assertEquals(expected, actuals);
	}
	
	/////////TOMAR ELEMENTO/////////////////////////////////////////
	
	@Test
	public void tomarUnElemento() {
		action = new Action("tomar","barreta", "item",null, null);
		expected = "Tienes una barreta";
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
	}
	
	
	
	@Test
	public void tomarItemCuandoNoHayNinguno() {
		expected = "No encuentro ese objeto. Tal vez lo tengas en tu inventario";	
		//Tomamos la barreta
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		//Tomamos rociador
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		//Tomamos espejo
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		//Miramos alrededor habiendo sacado la barreta	
		action = new Action("tomar","telefono", "item",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
	}
	
	
	///////MIRAR ALREDEDOR////////////////////////////
	
	@Test
	public void verAlrededor() {
		expected = "Estas en un muelle. En el suelo hay: una barreta , un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";
		action = new Action("ver alrededor",null, null,null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void mirarAlrededorSacandoUnItem() {
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";	
		//Tomamos la barreta
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		//Miramos alrededor habiendo sacado la barreta	
		action = new Action("ver alrededor",null,null,null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void mirarAlrededorSinItems() {
		expected = "Estas en un muelle. Hay un pirata fantasma. Al sur hay una taberna.";	
		//Tomamos la barreta
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		//Tomamos rociador
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		//Tomamos espejo
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		//Miramos alrededor habiendo sacado la barreta	
		action = new Action("ver alrededor",null, null,null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	////PRUEBAS DE TRIGGERS////////////////////////////////////////
	
	@Test
	public void dispararTrigger() {
		
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		//Eliminar obstaculo
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
		
	
	@Test
	public void eliminarObstaculoConTrigger() {
		expected = "Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.\nFIN.";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		//Eliminar obstaculo
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void eliminarObstaculoDosVeces() {
		expected = "Eso no ha servido de nada.";	
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);	
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	///////////MOVERSE//////////////////////////////////////
	
	@Test
	public void moverseATabernaConObstaculoOld() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
		
	@Test
	public void irALocation_SePuedeIr() {
		expected = "Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.\nFIN.";	
		//Eliminar obstaculo
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void irALocationActual() {
		expected = "Ya te encuentras aqui.";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		//Eliminar obstaculo
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = game.processAction(action);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	
	
	@Test
	public void irAPuntoCardinalSinAcceso() {
		expected = "No puedes ir hacia alla.";	
		
		//Ir a cualquier lugar que no sea location	
		action = new Action("ir","sudeste", "direction",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	
	
	@Test
	public void irADirection_SePuedeIr() {
		expected = "Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.\nFIN.";	
		//Eliminar obstaculo
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		//Ir a taberna	
		action = new Action("ir","sur", "direction",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void irADirection_NoSePuedeIr() {
		expected = "No puedes ir hacia alla."; 
		//Ir al norte	
		action = new Action("ir","norte", "direction",null, null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	
	/////////////HABLAR CON NPC//////////////////////////////////////////////
	
	@Test
	public void hablarConNpcVigente() {
		expected = "No hay nada que me digas que me haga cambiar de opinion!";	
		
		//Ir a taberna	
		action = new Action("hablar",null, "npcs","pirata fantasma", null);
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	
	@Test
	public void hablarConNpcBorrado() {
		expected = "Nadie te respondera...";	
		
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		//Eliminar obstaculo
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		
		//Hablar con npcs
		action = new Action("hablar",null, null,"pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
		
	}
	
	////////////////USO DE ITEMS//////////////////////////////
	
	@Test
	public void UsarRociadorConNpcQueNoEstaEnPlace() {
		expected = "Eso no ha servido de nada.";	
		
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		//Eliminar obstaculo
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		
		//intenar volver a usar rociador sobre fantasma
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void usarRociadorSobrePirata_TengoEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejoSobrePirata_TengoEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","espejo", "item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
			
	}
	
	@Test
	public void usarBarretaSobrePirata_EstaEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta","item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
		
	}
	
	@Test
	public void usarBarretaSobrePirata_NoEstaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";

		action = new Action("usar","barreta","item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);	
	}
	
	
	
	@Test
	public void usarRociadorSobrePirata_EstaEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);

		action = new Action("usar","rociador con cerveza de raiz","item","pirata fantasma", "npcs");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	
	///USAR ITEM SOBRE ITEM//////////////////////
	
	@Test
	public void usarBarretaSobreEspejo_TengoSoloBarreta_SePuedeUsar() {
		expected = "El espejo se ha roto";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta", "item","espejo", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void verAfterTriggerDeItem() {
		//Luego del trigger, el espejo ya no deberia esTar en el muelle
		//REMOVE DE PLACE
		
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz. Hay un pirata fantasma. Al sur hay una taberna.";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta", "item","espejo", "item");
		actuals = game.processAction(action);
		
		//Luego del trigger, con remove, el espejo ya no deberia estar en el muelle
		action = new Action("ver alrededor",null, null,null, null);
		actuals = game.processAction(action);
		
		assertEquals(expected,actuals);//si el trigger de espejo tuviera remove en lugar de default
		//assertNotEquals(expected,actuals);//si en trigger self de espejo hay default
		
	}
	
	
	@Test
	public void usarEspejoSobreSelf_LoTengoEnInventario() {
		expected = "Oh, no! Acabas de descubrir que tu tambien eres un pirata fantasma... El horror!\nFIN.";
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","espejo", "item","self", "self");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
		//FUNCIONA
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
	}
	
	@Test
	public void usarEspejoSobreSelf_NoLoTengoEnInventario() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","espejo", "item","self", "self");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarRociadorSobreSelf() {
		expected = "Que delicia de cerveza!";
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","self", "self");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
		//FUNCIONA
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
	}
	
	@Test
	public void usarBarretaSobreSelf_LoTengoEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta", "item","self", "self");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreSelf_NoLoTengoEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","barreta", "item","self", "self");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreRociador() {
		//tenemos barreta, pero rociador esta en el place
		
		expected = "Se ha abollado la lata";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta","item","rociador con cerveza de raiz", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreRociador_AmbosEnInventario_SePuedeUsar() {
		//tenemos barreta y rociador en el inventario
		
		expected = "Se ha abollado la lata";
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta","item","rociador con cerveza de raiz", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
		
	}
	
	
	@Test
	public void usarEspejoSobreBarreta_AmbosEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);

	}
	
	@Test
	public void usarEspejoSobreBarreta_NingunoEnInventario() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreEspejo_SoloTieneEspejoEnInventario_SePuedeUsar() {
		expected = "No tienes ese objeto.";
		
		action = new Action("tomar","espejo", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","barreta","item","espejo", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejoSobreBarreta_SoloTienesBarretaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		
		action = new Action("tomar","barreta", "item",null, null);
		actuals = game.processAction(action);
		
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejo_NoEstaEnInventario_SePuedeUsarSelf() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","espejo","item","self", "self");
		actuals = game.processAction(action);
		assertEquals(expected,actuals);
	}

}
