package com.example.web.service.impl;

import java.util.List;

import com.example.web.domain.Student;
import com.example.web.mapper.StudentMapper;
import com.example.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	public List<Student> findAll() {
		
		return studentMapper.findAll();

	}

	@Override
	public Student findOne(int id) {
		
		return studentMapper.findOne(id);
	}

	
}
