package com.libraapps.api.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.libraapps.api.entity.StudentEntity;
import com.libraapps.api.model.StudentModel;
import com.libraapps.api.repository.IStudentRepository;

@Service ("studentServiceBean")
public class StudentServiceImpl implements IStudentService<StudentModel>{

	@Autowired  // quien implementa esta interface studentrepository? Spring implementa el repositorio
	private IStudentRepository repository;
	
	@Override
	public List<StudentModel> findAll() {
		List<StudentModel> list = this.repository.findAll().stream().
				map((e) ->new StudentModel(e.getName(),e.getLastName(),e.getDni(),e.getEmail(),e.getMobile())).collect(Collectors.toList());
		return list;
	}
	
	@Override
	public  StudentModel findById (String dni) {
		StudentEntity entity = null; 
				entity = repository.findById(dni).get();
		StudentModel model = null;
		if (entity != null) {
			model = new StudentModel(entity.getName(), entity.getLastName(),entity.getDni(),entity.getEmail(),entity.getMobile());
		}
		
		return model;
		
	}

	@Override
	public void create(StudentModel model) {
		
		StudentEntity entity = new StudentEntity();		
		//el bean util copy properties se encarga de copiar valor de propiedades que mantienen exactamente el mismo nombre.
		BeanUtils.copyProperties(model, entity);
		this.repository.save(entity);
		
	}

	@Override
	public void update(StudentModel model) {		
		StudentEntity entity = new StudentEntity();		
		//el bean util copy properties se encarga de copiar valor de propiedades que mantienen exactamente el mismo nombre.
		BeanUtils.copyProperties(model, entity);		
		repository.save(entity);		
	}

	@Override
	public void delete(String dni) {		
		this.repository.deleteById(dni);		
	}

	@Override
	public boolean existId(String dni) {
		
		return this.repository.existsById(dni);
	}

}
