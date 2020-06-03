package juego_de_aventura;

public abstract class Thing {
	protected String name;
	protected String gender;
	protected String number;
	
	public Thing(String name, String gender, String number) {
		super();
		this.name = name;
		this.gender = gender;
		this.number = number;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public abstract String getDescription();
}
