package com.example.demo.workinoutrecords;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.members.Members;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
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
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_time", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_time")
	private int memberid;
	
	@ManyToOne
	@JoinColumn(name = "User_id")
	private Members user;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDate workindt;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDate workoutdt;
	private String state;
//	출근
//	정상근무
//	지각
//	야근
//	휴무
	
	@PrePersist
	public void setDate() {
		workindt = LocalDate.from(LocalDateTime.now());
	}
}
