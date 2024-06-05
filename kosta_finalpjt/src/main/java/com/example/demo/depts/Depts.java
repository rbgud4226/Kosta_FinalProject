package com.example.demo.depts;

import java.util.List;

import com.example.demo.members.Members;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

public class Depts {
	@Id
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_deptid", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_deptid")
	private int deptid;
	private String deptnm;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Members_deptid", cascade = CascadeType.ALL)
	private List<Members> members;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Members_mgrids", cascade = CascadeType.ALL)
	private List<Members> mgrids;
}