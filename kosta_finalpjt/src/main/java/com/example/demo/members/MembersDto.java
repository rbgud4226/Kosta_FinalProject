package com.example.demo.members;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.depts.Depts;
import com.example.demo.users.Users;

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

public class MembersDto {
	private Users userid;
	private int memberid;
	private Date birthdt;
	private String email;
	private String cpnum;
	private String address;
	private String memberimgnm;
	private Date hiredt;
	private Date leavedt;
	private Depts deptid;
	private int joblv;
//	private ArrayList<EduWorkExperienceInfo> eweinfo;
	private MultipartFile imgf;
	
}
