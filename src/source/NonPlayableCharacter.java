package source;

import java.util.ArrayList;

public class NonPlayableCharacter implements Action{
	private String name;
	private String gender;
	private String number;
	private String description;
	private String talk;
	private ArrayList<Trigger> triggers = null;
	
	public NonPlayableCharacter(String name, String gender, String description, String message) {
		super();
		this.name = name;
		this.gender = gender;
		this.description = description;
		this.talk = message;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	public String toString() {
		return this.talk;
	}

	@Override
	public String usar() {
		return "Resultado";
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTalk() {
		return talk;
	}

	public void setTalk(String talk) {
		this.talk = talk;
	}

	public ArrayList<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(ArrayList<Trigger> triggers) {
		this.triggers = triggers;
	}
	
}
