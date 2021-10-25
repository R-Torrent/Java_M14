package milestone1.controllers;

public class CapacitatUltrapassadaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CapacitatUltrapassadaException(int id) {
		super("La botiga " + id + " és plena; operació d'afegir abortada");
	}
	
}
