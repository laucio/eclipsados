package source;
import java.util.HashMap;

public class Player {

	private Location currentLocation;
	
	public Player(Location initialLocation) {
		this.currentLocation = initialLocation;
	}
	
	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public boolean goTo(String direction, HashMap<String, Location> locations) {
		
		for (Connection con : this.currentLocation.getConnections()) {
			if (direction.equalsIgnoreCase(con.getDirection())) {
				//TODO: search location in a DB, in order not to pass it as a function parameter.
				this.currentLocation = locations.get(direction);
				return true;
			}
		}
		
		return false;
	}
}
