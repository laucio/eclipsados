package juego_de_aventura;

import java.util.ArrayList;

import juego_de_aventura.*;

public class NPC extends Obstacle implements Shootable, HitPointsController{
	
	private String description;
	private String talk;
	private int ataque;

	public NPC(String name, String gender,String number, String description, String message, int ataque) {
		super(name, gender, number);
		this.description = description;
		this.talk = message;
		this.ataque = ataque;
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
	

	public boolean isNamed(String npcName) {
		return this.name.equals(npcName);
	}

	@Override
	public String shootTrigger(Action action, Adventure adventure, Player player) {
		String retorno = "Eso no ha servido de nada.";
		
		if(this.isInLocation(player.getCurrentLocation())) {
		Trigger trigger = this.findTrigger(action);
		action.setAchieved(true);
		
		if(trigger != null) {
			retorno = trigger.getOn_trigger();
			//action.setAchieved(true);
			
			switch (trigger.getAfter_trigger()) {
			case "remove":
				removeObstacle(action.getTarget(), player.getCurrentLocation());
				break;
			case "remove item":
				Item item = adventure.getItem(action.getThing());
				player.removeItemFromInventory(item);
				break;
			case "restart":
				player.setCurrentLocation(adventure.getLocations().get(0));
				break;
			default:
				break;
			}
		}
		}

		return retorno;
	}
	
	@Override
	public void removeObstacle(String obstacle,Location location) {
		location.removeNpc(obstacle);
		
	}

	private boolean isInLocation(Location location) {
		return location.containsNpc(this.getName());
	}

	@Override
	public String alterPlayerHitPoints(Player player, Adventure adventure, int hitPoints) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
