package juego_de_aventura;

public class Action {

	private String action;	//usar
	private String thing;	//npcs
	private String condition; //direction,location,item,etc..
	private String target;
	private String effect_over;
	private boolean achieved;
	

	public Action() {
	this.action = "";
	this.thing = "";
	this.condition = "";
	
	this.target = null;
	this.effect_over = null;
	this.achieved = false;
	}
	
	public Action(String action, String thing, String condition) {
		this.action = action;
		this.thing = thing;
		this.condition = condition;
		this.target = null;
		this.effect_over = null;
		this.achieved = false;
	}
		
	public Action(String action, String thing, String condition,String target, String effect_over) {
		this.action = action;
		this.thing = thing;
		this.condition = condition;
		this.target = target;
		this.effect_over = effect_over;
		this.achieved = false;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	
	public boolean IsConditionADirection() {
		return this.getCondition().equals("direction");
	}
	
	public boolean hasSameDirection(String direction) {
		return this.thing.equals(direction);
	}
	
	public boolean isConditionALocation() {
		return this.getCondition().equals("location");
	}
	
	public boolean hasSameLocation(String location) {
		return this.thing.equals(location);
	}

	public boolean IsSelfEffect() {
		return this.target.equals("self");
	}

}
