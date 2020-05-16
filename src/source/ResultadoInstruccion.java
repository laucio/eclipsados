package source;

public class ResultadoInstruccion {
	boolean esEndgame;
	String resultado;
	
	public ResultadoInstruccion(boolean esEndgame, String resultado) {
		super();
		this.esEndgame = esEndgame;
		this.resultado = resultado;
	}

	public boolean isEsEndgame() {
		return esEndgame;
	}

	public void setEsEndgame(boolean esEndgame) {
		this.esEndgame = esEndgame;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
	
}
