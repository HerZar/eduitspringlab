package com.libraapps.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.libraapps.api.model.StudentModel;
import com.libraapps.api.model.StudentValidationError;
import com.libraapps.api.service.IStudentService;

@RestController
@RequestMapping("students")
public class StudentController {
	//agregar persistencia en base de datos mysql
	@Autowired        //con esto le decimos a spring que injecte un objeto de este tipo
	private IStudentService<StudentModel> service;
	
	private static final Logger logger = Logger.getLogger(StudentController.class);
	
	// post creamos
	// put actualizamos
	// get consultamos
	// delete borramos
	
	//localhost:8080/students
	@RequestMapping(method = RequestMethod.GET) // sino tambien puedo usar @GetMapping solo y ya lo configura para invocarlo por get.	
	public ResponseEntity<?> getAll(){
		List<StudentModel> list = new ArrayList<StudentModel>();
		list = this.service.findAll();
		
		if (list.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(list);		// envio la lista en el body de la respuesta...Como transforma la lista en json?
	}
	
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)	
	public ResponseEntity<?> getById( @PathVariable("dni") String dni){
		List<StudentValidationError> resp = new ArrayList<StudentValidationError>();
		if (isNullOrEmpty(dni)) {
			resp.add(new StudentValidationError("1", "EL DNI ES OBLIGATORIO"));
		}else if (!validDni(dni)) {
			resp.add(new StudentValidationError("1.1", "EL DNI ES INVALIDO"));
		}
		
		if (resp.isEmpty()) {
			StudentModel model = this.service.findById(dni);
			return ResponseEntity.ok(model);	
		}
		else {
			return new ResponseEntity (resp, HttpStatus.BAD_REQUEST);
		}
		
			
	}
	
	@RequestMapping(method = RequestMethod.POST) // agregar validaciones sobre los campos
	public ResponseEntity<?> create( @RequestBody StudentModel model){
		ResponseEntity result = null;
		
		List<StudentValidationError> errores = validateModel(model);
		if ( errores != null) {
			result = new ResponseEntity (errores , HttpStatus.NOT_ACCEPTABLE);
		}
		else if (!this.service.existId(model.getDni())) {
			this.service.create(model);
			result = new ResponseEntity ("El registro fue creado con exito" , HttpStatus.CREATED);
		}else {
			result = new ResponseEntity ("El dni recibido ya existe en la base de datos", HttpStatus.ALREADY_REPORTED);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/{dni}", method = RequestMethod.PUT) 	// agregar validacion sobre los campos
	public ResponseEntity<?> update( @PathVariable("dni") String dni, @RequestBody StudentModel model){
		ResponseEntity result = null;
		List<StudentValidationError> errores = validateModel(model);
		if ( errores != null) {
			result = new ResponseEntity (errores , HttpStatus.NOT_ACCEPTABLE);
		}
		else if (!this.service.existId(dni)) {			
			result = new ResponseEntity ("El registro a actualizar no fue encontrado", HttpStatus.NOT_FOUND);
		}else {
			this.service.update(model);
			result = new ResponseEntity ("El registro se actualizo correctamente", HttpStatus.OK);
		}
				
		return result;		
	}
	
	@RequestMapping(value = "/{dni}", method = RequestMethod.DELETE) 	
	public ResponseEntity<?> delete( @PathVariable("dni") String dni){
		
		ResponseEntity result = null; 
		if (!this.service.existId(dni)) {			
			result = new ResponseEntity ("El registro a borrar no fue encontrado",HttpStatus.NOT_FOUND);
		}else {
			this.service.delete(dni);
			result = new ResponseEntity ("El registro se borro correctamente",HttpStatus.OK);
		}				
		return result;				
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
