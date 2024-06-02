package com.example.demo.departments;

import com.example.demo.members.Members;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DepartmentsDto {
	private int deptid;
	private String deptnm;
	private Members mgrid;
}
