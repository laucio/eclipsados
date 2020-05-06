package source;

import java.util.ArrayList;
import java.util.Set;

public class Place {
	private String name;
	private String gender;
	private String number;
	private ArrayList<String> items;
	
	public Place(String name, String gender, String number, Set<Item> items) {
		super();
		this.setName(name);
		this.setGender(gender);
		this.setNumber(number);
		this.setItems(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	
	
	@Override
	public String toString() {
		String cadena ="las ";
		if(this.gender.equals("male") && this.number.equals("singular")) {
						cadena="el ";
		}

		if(this.gender.equals("male") && this.number.equals("plural")) {
				cadena="los ";
		}
		
		if(this.gender.equals("female") && this.number.equals("singular")) {
		cadena = "la ";
		}
		
		return cadena + this.getName();
			
	}
	
	
}
