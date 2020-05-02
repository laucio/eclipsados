package source;
import source.Action;

public class Item implements Action{
	private String name;

	public Item(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String usar() {
		return "Resultado";
	}
	
	
	
}
