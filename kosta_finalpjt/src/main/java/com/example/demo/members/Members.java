package com.example.demo.members;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.depts.Depts;
import com.example.demo.users.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private Users userid;

	@Id
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_memberid", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_memberid")
	private int memberid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdt;
	private String email;
	private String cpnum;
	private String address;
	private String memberimgnm;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hiredt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate leavedt;

	@ManyToOne
	@JoinColumn(nullable = false, name = "deptsId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Depts deptid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Members_Id")
	private Members mgrofmember;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mgrofmember", cascade = CascadeType.ALL)
	private List<Members> mgrids;
	private int joblv;

	@PrePersist
	public void setDate() {
		hiredt = LocalDate.from(LocalDateTime.now());
	}
}
