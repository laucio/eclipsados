package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;

public class AventuraZork {
	private Setting settings;
	private ArrayList<Location> locations = null;
	private ArrayList<NonPlayableCharacter> npcs = null;
	private ArrayList<Item> items = null;
	private ArrayList<String> inventory =null;
	private ArrayList<Endgame> endgames = null;
	
	public AventuraZork() {
	
		
	}
		
	
	public Setting getSettings() {
		return settings;
	}
	public void setSettings(Setting settings) {
		this.settings = settings;
	}
	public ArrayList<Location> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	public ArrayList<NonPlayableCharacter> getNpcs() {
		return npcs;
	}
	public void setNpcs(ArrayList<NonPlayableCharacter> npcs) {
		this.npcs = npcs;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	public ArrayList<String> getInventory() {
		return inventory;
	}
	public void setInventory(ArrayList<String> inventory) {
		this.inventory = inventory;
	}
	public ArrayList<Endgame> getEndgames() {
		return endgames;
	}
	public void setEndgames(ArrayList<Endgame> endgames) {
		this.endgames = endgames;
	}
}
