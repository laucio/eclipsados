package traductortest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TraductorUsarTest {
	
	private final static String[] VERBO_USAR = { " usar ", " usa ", " utilizar ", " utiliza " };
	
	@Test
	public void test() {
		String cadena = VERBO_USAR[0];
		System.out.println(cadena.indexOf("sa"));
	}

}
