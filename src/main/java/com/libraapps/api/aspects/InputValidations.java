package com.libraapps.api.aspects;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.libraapps.api.model.StudentModel;
import com.libraapps.api.model.StudentValidationError;

@Aspect
@Component
public class InputValidations {

	private static final Logger logger = Logger.getLogger (InputValidations.class);
	
	@Around ("@annotation(com.libraapps.api.aspects.ValidateModel)")
	public Object validateModel(ProceedingJoinPoint proceedingJoinPoint) {
		logger.info("Validando por Modelo " + proceedingJoinPoint.getArgs().toString());
		Object resp = null;
		try {
			StudentModel model = (StudentModel)(proceedingJoinPoint.getArgs()[0]);
			logger.info(model.toString());
			List<StudentValidationError> errores = validateModel(model);
			if ( errores != null) {
				resp = new ResponseEntity (errores , HttpStatus.NOT_ACCEPTABLE);
			}else {
				resp=proceedingJoinPoint.proceed();
				logger.info("termino");
			}
		}catch (Throwable e) {
			resp = new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		return resp;
	}
	
	
	@Around ("@annotation(com.libraapps.api.aspects.ValidateId)")
	public Object validateId(ProceedingJoinPoint proceedingJoinPoint)  {
		logger.info("Validando por id " + proceedingJoinPoint.getArgs().toString());
		Object resp = null;
		try {
			String id = (String)(proceedingJoinPoint.getArgs()[0]);
			logger.info(id);
			List<StudentValidationError> result = new ArrayList<StudentValidationError>();
			if (isNullOrEmpty(id)) {
				result.add(new StudentValidationError("1", "EL DNI ES OBLIGATORIO"));
			}else if (!validDni(id)) {
				result.add(new StudentValidationError("1.1", "EL DNI ES INVALIDO"));
			}
			
			if (result.isEmpty()) {
				resp=proceedingJoinPoint.proceed();
				logger.info("termino");
			}
			else {
				resp = new ResponseEntity (result, HttpStatus.NOT_ACCEPTABLE);
			}
		}catch(Exception ex) {
		  logger.error(ex);	
		  resp = new ResponseEntity(HttpStatus.BAD_REQUEST);
		}		
		catch (Throwable e) {
			resp = new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
	
	private List<StudentValidationError> validateModel(StudentModel m) {
		List<StudentValidationError> resp = new ArrayList<StudentValidationError>();
		if (isNullOrEmpty(m.getDni())) {
			resp.add(new StudentValidationError("1", "EL DNI ES OBLIGATORIO"));
		}else if (!validDni(m.getDni())) {
			resp.add(new StudentValidationError("1.1", "EL DNI ES INVALIDO"));
		}
		if (isNullOrEmpty(m.getEmail())) {
			resp.add(new StudentValidationError("2", "EL EMAIL ES OBLIGATORIO"));
		}else if (!validEmail(m.getEmail())) {
			resp.add(new StudentValidationError("2.1", "EL EMAIL ES INVALIDO"));
		}
		if (isNullOrEmpty(m.getName())) {
			resp.add(new StudentValidationError("3", "EL NOMBRE ES OBLIGATORIO"));
		}
		if (isNullOrEmpty(m.getLastName())) {
			resp.add(new StudentValidationError("4", "EL APELLIDO ES OBLIGATORIO"));
		}
		if (isNullOrEmpty(m.getMobile())) {
			resp.add(new StudentValidationError("5", "EL MOVIL ES OBLIGATORIO"));
		}else if (!validMobil(m.getMobile())) {
			resp.add(new StudentValidationError("5.1", "EL MOVIL ES INVALIDO"));
		}		
		
		if (resp.isEmpty()) resp = null;
		
		return resp;
	}

	
	private boolean validMobil(String mobile) {
		String s = mobile.replace(" ", "");
		s = s.replace("+", "");
		s = s.replace("-", "");		
		try {
			Long.parseLong(s);
		}catch (NumberFormatException nfe) {
			return false;
		}
		return true;
		
	}

	private boolean validEmail(String email) {
		
		return email.contains("@");
		
	}

	private boolean validDni(String dni) {
		try {
			Long.parseLong(dni);
		}catch (NumberFormatException nfe) {
			return false;
		}
		return true;		
	}

	private boolean isNullOrEmpty(String s) {
		return (s == null || s.isEmpty());
	}
}
