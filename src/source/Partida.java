package source;

import java.io.IOException;

public class Partida {
	private AventuraZork aventura = null;
	private String currentLocation = null;
	
	public Partida(String aventuraPath) throws IOException {
		this.aventura = CargaAventura.cargarArchivo(aventuraPath);
		this.currentLocation = aventura.getLocations().get(0).getName();
	}

	public AventuraZork getAventura() {
		return aventura;
	}

	public void setAventura(AventuraZork aventura) {
		this.aventura = aventura;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	
	
}
