package com.example.demo.members;

import java.util.Date;

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

public class EduWorkExperienceInfoDto {
	private int eweid;
	private Members members;
	private Date startdt;
	private Date enddt;
	private String ewenm1;
	private String ewenm2;
	private int state;
	private int type;
}
