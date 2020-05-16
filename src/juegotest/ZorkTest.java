package juegotest;

import source.Player;
import source.Place;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import source.CargaAventura;
import source.Item;
import source.Location;
import source.NonPlayableCharacter;
import source.Action;
import source.AventuraZork;

public class ZorkTest {

	/*
	 * @Test public void getCharacterName() throws IOException {
	 * 
	 * String path = "Juego.json"; AventuraZork juego =
	 * CargaAventura.cargarArchivo(path);
	 * 
	 * assertEquals("Guybrush Threepwood", juego.getSettings().getCharacter());
	 * 
	 * }
	 * 
	 * @Test public void getWelcome() throws IOException {
	 * 
	 * String path = "Juego.json"; AventuraZork juego =
	 * CargaAventura.cargarArchivo(path); assertEquals(
	 * "Te encuentras en un muelle. Es de noche pero la luna ilumina todo el lugar. En el suelo hay algunos objetos, y sientes muchas ganas de ir hacia una taberna."
	 * , juego.getSettings().getWelcome()); }
	 * 
	 * @Test public void getLocationDescription() throws IOException {
	 * 
	 * String path = "Juego.json"; AventuraZork juego =
	 * CargaAventura.cargarArchivo(path); assertEquals("Estas en un muelle",
	 * juego.getLocations().get(0).getDescription()); }
	 * 
	 * @Test public void getLocationDescriptionNext() throws IOException { String
	 * path = "Juego.json"; AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * assertEquals("Estas en una sucia taberna",
	 * juego.getLocations().get(1).getDescription()); }
	 * 
	 * @Test public void mirarAlrededor() throws IOException { String path =
	 * "Juego.json"; AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * Assert.assertEquals(
	 * "Estas en un muelle. En el suelo hay: una barreta , un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna."
	 * , juego.verAlrededor("muelle"));
	 * 
	 * }
	 * 
	 * @Test public void mirarAlrededorSinItems() throws IOException { String path =
	 * "Juego.json"; AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * juego.tomarItem("muelle", "suelo", "barreta"); juego.tomarItem("muelle",
	 * "suelo", "rociador con cerveza de raiz"); juego.tomarItem("muelle", "suelo",
	 * "espejo"); Assert.
	 * assertEquals("Estas en un muelle. Hay un pirata fantasma. Al sur hay una taberna."
	 * , juego.verAlrededor("muelle"));
	 * 
	 * }
	 * 
	 * @Test public void darBienvenida() throws IOException { String path =
	 * "Juego.json"; Player partida = new Player(path); String cadenaEsperada =
	 * "Te encuentras en un muelle. Es de noche pero la luna ilumina todo el lugar. En el suelo hay algunos objetos, y sientes muchas ganas de ir hacia una taberna."
	 * ; Assert.assertEquals(cadenaEsperada, partida.darBienvenida());
	 * 
	 * }
	 * 
	 * @Test public void TraducirMoverseDeLocacion() throws IOException { Action obj
	 * = new Action(); String path = "Juego.json"; Player partida = new
	 * Player(path); if (!partida.traducir("ir al sur", obj)) {
	 * System.out.println(obj.getMessage()); } else {
	 * System.out.println(obj.getAction() + " " + obj.getThing() + " " +
	 * obj.getCondition()); }
	 * 
	 * }
	 * 
	 * @Test public void TraducirTomarObjeto() throws IOException { Action obj = new
	 * Action(); String path = "Juego.json"; Player partida = new Player(path); if
	 * (!partida.traducir("toma la barreta", obj)) {
	 * System.out.println(obj.getMessage()); } else {
	 * System.out.println(obj.getAction() + " " + obj.getThing() + " " +
	 * obj.getCondition()); }
	 * 
	 * }
	 */
	/*
	 * @Test public void MoverseConObstaculo() throws IOException { Action obj = new
	 * Action(); String path = "Juego.json"; Player partida = new Player(path);
	 * 
	 * if (!partida.traducir("ir al sur", obj)) {
	 * System.out.println(obj.getMessage());
	 * 
	 * } else {
	 * 
	 * System.out.println(obj.getAction() + " " + obj.getThing() + " " +
	 * obj.getCondition()); }
	 * 
	 * System.out.println(); if(partida.goTo(obj)) {
	 * 
	 * System.out.println(partida.getAventura().verAlrededor(partida.
	 * getCurrentLocation())); } else {
	 * 
	 * System.out.println(obj.getMessage());
	 * 
	 * }
	 * 
	 * }
	 */
/*
	@Test
	public void MoverseSinObstaculo() throws IOException {
		Action obj = new Action();
		String path = "Juego.json";
		Player partida = new Player(path);
		partida.getAventura().getLocations().get(0).getConnections().get(0).setObstacles(null);
		if (!partida.traducir("ir al sur", obj)) {
			System.out.println(obj.getMessage());

		} else {

			System.out.println(obj.getAction() + " " + obj.getThing() + " " + obj.getCondition());
		}

		System.out.println();
		if (!partida.goTo(obj)) {

			System.out.println(obj.getMessage());
		}

	}
	*/

