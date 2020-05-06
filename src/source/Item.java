package source;
import java.util.ArrayList;

public class Item {// implements Action{
	private String name;
	private String gender;
	private String number;
	private ArrayList<String> actions = null;
	private ArrayList<String> effects_over = null;

	public Item(String name,String gender, String number,ArrayList<String> actions, ArrayList<String> effects_over ) {
		this.name = name;
		this.gender = gender;
		this.number =number;
		this.actions = actions;
		this.effects_over = effects_over;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

/*	@Override
	public String usar() {
		return "Resultado";
	}*/

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

	public ArrayList<String> getEffects_over() {
		return effects_over;
	}

	public void setEffects_over(ArrayList<String> effects_over) {
		this.effects_over = effects_over;
	}

	public ArrayList<String> getActions() {
		return actions;
	}

	public void setActions(ArrayList<String> actions) {
		this.actions = actions;
	}

	@Override
	public String toString() {
		String cadena ="unas ";
		if(this.gender.equals("male") && this.number.equals("singular")) {
						cadena="un ";
		}

		if(this.gender.equals("male") && this.number.equals("plural")) {
				cadena="unos ";
		}
		
		if(this.gender.equals("female") && this.number.equals("singular")) {
		cadena = "una ";
		}
		
		return cadena + this.getName();
			
	}
	
	public String toString(String item) {
		return "Hola";
	}
	
}
