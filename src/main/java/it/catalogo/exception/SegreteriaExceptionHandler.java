package it.catalogo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class SegreteriaExceptionHandler extends ResponseEntityExceptionHandler{
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(SegreteriaException.class)
	protected ResponseEntity<Object> handleCategoriaException(SegreteriaException ex){
		ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return buildResponseEntity(apiError);
	}

}
