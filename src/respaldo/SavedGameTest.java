package respaldo;
/*
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;

import source.*;

public class SavedGameTest {


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
	}*/
	
	/*
	@Test
    public void guardarPartida() throws JsonIOException, IOException {
		 ArrayList<String> targets = new ArrayList<String>();
		 ArrayList<Trigger> triggers = new ArrayList<Trigger>();
		targets.add("espejo");
		triggers.add(new Trigger("item","barreta","El espejo se ha roto","remove"));
		jugador.getAventura().getItems().get(0).setTargets(targets);
		jugador.getAventura().getItems().get(2).setTriggers(triggers);
		
		
        String filePath = "partidaGuardada.json";
        expected = "No encuentro ese objeto."; 
       
        SavedGame.saveStatus(jugador.getAventura(), filePath);
       
    }*/
	
	/*
	@Test
	public void usarBarretaSobreEspejo() throws JsonIOException, IOException {
		path = "partidaGuardada.json";
		juego = LoadAdventure.cargarArchivo(path);
		jugador = new Player(juego);
		action = new Action("usar","barreta", "item","espejo","item");//usa una barreta que es un 
		                                                              //item sobre un espejo que es un item
		expected = "El espejo se ha roto";
		actuals = jugador.switchearAction(action);
		assertEquals(expected, actuals);
	}
	*/
	
	/* @Test//No es una prueba... se uso para modificar json
	 public void  NuevoDispTrigger() throws JsonIOException, IOException {
       
		 //a barreta le agregamos targets
		ArrayList<String> targets = new ArrayList<String>();
		targets.add("espejo");
		targets.add("rociador con cerveza de raiz");
		jugador.getAventura().getItems().get(0).setTargets(targets);
		
		//a espejo le asignamos un trigger ejecutado por barreta
		ArrayList<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item","barreta","El espejo se ha roto","remove"));
		jugador.getAventura().getItems().get(2).setTriggers(triggers);
		
		//asigna como target self a espejo, para que pueda disparar un trigger sobre si mismo
		ArrayList<String> targets1 = new ArrayList<String>();
		targets1.add("self");
		jugador.getAventura().getItems().get(2).setTargets(targets1);
		
		
		//a espejo le asignamos un trigger ejecutado por si mismo, self
		ArrayList<Trigger> triggers2 = new ArrayList<Trigger>();
		triggers2.add(new Trigger("item","barreta","El espejo se ha roto","remove"));//si se pone remove, lo quita de invetario
		triggers2.add(new Trigger("item","espejo","Te ves horrible!","default"));
		jugador.getAventura().getItems().get(2).setTriggers(triggers2);
		
		
		//la barreta solo tendra efecto sobre npcs o sobre otro item, no self
		ArrayList<String> paraBarreta = new ArrayList<String>();
		paraBarreta.add("item");
		jugador.getAventura().getItems().get(0).setEffects_over(paraBarreta);
		
		/////////////////////////////////////////////////////////////
		//no modificar nada antes de este comentario
		
		
		//el rociador solo tendra efecto sobre npcs o self
		ArrayList<String> paraRociador = new ArrayList<String>();
		paraRociador.add("npcs");
		paraRociador.add("self");
		jugador.getAventura().getItems().get(1).setEffects_over(paraRociador);
		
		
		//el espejo solo tendra efecto sobre si mismo ,self
		ArrayList<String> paraEspejo = new ArrayList<String>();
		paraEspejo.add("self");
		jugador.getAventura().getItems().get(2).setEffects_over(paraEspejo);
		
		//los unicos targets del rociador son el pirata y self
		ArrayList<String> targetsR = new ArrayList<String>();
		targetsR.add("self");
		targetsR.add("pirata fantasma");
		jugador.getAventura().getItems().get(1).setTargets(targetsR);
		
		//los dos triggers de rociador: uno q dispara la barreta, y otro que dispara el mismo
		ArrayList<Trigger> triggersR = new ArrayList<Trigger>();
		triggersR.add(new Trigger("item","barreta","Se ha abollado la lata","default"));//con remove, lo quita de inventario
		triggersR.add(new Trigger("item","rociador con cerveza de raiz","Que delicia de cerveza!","default"));
		jugador.getAventura().getItems().get(1).setTriggers(triggersR);
		
		
		
		//graba cambios en json
       String filePath = "Juego.json";
       expected = "No encuentro ese objeto."; 
      
       SavedGame.saveStatus(jugador.getAventura(), filePath);
      
   }

	
}
*/
