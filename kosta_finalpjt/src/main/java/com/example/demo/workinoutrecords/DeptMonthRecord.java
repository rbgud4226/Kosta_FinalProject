package com.example.demo.workinoutrecords;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
public class DeptMonthRecord {
	private String usernm;
	private LocalDate day;
	private String day_of_week;
	private String workinTime;
	private String workOutTime;
	private String workHours;
	private String state;
}
