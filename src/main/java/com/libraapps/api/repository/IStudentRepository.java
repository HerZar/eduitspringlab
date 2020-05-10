package com.libraapps.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libraapps.api.entity.StudentEntity;


@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, String>{
	
	/// spring jpa al reconocer una interface con el notation 
//	@Repository se encarga de implementar un repositorio basico

}
