package traductortest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import juego_de_aventura.*;
import traductor_de_comandos.*;

public class CommandTraductorTest {
	
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
		path = "Adventures/Juego.json";
		game = new Game(path);
	}
	
	@Test
	public void moverseATabernaConObstaculo() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";
		
		command = "Ir a taberna";
		
		// Ir a taberna
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);

	}
	
	/////////VER INVENTARIO////////////////////////////////////////////////
	
	@Test
	public void verInventarioVacio() {
		expected = "Tu inventario esta vacio.";
		
		command = "inspeccionar inventario";
		
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	// action, thing,condition, target,  effect_over
	@Test
	public void verInventarioConUnElemento() {
		expected = "En tu inventario hay una barreta.";
		
		command = "agarrar barreta";
		actuals = game.processCommand(command);
		
		command = "ver inventario";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void verInventarioConVariosElementos() {
		expected = "En tu inventario hay una barreta, un espejo, y un rociador con cerveza de raiz.";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "recoger espejo";
		actuals = game.processCommand(command);
		
		command = "agarrar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "mirar inventario";
		actuals = game.processCommand(command);
		
		assertEquals(expected, actuals);
	}
	
	/////////TOMAR ELEMENTO/////////////////////////////////////////
	
	@Test
	public void tomarUnElemento() {
		expected = "Tienes una barreta";
		
		command = "Tomar Barreta";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void tomarDosVecesUnElemento() {
		expected = "No encuentro ese objeto.";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void tomarItemCuandoNoHayNinguno() {
		expected = "No encuentro ese objeto.";	
		//Tomamos la barreta
		command = "tomar barreta";
		actuals = game.processCommand(command);
		//Tomamos rociador
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		//Tomamos espejo
		command = "tomar espejo";
		actuals = game.processCommand(command);
		//Miramos alrededor habiendo sacado la barreta	
		command = "tomar telefono";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	///////MIRAR ALREDEDOR////////////////////////////
	
	@Test
	public void verAlrededor() {
		expected = "Estas en un muelle. En el suelo hay: una barreta , un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";
		
		command = "mirar alrededores";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void mirarAlrededorSacandoUnItem() {
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";	
		//Tomamos la barreta
		command = "recoger barreta";
		actuals = game.processCommand(command);
		//Miramos alrededor habiendo sacado la barreta	
		command = "ver alrededores";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	@Test
	public void mirarAlrededorSinItems() {
		expected = "Estas en un muelle. Hay un pirata fantasma. Al sur hay una taberna.";	
		//Tomamos la barreta
		command = "tomar barreta";
		actuals = game.processCommand(command);
		//Tomamos rociador
		command = "agarrar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		//Tomamos espejo
		command = "recoger espejo";
		actuals = game.processCommand(command);
		//Miramos alrededor habiendo sacado la barreta	
		command = "ver alrededor";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	////PRUEBAS DE TRIGGERS////////////////////////////////////////
	
	@Test
	public void dispararTrigger() {
		
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";	
		
		//Ir a taberna	
		command = "ir a la taberna";
		actuals = game.processCommand(command);
		//Eliminar obstaculo
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
		
	
	@Test
	public void eliminarObstaculoConTrigger() {
		expected = "Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.\nFIN.";	
		
		//Ir a taberna	
		command = "ir a sur";
		actuals = game.processCommand(command);
		//Eliminar obstaculo
		command = "agarrar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usa rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		//Ir a taberna	
		command = "ir taberna";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	@Test
	public void eliminarObstaculoDosVeces() {
		expected = "Eso no ha servido de nada.";	
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	///////////MOVERSE//////////////////////////////////////
	
	@Test
	public void moverseATabernaConObstaculoOld() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";	
		
		//Ir a taberna	
		command = "caminar a taberna";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void irALocation_SePuedeIr() {
		expected = "Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.\nFIN.";	
		//Eliminar obstaculo
		command = "toma rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usa rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		//Ir a taberna
		command = "ir hacia el sur";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	@Test
	public void irALocationActual() {
		expected = "Ya te encuentras aqui.";	
		
		//Ir a muelle	
		command = "ir al muelle";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	
	@Test
	public void irAPuntoCardinalSinAcceso() {
		expected = "No puedes ir hacia alla.";	
		
		//Ir a cualquier lugar que no sea location	
		command = "ir al sudeste";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	@Test
	public void irADirection_SePuedeIr() {
		expected = "Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.\nFIN.";	
		//Eliminar obstaculo
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		command = "usar rociador con cerveza de raiz pirata fantasma";
		actuals = game.processCommand(command);
		//Ir a taberna	
		command = "ir al sur";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void irADirection_NoSePuedeIr() {
		expected = "No puedes ir hacia alla."; 
		//Ir al norte	
		command = "Ir al Norte";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	/////////////HABLAR CON NPC//////////////////////////////////////////////
	
	@Test
	public void hablarConNpcVigente() {
		expected = "No hay nada que me digas que me haga cambiar de opinion!";	
			
		command = "charlar con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	
	@Test
	public void hablarConNpcBorrado() {
		expected = "Nadie te respondera...";	
		
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		//Eliminar obstaculo
		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		
		//Hablar con npcs
		command = "hablar con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
		
	}
	
	////////////////USO DE ITEMS//////////////////////////////
	@Test
	public void UsarRociadorConNpcQueNoEstaEnPlace() {
		expected = "Eso no ha servido de nada.";	
		
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		//Eliminar obstaculo
		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		
		//intenar volver a usar rociador sobre fantasma
		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected, actuals);
	}
	
	
	@Test
	public void usarRociadorSobrePirata_TengoEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
	
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarEspejoSobrePirata_TengoEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		
		command = "tomar espejo";
		actuals = game.processCommand(command);
		
		command = "usar espejo con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
			
	}
	
	
	@Test
	public void usarBarretaSobrePirata_EstaEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usar barreta con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
		
	}
	
	
	@Test
	public void usarBarretaSobrePirata_NoEstaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";

		command = "usar barreta con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);	
	}
	
	
	
	@Test
	public void usarRociadorSobrePirata_EstaEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);

		command = "usar rociador con cerveza de raiz con pirata fantasma";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	///USAR ITEM SOBRE ITEM//////////////////////
	
	@Test
	public void usarBarretaSobreEspejo_TengoSoloBarreta_SePuedeUsar() {
		expected = "El espejo se ha roto";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usar barreta en espejo";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void verAfterTriggerDeItem() {
		//Luego del trigger, el espejo ya no deberia esTar en el muelle
		//REMOVE DE PLACE
		
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz. Hay un pirata fantasma. Al sur hay una taberna.";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usa barreta con espejo";
		actuals = game.processCommand(command);
		
		//Luego del trigger, con remove, el espejo ya no deberia estar en el muelle
		command = "ver alrededor";
		actuals = game.processCommand(command);
		
		assertEquals(expected,actuals);//si el trigger de espejo tuviera remove en lugar de default
		//assertNotEquals(expected,actuals);//si en trigger self de espejo hay default
		
	}
	
	
	@Test
	public void usarEspejoSobreSelf_LoTengoEnInventario() {
		expected = "Oh, no! Acabas de descubrir que tu tambien eres un pirata fantasma... El horror!\nFIN.";
		
		command = "tomar espejo";
		actuals = game.processCommand(command);
		
		command = "usar espejo";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
		//FUNCIONA
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
	}
	
	
	@Test
	public void usarEspejoSobreSelf_NoLoTengoEnInventario() {
		expected = "No tienes ese objeto.";
		
		command = "usar espejo";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarRociadorSobreSelf() {
		expected = "Que delicia de cerveza!";
		
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
		//FUNCIONA
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
	}
	
	
	@Test
	public void usarBarretaSobreSelf_LoTengoEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usar barreta";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarBarretaSobreSelf_NoLoTengoEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		
		command = "usar barreta";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarBarretaSobreRociador() {
		//tenemos barreta, pero rociador esta en el place
		
		expected = "Se ha abollado la lata";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usa barreta con rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarBarretaSobreRociador_AmbosEnInventario_SePuedeUsar() {
		//tenemos barreta y rociador en el inventario
		
		expected = "Se ha abollado la lata";
	
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "tomar rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		
		command = "usar barreta con rociador con cerveza de raiz";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
		
	}
	
	
	@Test
	public void usarEspejoSobreBarreta_AmbosEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		
		command = "tomar espejo";
		actuals = game.processCommand(command);
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usar espejo en barreta";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);

	}
	
	
	@Test
	public void usarEspejoSobreBarreta_NingunoEnInventario() {
		expected = "No tienes ese objeto.";
		
		command = "usar espejo sobre barreta";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	
	@Test
	public void usarBarretaSobreEspejo_SoloTieneEspejoEnInventario_SePuedeUsar() {
		expected = "No tienes ese objeto.";
		
		action = new Action("tomar","espejo", "item",null, null);
		command = "tomar espejo";
		actuals = game.processCommand(command);
		
		command = "usar barreta en espejo";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarEspejoSobreBarreta_SoloTienesBarretaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		
		command = "tomar barreta";
		actuals = game.processCommand(command);
		
		command = "usar espejo en barreta";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
	@Test
	public void usarEspejo_NoEstaEnInventario_SePuedeUsarSelf() {
		expected = "No tienes ese objeto.";
		
		command = "usar espejo";
		actuals = game.processCommand(command);
		assertEquals(expected,actuals);
	}
	
	
}
