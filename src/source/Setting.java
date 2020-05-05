package source;

public class Setting {
	private String welcome;
	private String character;

	public Setting(String welcome, String character) {
		this.welcome = welcome;
		this.character = character;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String toString() {
		return getWelcome() +
		"\nPersonaje: " + getCharacter();
	}
}
