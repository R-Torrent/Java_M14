package milestone1.controllers;

import java.util.NoSuchElementException;

public class BotigaNotFoundException extends NoSuchElementException {
	
	private static final long serialVersionUID = 1L;
	
	public BotigaNotFoundException(int id) { super("Botiga " + id + " desconeguda"); }
	
}
