package source;

import java.util.ArrayList;
import java.util.Set;

public class Location {
	private String name;
	private String gender;
	private String number;
	private String description;
	private ArrayList<Place> places;
	private ArrayList<String> NPCS;
	private ArrayList<Connection> connections;
	
	public Location(String name, String gender, String number, String description) {
		super();
		this.name = name;
		this.gender = gender;
		this.number = number;
		this.description = description;
		this.setPlaces(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public ArrayList<String> getNPCS() {
		return NPCS;
	}

	public void setNPCS(ArrayList<String> nPCS) {
		NPCS = nPCS;
	}


	
	
}
