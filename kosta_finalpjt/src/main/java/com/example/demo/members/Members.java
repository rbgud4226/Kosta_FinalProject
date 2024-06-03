package com.example.demo.members;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.depts.Depts;
import com.example.demo.users.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Members {

	@OneToOne
	@JoinColumn(nullable = false)
	private Users user;

	@Id
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_memberid", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_memberid")
	private int memberid;
	private Date birthdt;
	private String email;
	private String cpnum;
	private String address;
	private String memberimgnm;
	private Date hiredt;
	private Date leavedt;

	@ManyToOne
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Depts deptid;
	private int joblv;

	@PrePersist
	public void setDate() {
		hiredt = new Date();
	}
}
