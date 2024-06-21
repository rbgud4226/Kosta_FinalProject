package com.example.demo.workinoutrecords;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

//관리자 페이지 선형 통계용
public class DeptsYearWorkData {
	private int deptnum;
	private String month;
	private int workhours;
}
