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
public class ChartDeptMember {
	//사원번호 이름 부서번호 직급레벨 총_출근횟수 지각횟수 총_근무시간
	private int id;
	private String name;
	private String deptNum;
	private String joblv;
	private int totalRecords;
	private int lateCount;
	private String workTime;
}
