package milestone1.controllers;

import java.util.NoSuchElementException;

public class QuadreNotFoundException extends NoSuchElementException {
	
	private static final long serialVersionUID = 1L;
	
	public QuadreNotFoundException(int id) { super("Quadre " + id + " desconegut"); }
	
}
