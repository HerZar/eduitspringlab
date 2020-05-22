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

import com.libraapps.api.aspects.ValidateId;
import com.libraapps.api.aspects.ValidateModel;
import com.libraapps.api.model.StudentModel;
import com.libraapps.api.model.StudentValidationError;
import com.libraapps.api.service.IStudentService;

@RestController
@RequestMapping("students")
public class StudentController {
	//agregar persistencia en base de datos mysql
	@Autowired        //con esto le decimos a spring que injecte un objeto de este tipo
	private IStudentService<StudentModel> service;
	
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
	@ValidateId
	public ResponseEntity<?> getById( @PathVariable("dni") String dni){		
		StudentModel model = null;
		if (this.service.existId(dni)) {
			model = this.service.findById(dni);			
		}else {
			return new ResponseEntity ("El dni recibido no existe en la base de datos", HttpStatus.ALREADY_REPORTED);
		}			
		return ResponseEntity.ok(model);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ValidateModel
	public ResponseEntity<?> create( @RequestBody StudentModel model){       
		if (!this.service.existId(model.getDni())) {
			this.service.create(model);
			return new ResponseEntity ("El registro fue creado con exito" , HttpStatus.CREATED);
		}else {
			return new ResponseEntity ("El dni recibido ya existe en la base de datos", HttpStatus.ALREADY_REPORTED);
		}						
	}		
	
	@RequestMapping(method = RequestMethod.PUT)
	@ValidateModel
	public ResponseEntity<?> update( @RequestBody StudentModel model){		
		if (!this.service.existId(model.getDni())) {			
			return new ResponseEntity ("El registro a actualizar no fue encontrado", HttpStatus.NOT_FOUND);
		}else {
			this.service.update(model);
			return new ResponseEntity ("El registro se actualizo correctamente", HttpStatus.OK);
		}							
	}
	
	@RequestMapping(value = "/{dni}", method = RequestMethod.DELETE)
	@ValidateId
	public ResponseEntity<?> delete( @PathVariable("dni") String dni){		 		
		if (!this.service.existId(dni)) {			
			return new ResponseEntity ("El registro a borrar no fue encontrado",HttpStatus.NOT_FOUND);
		}else {
			this.service.delete(dni);
			return new ResponseEntity ("El registro se borro correctamente",HttpStatus.OK);
		}				
	}	
}
