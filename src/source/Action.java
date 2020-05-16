package source;

public class Action {

	String action;	//usar
	String thing;	//npcs
	String condition; //direction,location,item,etc..
	String target;
	String message;
	String effect_over;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Action(String action, String thing, String condition) {
		this.action = action;
		this.thing = thing;
		this.condition = condition;
		this.message = null;
		this.target = null;
		this.effect_over = null;
	}
	
	public Action(String action, String thing, String condition,String target, String effect_over) {
		this.action = action;
		this.thing = thing;
		this.condition = condition;
		this.message = null;
		this.target = target;
		this.effect_over = effect_over;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Action() {
	this.action = "";
	this.thing = "";
	this.condition = "";
	}
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getThing() {
		return thing;
	}
	public void setThing(String thing) {
		this.thing = thing;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getEffect_over() {
		return effect_over;
	}

	public void setEffect_over(String effect_over) {
		this.effect_over = effect_over;
	}
	
	

}
