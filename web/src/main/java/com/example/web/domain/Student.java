package com.example.web.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

@Getter
@Setter
public class Student implements Serializable {

	private static final long serialVersionUID = 6519997700281088880L;
	
	private Integer id;
	private String name;
	private String tel;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	public Student(){
		System.out.println("构造方法执行");
	}
	@PostConstruct
	public void init(){
		System.out.println("student初始化方法");
	}
}
