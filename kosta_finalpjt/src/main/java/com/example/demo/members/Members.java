package com.example.demo.members;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.depts.Depts;
import com.example.demo.depts.Joblvs;
import com.example.demo.users.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@ToString.Exclude
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "depts_deptid")
	@JsonBackReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ToString.Exclude
	private Depts deptid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "joblvs_joblvid")
	@JsonBackReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ToString.Exclude
	private Joblvs joblvid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "mgrid")
	@JsonBackReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ToString.Exclude
	private Members mgrid;

	@OneToMany(mappedBy = "mgrid", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
	@JsonIgnore
	@ToString.Exclude
	private List<Members> mgrinmembers = new ArrayList<Members>();

	@PrePersist
	public void setDate() {
		hiredt = LocalDate.from(LocalDateTime.now());
	}

}
