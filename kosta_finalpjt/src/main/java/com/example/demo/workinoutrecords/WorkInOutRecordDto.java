package com.example.demo.workinoutrecords;

import java.util.Date;

import com.example.demo.members.Members;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkInOutRecordDto {
	private int memberid;
	private Members user;
	private Date workindt;
	private Date workoutdt;
	private String state;
}
