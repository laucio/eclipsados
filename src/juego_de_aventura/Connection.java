package juego_de_aventura;

public class Connection {
	
	private String direction;
	private String location;
	private String obstacles = null;

	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getObstacles() {
		return obstacles;
	}
	public void setObstacles(String obstacles) {
		this.obstacles = obstacles;
	}
	
	public boolean hasObstacles() {
		return (this.obstacles == null || this.obstacles.isEmpty()) ? false : true;
	}
	
	public boolean removeNPC(String personaje) {
		boolean removed = false;
		if(this.obstacles!=null && (removed = this.obstacles.equals(personaje))) {
			this.setObstacles("");
		}
		return removed;
	}
	
	public boolean hasObstacle(String itemName) {
		return obstacles!=null && obstacles.equals(itemName);
	}
	
}
