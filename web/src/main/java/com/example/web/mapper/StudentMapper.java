package com.example.web.mapper;

import java.util.List;

import com.example.web.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface StudentMapper {

	
		
		/**
		 * findOne
		 * @param id
		 * @return
		 */
		@Select(value="select *from boot_user where id=#{id}")
		Student findOne(int id);

		/**
		 * findAll
		 * @return
		 */
		List<Student> findAll();
	
}
