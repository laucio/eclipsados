import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import source.Location;
import source.Player;

public class PlayerTest {

	@Test
	public void firstLocationShouldBeMuelle() {
		Location location = new Location("muelle","male,","singular","Estas en un muelle");
		Player player = new Player(location);
		
		assertEquals("muelle",player.getCurrentLocation().getName());
		
	}
	@Test
	public void goToShouldReturnFalseWhenThereAreNotConnections() {
		Location location = new Location("muelle","male,","singular","Estas en un muelle");
		Player player = new Player(location);
		HashMap<String,Location> locations = new HashMap<String,Location>();
		
		boolean couldGoToLocation = player.goTo("norte", locations);
		
		assertFalse(couldGoToLocation);
		
	}

}