	/*
	 * @Test public void activarTrigger_PirataFantasma() throws IOException { String
	 * path = "Juego.json"; AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * assertEquals("remove",juego.getNpcs().get(0).getTriggers().get(0).
	 * activarTrigger("rociador con cerveza de raiz")); }
	 */

	/*
	 * @Test public void getNPCSTalk1() throws IOException {
	 * System.out.println("getNPCSTalk1"); String path = "Juego.json"; Partida
	 * partida = new Partida(path);
	 * 
	 * }
	 */

	/*
	 * @Test public void verInventario1() throws IOException{
	 * System.out.println("verInventario1"); String path = "Juego.json";
	 * AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * juego.agregarItemInventario("barreta");
	 * juego.agregarItemInventario("rociador con cerveza de raiz");
	 * juego.agregarItemInventario("espejo"); Assert.assertEquals(
	 * "En tu inventario hay una barreta, un rociador con cerveza de raiz, y un espejo."
	 * , juego.verInventario()); //System.out.println(juego.verInventario()); }
	 */

	/*
	 * @Test public void pruebaQuitarItemDePlace() throws IOException{
	 * 
	 * System.out.println("test quitar item de place"); String path = "Juego.json";
	 * AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * //System.out.println(juego.verAlrededor("muelle"));
	 * juego.getLocations().get(0).getPlaces().get(0).quitarItemDeArray("barreta");
	 * //System.out.println(juego.verAlrededor("muelle")); Assert.assertEquals(
	 * "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna."
	 * , juego.verAlrededor("muelle"));
	 * 
	 * }
	 */

	/*
	 * @Test public void tomaItemYloAgregaAlInventario() throws IOException { String
	 * path = "Juego.json"; AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * juego.agregarItemInventario("rociador con cerveza de raiz");
	 * juego.agregarItemInventario("espejo");
	 * //System.out.println(juego.verInventario()); juego.tomarItem("muelle",
	 * "suelo", "barreta"); // System.out.println(juego.verInventario());
	 * //System.out.println(juego.verAlrededor("muelle")); Assert.assertEquals(
	 * "Estas en un muelle. En el suelo hay: un rociador con cerveza de raiz y un espejo. Hay un pirata fantasma. Al sur hay una taberna."
	 * , juego.verAlrededor("muelle")); Assert.
	 * assertEquals("En tu inventario hay un rociador con cerveza de raiz, un espejo, y una barreta."
	 * , juego.verInventario());
	 * 
	 * 
	 * }
	 */

	/*
	 * @Test public void seFijaSinoEncuentraEseItem() throws IOException { 
	 * String path = "Juego.json"; AventuraZork juego = CargaAventura.cargarArchivo(path);
	 * juego.tomarItem("muelle", "suelo", "escopeta"); }
	 */

/*	@Test public void remueveNPCS() throws IOException { 
		String path = "Juego.json"; 
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		juego.removeNpc("pirata fantasma", juego.getLocationIndex("muelle"));
		System.out.println(juego.verAlrededor("muelle"));
		 }
	
	*/
/*	
	@Test public void agregaNPCSyRemueve()throws IOException { 
		String path = "Juego.json"; 
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		int indexTaberna = juego.getLocationIndex("taberna");
		
		ArrayList<String> nue =new ArrayList<String>(); 
		nue.add("Chris");
		NonPlayableCharacter npc2 = new NonPlayableCharacter("Chris", "male","singular", "Soy chris y estudio informatica", "dame tu cerveza");
		juego.getNpcs().add(npc2);
		juego.getLocations().get(indexTaberna).getConnections().get(0).setObstacles("Chris");
		juego.getLocations().get(indexTaberna).setNPCS(nue);
		//juego.getLocations().get(indexTaberna).getNPCS().add("Chris");
		juego.removeNpc("pirata fantasma", juego.getLocationIndex("muelle"));
		
		System.out.println(juego.verAlrededor("taberna"));
		
	}
	*/
	
	@Test public void agregaNPCSyRemueveMuelle()throws IOException { 
		String path = "Juego.json"; 
		AventuraZork juego = CargaAventura.cargarArchivo(path);
		int indexTaberna = juego.getLocationIndex("muelle");
		
		NonPlayableCharacter npc2 = new NonPlayableCharacter("Chris", "male","singular", "Soy chris y estudio informatica", "dame tu cerveza");
		juego.getNpcs().add(npc2);
		//juego.getLocations().get(indexTaberna).getConnections().get(0).setObstacles("Chris");
		juego.getLocations().get(indexTaberna).getNPCS().add("Chris");
		//juego.getLocations().get(indexTaberna).getNPCS().add("Chris");
		//juego.removeNpc("pirata fantasma", juego.getLocationIndex("muelle"));
		
		System.out.println(juego.verAlrededor("muelle"));
		
	}

}
