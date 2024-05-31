package com.example.demo.workinoutrecords;

import java.util.Date;

import com.example.demo.members.Members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * ==================================================================
 * 
 * 추가 여부 확인 필요 멤버변수
	//	private Members memberid;
	//	private Date workdt;
	//	private Date workintime;
	//	private Date workouttime;
	//	private String state;
	//	private String workinstate;
	//	private String workoutstate;
 * ==================================================================
*/

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class WorkInOutRecord {
	@Id
	@Column(name = "members_memberid")
	private int memberid;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "members_memberid")
	private Members members;
	private Date workindt;
	private Date workoutdt;
	private String state;
	
	@PrePersist
	public void setDate() {
		workindt = new Date();
		workoutdt = new Date();
	}
}
