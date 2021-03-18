package com.myProject.rest.webservices.exception;

import java.util.Date;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.myProject.rest.webservices.user.userNotFoundException;

@ControllerAdvice
@RestController
/*
 * ResponseEntityExceptionHandler is a default class part of spring package to
 * handle all the exceptions
 */
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	// Override the default behaviour of handleallexception method
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExcpetions(Exception ex, WebRequest wbsrequest) {

		ExceptionResponse expRes = new ExceptionResponse(new Date(), ex.getMessage(), wbsrequest.getDescription(false));

		return new ResponseEntity(expRes, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(userNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundExcpetions(userNotFoundException ex, WebRequest wbsrequest) {

		ExceptionResponse expRes = new ExceptionResponse(new Date(), ex.getMessage(), wbsrequest.getDescription(false));

		return new ResponseEntity(expRes, HttpStatus.NOT_FOUND);

	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse expRes = new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());

		return new ResponseEntity(expRes, HttpStatus.BAD_REQUEST);
	}
}
