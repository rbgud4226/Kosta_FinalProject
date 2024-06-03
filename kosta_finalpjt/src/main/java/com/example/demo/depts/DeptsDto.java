package com.example.demo.depts;

import com.example.demo.member.Members;

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

public class DeptsDto {
	private int deptid;
	private String deptnm;
	private Members mgrid;
}
