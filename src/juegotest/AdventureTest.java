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
	
	//////BIENVENIDA DE INICIO DE JUEGO/////////////////////////////////
	
	@Test
	public void RecibirBienvenida() {
		expected = "Te encuentras en un muelle. Es de noche pero la luna ilumina todo el lugar. En el suelo hay algunos objetos, y sientes muchas ganas de ir hacia una taberna.";
		actuals = jugador.getWelcome();
		assertEquals(expected, actuals);
	}
	
	
	////////////ACCION NO RECONOCIDA///////////////////////////////////////
	
	@Test
	public void accionNoReconocida() {
		action = new Action("ver netflix",null, null,null, null);
		expected = "No entiendo que quieres hacer";
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	/////////VER INVENTARIO////////////////////////////////////////////////
	
	@Test
	public void verInventarioVacio() {
		expected = "Tu inventario esta vacio.";
		action = new Action("ver inventario",null, null,null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	
	// action, thing,condition, target,  effect_over
	@Test
	public void verInventarioConUnElemento() {
		expected = "En tu inventario hay una barreta.";
		
		action = new Action("tomar","barreta", "item",null, null);
		actuals = jugador.switchearAction(action);
		
		action = new Action("ver inventario",null, null,null, null);
		actuals = jugador.switchearAction(action);
		
		assertEquals(expected, actuals);
	}
	
	@Test
	public void verInventarioConVariosElementos() {
		expected = "En tu inventario hay una barreta, un espejo, y un rociador con cerveza de raiz.";
		
		action = new Action("tomar","barreta", "item",null, null);
		actuals = jugador.switchearAction(action);
		
		action = new Action("tomar","espejo", "item",null, null);
		actuals = jugador.switchearAction(action);
		
		action = new Action("tomar","rociador con cerveza de raiz", "item",null, null);
		actuals = jugador.switchearAction(action);
		
		action = new Action("ver inventario",null, null,null, null);
		actuals = jugador.switchearAction(action);
		
		assertEquals(expected, actuals);
	}
	
	/////////TOMAR ELEMENTO/////////////////////////////////////////
	
	@Test
	public void tomarUnElemento() {
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
	
	
	///////MIRAR ALREDEDOR////////////////////////////
	
	@Test
	public void verAlrededor() {
		action = new Action("ver alrededor",null, null,null, null);
		expected = "Estas en un muelle. En el suelo hay: una barreta , un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna.";
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
	
	////PRUEBAS DE TRIGGERS////////////////////////////////////////
	
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
	
	///////////MOVERSE//////////////////////////////////////
	
	@Test
	public void moverseATabernaConObstaculo() {
		expected = "No puedes pasar! El pirata fantasma no te dejara pasar";	
		
		//Ir a taberna	
		action = new Action("ir","taberna", "location",null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	@Test
	public void irALocation_SePuedeIr() {
		expected = "Estas en una sucia taberna. Al norte hay un muelle.";	
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
	public void irADirection_SePuedeIr() {
		expected = "Estas en una sucia taberna. Al norte hay un muelle.";	
		//Eliminar obstaculo
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		//Ir a taberna	
		action = new Action("ir","sur", "direction",null, null);
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void irADirection_NoSePuedeIr() {
		expected = "No puedes ir por ahi."; //Mensaje propuesto
		//Ir a taberna	
		action = new Action("ir","norte", "direction",null, null);
		actuals = jugador.switchearAction(action);//actuals tiene "No se a donde quieres ir."
		System.out.println(actuals);
		assertEquals(expected, actuals);
		
		//FALLA
		//DICE QUE NO SABE DONDE SE QUIERE IR
		//NORTE ES UNA DIRECCION
		//DEBERIA DECIR QUE NO PUEDE IR EN ESA DIRECCION
	}
	
	
	/////////////HABLAR CON NPC//////////////////////////////////////////////
	
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
		
		//Hablar con npcs
		action = new Action("hablar",null, null,"pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
		
	}
	
	////////////////USO DE ITEMS//////////////////////////////
	
	@Test
	public void UsarRociadorConNpcQueNoEstaEnPlace() {
		expected = "No ha servido de nada.";	
		
		jugador.tomarItem("rociador con cerveza de raiz");
		//Eliminar obstaculo
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		
		//intenar volver a usar rociador sobre fantasma
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	
	@Test
	public void usarRociadorSobrePirata_TengoEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejoSobrePirata_TengoEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo", "item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FALLA... SI SE TIENE EN INVENTARIO. DEBERIA DECIR QUE NO SE PUEDE REALIZAR ESA ACCION O QUE NO TIENE EFECTO
		
	}
	
	@Test
	public void usarBarretaSobrePirata_EstaEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FALLA
		//Dice que no tiene objeto, pero si lo tiene... 
		//el problema es que no puede usar este objeto sobre un npcs
	}
	
	@Test
	public void usarBarretaSobrePirata_NoEstaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";

		action = new Action("usar","barreta","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);	
	}
	
	@Test
	public void usarRociadorSobrePirata_EstaEnInventario_SePuedeUsar() {
		expected = "Me encanta la cerveza de raiz! El pirata fantasma se veia entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzo a desintegrarse. La mitad de arriba de su cuerpo se desvanecio, y las piernas inmediatamente echaron a correr.";
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz","item","pirata fantasma", "npcs");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreEspejo_TengoSoloBarreta_SePuedeUsar() {
		expected = "El espejo se ha roto";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta", "item","espejo", "item");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void verAfterTriggerDeItem() {
		//Luego del trigger, el espejo ya no deberia esar en el muelle
		//REMOVE DE PLACE
		
		expected = "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz. Hay un pirata fantasma. Al sur hay una taberna.";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta", "item","espejo", "item");
		jugador.switchearAction(action);
		
		//Luego del trigger, con remove, el espejo ya no deberia estar en el muelle
		actuals = jugador.verAlrededor();
		
		assertEquals(expected,actuals);//si el trigger de espejo tuviera remove en lugar de default
		//assertNotEquals(expected,actuals);//si en trigger self de espejo hay default
		
		//FUNCIONA
	}
	
	
	@Test
	public void usarEspejoSobreSelf_LoTengoEnInventario() {
		expected = "Te ves muy bello hoy";
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo", "item","self", "self");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
		//FUNCIONA
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
	}
	
	@Test
	public void usarEspejoSobreSelf_NoLoTengoEnInventario() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","espejo", "item","self", "self");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarRociadorSobreSelf() {
		expected = "Que delicia de cerveza!";
		jugador.tomarItem("rociador con cerveza de raiz");
		action = new Action("usar","rociador con cerveza de raiz", "item","self", "self");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		//FUNCIONA
		//Cuando se quiera usar un objeto sobre si mismo, en la action se usa self para target y effect_over 
		//si se quiere q el item dispare un trigger, debe tener en su array de targets "self" y su trigger debe tener: type="item", 
	}
	
	@Test
	public void usarBarretaSobreSelf_LoTengoEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta", "item","self", "self");
		actuals = jugador.switchearAction(action);
		assertNotEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreSelf_NoLoTengoEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","barreta", "item","self", "self");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreRociador() {
		//tenemos barreta, pero rociador esta en el place
		
		expected = "Se ha abollado la lata";
		jugador.tomarItem("barreta");
		action = new Action("usar","barreta","item","rociador con cerveza de raiz", "item");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
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
		
		//FALLA: CUANDO SE TIENEN EN INVENTARIO EL OBJETO Q REALIZA ACCION Y EL Q LA RECIBE, DICE QUE NO ENCUENTRA EL SEGUNDO
		//CUANDO BUSCA EL SEGUNDO, TAMBIEN DEBE BUSCARLO EN INVENTARIO... SI HAY REMOVE, TAMBIN DEBE QUITARLO DE INVENTARIO
		//DEBERIA FUNCIONAR
	}
	
	
	@Test
	public void usarEspejoSobreBarreta_AmbosEnInventario_NoSePuedeUsar() {
		expected = "Se ha abollado la lata";
		jugador.tomarItem("espejo");
		jugador.tomarItem("barreta");
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FALLA
		//REVISAR--- DEVUELVE "No tienes ese objeto" y en realidad tiene ambos
		//Deberia decir que no puede realizar eso o que la accion no tiene efecto
	}

	@Test
	public void usarEspejoSobreBarreta_NingunoEnInventario() {
		expected = "No tienes ese objeto.";
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarBarretaSobreEspejo_SoloTieneEspejoEnInventario_SePuedeUsar() {
		expected = "No tienes ese objeto.";
		jugador.tomarItem("espejo");
		action = new Action("usar","barreta","item","espejo", "item");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejoSobreBarreta_SoloTienesBarretaEnInventario_NoSePuedeUsar() {
		expected = "No tienes ese objeto.";
		jugador.tomarItem("barreta");
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		assertEquals(expected,actuals);
	}
	
	@Test
	public void usarEspejoSobreBarreta_TieneAmbosEnInventario_NoSePuedeUsar() {
		expected = "No ha servido de nada.";
		jugador.tomarItem("barreta");
		jugador.tomarItem("espejo");
		action = new Action("usar","espejo","item","barreta", "item");
		actuals = jugador.switchearAction(action);
		//System.out.println(actuals);
		assertEquals(expected,actuals);
		
		//FALLA
		//Dice que no tenemos el objeto (que queremos usar, primero)
		//Tiene ambos... pero deberia decir que no puede usar espejo sobre barreta
	}	
	

}
