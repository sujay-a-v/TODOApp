package com.bridgelabz.control;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value= NullPointerException.class)
	public String handlingNullPointerException(Exception exception)
	{
		System.out.println("Null pointer exception occured "+ exception);
		return "NullPointerException";
	}
	/*@ExceptionHandler(value= IOException.class)
	public String handlingIOException(Exception exception)
	{
		System.out.println("IO exception occured "+ exception);
		return "IOException"; //create a IOException.jsp file
	}*/
	@ExceptionHandler(value= Exception.class)
	public String handlingGenericException(Exception exception)
	{
		System.out.println("Unknown exception occured "+ exception);
		return "NullPointerException";//create a GenericException.jsp file   (Customized error page)
	}

}
