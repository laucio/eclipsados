package source;

public class NonPlayableCharacter {
	private String name;
	private String gender;
	private String description;
	private String message;
	
	public NonPlayableCharacter(String name, String gender, String description, String message) {
		super();
		this.name = name;
		this.gender = gender;
		this.description = description;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String hablar() {
		this.setMessage("hola");
		return this.toString();
	}
	
	public String toString() {
		return this.message;
	}
	
}
