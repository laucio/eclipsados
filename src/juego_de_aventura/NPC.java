package juego_de_aventura;

import java.util.ArrayList;
import java.util.Random;
import juego_de_aventura.*;

public class NPC extends Obstacle implements Shootable, HitPointsController {

	private String description;

private ArrayList<String> talks;
	private int points;

	public NPC(String name, String gender, String number, String description, ArrayList<String> messages, int points) {
		super(name, gender, number);
		this.description = description;
		this.talks = messages;
		this.points = points;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTalk() {
	  Random rand = new Random();
      int int_random = rand.nextInt(this.talks.size()); 
	  return this.talks.get(int_random);
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

		if (this.isInLocation(player.getCurrentLocation())) {
			Trigger trigger = this.findTrigger(action);
			action.setAchieved(true);

			if (trigger != null) {
				retorno = trigger.getOn_trigger();
				// action.setAchieved(true);
				Item item;

				switch (trigger.getAfter_trigger()) {
				case "remove":
					removeObstacle(action.getTarget(), player.getCurrentLocation());
					break;
				case "remove item":
					item = adventure.getItem(action.getThing());
					player.removeItemFromInventory(item);
					break;
				case "restart":
					retorno += "\n" + player.restart(adventure.getLocations().get(0));
					break;
				case "alter points":
					retorno += "\n" + alterPlayerHitPoints(player, adventure, this.points);
					break;
				case "alter points & remove":
					retorno += "\n" + alterPlayerHitPoints(player, adventure, this.points);
					removeObstacle(action.getTarget(), player.getCurrentLocation());
					break;
				case "alter points & remove item":
					retorno += "\n" + alterPlayerHitPoints(player, adventure, this.points);
					item = adventure.getItem(action.getThing());
					player.removeItemFromInventory(item);
				default:
					break;
				}
			}
		}

		return retorno;
	}

	@Override
	public void removeObstacle(String obstacle, Location location) {
		location.removeNpc(obstacle);

	}

	private boolean isInLocation(Location location) {
		return location.containsNpc(this.getName());
	}

	@Override
	public String alterPlayerHitPoints(Player player, Adventure adventure, int hitPoints) {
		String retorno = "";
		retorno = player.alterHitPoints(hitPoints);
		if (retorno.equals("restart")) {
			player.restart(adventure.getLocations().get(0));
			retorno = "Se agotaron todos tus puntos de vida! vuelves al principio con 50 HP.";
		}
		return retorno;
	}
}
