package juego_de_aventura;

import java.util.ArrayList;

import juego_de_aventura.*;

public class NPC extends Obstacle implements Shootable{
	
	private String description;
	private String talk;

	public NPC(String name, String gender,String number, String description, String message) {
		super(name, gender, number);
		this.description = description;
		this.talk = message;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		String cadena = "unas ";
		if (this.gender.equals("male") && this.number.equals("singular")) {
			cadena = "un ";
		}

		if (this.gender.equals("male") && this.number.equals("plural")) {
			cadena = "unos ";
		}

		if (this.gender.equals("female") && this.number.equals("singular")) {
			cadena = "una ";
		}

		return cadena + this.getName();

	}
	
	public String hablar() {
		return this.getTalk();
	}

	public boolean isNamed(String npcName) {
		return this.name.equals(npcName);
	}

	@Override
	public String shootTrigger(Action action, Adventure adventure, Location location) {
		String retorno = "Eso no ha servido de nada.";
		
		if(this.isInLocation(location)) {
		Trigger trigger = this.findTrigger(action);
		
		
		if(trigger != null) {
			retorno = trigger.getOn_trigger();
			action.setAchieved(true);
			
			switch (trigger.getAfter_trigger()) {
			case "remove":
				adventure.removeNpc(action.getTarget(), location);
				break;
			default:
				break;
			// case "invalidar":
			}
		}
		}

		return retorno;
	}

	private boolean isInLocation(Location location) {
		return location.containsNpc(this.getName());
	}
	
}
