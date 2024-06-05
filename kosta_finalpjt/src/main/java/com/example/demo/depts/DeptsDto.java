package com.example.demo.depts;

import java.util.List;

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

public class DeptsDto {
	private int deptid;
	private String deptnm;
	private List<Members> members;
	private List<Members> mgrid;
}
