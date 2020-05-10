package com.libraapps.api.service;

import java.util.List;

import com.libraapps.api.model.StudentModel;

/**
 * @author hernan
 * una capa de servicios es donde vamos a definir las definiciones de la logica de negocio.
 *
 */
public interface IStudentService <T>{

	
	
	/**metodo que nos va a devolver un list de estudiantes registrados/
	 */
	
	public List<T> findAll();
	
	public  T findById (String dni);
	
	public void create (T model);

	public void update(T model);

	public void delete(String dni);
	
	public boolean existId(String dni);
	
}
