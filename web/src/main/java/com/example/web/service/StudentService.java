package com.example.web.service;


import com.example.web.domain.Student;

import java.util.List;


public interface StudentService {

	/**
	 * findAll
	 * @return
	 */
	List<Student> findAll();
	
	Student findOne(int id);
}
