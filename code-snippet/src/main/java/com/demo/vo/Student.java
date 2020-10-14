package com.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wang
 * @since 2020/8/22
 */
@Getter
@Setter
@ToString
public class Student {

    private Long id;
    private String name;
    private Integer age;
    private String address;
}
