package juego_de_aventura;

import juego_de_aventura.*;

public class Trigger {
	private String type;
	private String thing;
	private String on_trigger;
	private String after_trigger;
	
	
	public Trigger(String type, String thing, String on_trigger, String after_trigger) {
		this.type = type;
		this.thing = thing;
		this.on_trigger = on_trigger;
		this.after_trigger = after_trigger;
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThing() {
		return thing;
	}
	public void setThing(String thing) {
		this.thing = thing;
	}
	public String getOn_trigger() {
		return on_trigger;
	}
	public void setOn_trigger(String on_trigger) {
		this.on_trigger = on_trigger;
	}
	public String getAfter_trigger() {
		return after_trigger;
	}
	public void setAfter_trigger(String after_trigger) {
		this.after_trigger = after_trigger;
	}
	
	public boolean isShot(Action action) {
		return this.type.equals(action.getCondition()) 
				&& this.thing.equals(action.getThing());
	}

}
