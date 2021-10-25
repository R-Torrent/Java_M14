package milestone1.controllers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AvisosExcepcions {
	
	@ResponseBody
	@ExceptionHandler({BotigaNotFoundException.class, QuadreNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String ElementNoTrobatHandler(NoSuchElementException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(CapacitatUltrapassadaException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	String CapacitatUltrapassadaHandler(CapacitatUltrapassadaException ex) {
		return ex.getMessage();
	}
	
}
