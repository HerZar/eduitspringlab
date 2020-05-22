package com.libraapps.api.aspects;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerMethodCalls {

	private static final Logger logger = Logger.getLogger (LoggerMethodCalls.class);
	
	@Before ("execution(* com.libraapps.api.controller.*.*(..))")
	public void logginmethod( JoinPoint joinpoint ) {
		
		logger.info("Se ejecuto el metodo: " + joinpoint.toShortString());
		
	}
}
