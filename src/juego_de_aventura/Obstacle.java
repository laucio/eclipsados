package juego_de_aventura;

import java.util.ArrayList;

public abstract class Obstacle extends Thing{
	
	protected ArrayList<Trigger> triggers = null;
	
	public Obstacle(String name, String gender, String number, ArrayList<Trigger> triggers) {
		super(name, gender, number);
		this.triggers = triggers;
	}
	
	public Obstacle(String name, String gender, String number) {
		super(name, gender, number);
	}
	
	public Trigger findTrigger(Action action) {
		Trigger trigger = null;
		
		if(triggers != null && triggers.size()>0) {
			boolean found = false;
			int i = 0;
			while( i< triggers.size() && !found) {
				if(triggers.get(i).isShot(action)) {
					trigger = triggers.get(i);
					found = true;
				}
			i++;
			}
		}
	
	return trigger;
	}
}
