package juego_de_aventura;

import java.util.ArrayList;

public class Item extends Obstacle implements Shootable, HitPointsController {// implements Action{
	private ArrayList<String> actions = null;
	private ArrayList<String> effects_over = null;
	private ArrayList<String> targets = null;
	private int points;

	public Item(String name, String gender, String number, ArrayList<String> actions, ArrayList<String> effects_over,
			ArrayList<String> targets, ArrayList<Trigger> triggers, int points) {

		super(name, gender, number, triggers);
		this.actions = actions;
		this.effects_over = effects_over;
		this.targets = targets;
		this.points = points;
	}

	@Override
	public String getDescription() {
		return "No puedes pasar! Hay " + this.toString();
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

	public ArrayList<String> getTargets() {
		return targets;
	}

	public void setTargets(ArrayList<String> targets) {
		this.targets = targets;
	}

	public ArrayList<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(ArrayList<Trigger> triggers) {
		this.triggers = triggers;
	}

	public boolean hasEffectsOver(Action action) {
		boolean resultado = false;
		int i = 0;
		while (!resultado && i < this.getEffects_over().size()) {

			if (this.getEffects_over().get(i).equals(action.getEffect_over())) {
				resultado = true;
			}

			i++;
		}

		if (resultado) {
			resultado = false;
			i = 0;
			while (!resultado && i < this.getTargets().size()) {

				if (this.getTargets().get(i).equals(action.getTarget())) {
					resultado = true;
				}

				i++;
			}
		}

		return resultado;
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

	public boolean isNamed(String name) {
		return this.name.equals(name);
	}

	@Override
	public String shootTrigger(Action action, Adventure adventure, Player player) {
		String retorno = "Eso no ha servido de nada.";

		Trigger trigger = this.findTrigger(action);
		action.setAchieved(true);

		if (trigger != null) {
			int index = this.getTriggerIndex(trigger);
			player.setImageName("Items/" + this.getName() + "/Triggers/" + index);
			retorno = trigger.getOn_trigger();

			switch (trigger.getAfter_trigger()) {
			case "remove":
				if (!adventure.removeItemFromLocation(action, adventure, player.getCurrentLocation())) {
					player.removeItemFromInventory(adventure.getItem(action.getThing()));
				}
				break;
			case "remove obstacles":
				removeObstacle(action.getTarget(), player.getCurrentLocation());
				adventure.removeItemFromLocation(action, adventure, player.getCurrentLocation());
				break;
			case "restart":
				retorno += "\n" + player.restart(adventure.getLocations().get(0));
				break;
			case "alter points":
				retorno += "\n" + alterPlayerHitPoints(player, adventure);
				break;
			case "alter points & remove":
				retorno += "\n" + alterPlayerHitPoints(player, adventure);
				if (!adventure.removeItemFromLocation(action, adventure, player.getCurrentLocation())) {
					player.removeItemFromInventory(adventure.getItem(action.getThing()));
				}
				break;
			case "remove obstacles & remove":
				removeObstacle(action.getTarget(), player.getCurrentLocation());
				if (!adventure.removeItemFromLocation(action, adventure, player.getCurrentLocation())) {
					player.removeItemFromInventory(adventure.getItem(action.getThing()));
				}
				break;
			case "restart & remove":
				if (!adventure.removeItemFromLocation(action, adventure, player.getCurrentLocation())) {
					player.removeItemFromInventory(adventure.getItem(action.getThing()));
				}
				retorno += "\n" + player.restart(adventure.getLocations().get(0));
				break;
			default:
				break;
			}
		}

		return retorno;
	}

	@Override
	public void removeObstacle(String obstacle, Location location) {
		location.getConnectionFromObstacle(obstacle).setObstacles(null);

	}

	public boolean allowsAction(String action) {
		boolean found = false;
		int i = 0;

		while (i < actions.size() && !found) {
			if (action.equals(actions.get(i))) {
				found = true;
			}

			i++;
		}
		return found;
	}

	public boolean couldBeOpened() {
		return this.allowsAction("abrir");
	}

	@Override
	public String alterPlayerHitPoints(Player player, Adventure adventure) {
		String retorno = "";
		retorno = player.alterHitPoints(this.points);
		if (retorno.equals("restart")) {
			player.restart(adventure.getLocations().get(0));
			retorno = "Has perdido " + Math.abs(this.points)
					+ " puntos de vida.\nSe agotaron todos tus puntos de vida! Vuelves al principio con 50 HP.";
		}
		return retorno;
	}

}
