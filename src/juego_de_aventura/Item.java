package juego_de_aventura;

import java.util.ArrayList;

public class Item extends Obstacle implements Shootable{// implements Action{
	private ArrayList<String> actions = null;
	private ArrayList<String> effects_over = null;
	private ArrayList<String> targets = null;

	public Item(String name, String gender, String number, ArrayList<String> actions, ArrayList<String> effects_over,
			ArrayList<String> targets, ArrayList<Trigger> triggers) {
		
		super(name, gender, number, triggers);
		this.actions = actions;
		this.effects_over = effects_over;
		this.targets = targets;
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
		
		if(trigger != null) {
			retorno = trigger.getOn_trigger();
			action.setAchieved(true);
			
			switch (trigger.getAfter_trigger()) {
			case "remove":
				if(!adventure.removeItemFromTrigger(action, adventure, player.getCurrentLocation())) {
					player.removeItemFromInventory(adventure.getItem(action.getThing()));
				}
				break;
			case "remove obstacles":
				removeObstacle(action.getTarget(), player.getCurrentLocation());
				adventure.removeItemFromTrigger(action, adventure, player.getCurrentLocation());
				break;
			case "restart":
				player.setCurrentLocation(adventure.getLocations().get(0));
			default:
				break;
			// case "invalidar":
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
		int i=0;
		
		while(i<actions.size() && !found) {
			if(action.equals(actions.get(i))) {
				found = true;
			}
			
		i++;
		}
	return found;	
	}
	
	
}
