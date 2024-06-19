package com.example.demo.workinoutrecords;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkInOutRecordService {
	@Autowired
	private WorkInOutRecordDao dao;
	
	//당일 출퇴근 기록 여부 조회
	public ArrayList<WorkInOutRecordDto> selectByDate(int user) {
		ArrayList<WorkInOutRecord> wo = dao.selectDay(user);
		ArrayList<WorkInOutRecordDto> list = new ArrayList<>();
		for(WorkInOutRecord w : wo) {
			list.add(new WorkInOutRecordDto(w.getDaynum(),w.getUser(),w.getDayOfWeek(),w.getDay(),w.getWorkinTime(),w.getWorkOutTime(),w.getWorkHours(),w.getState()));
		}
		return list;
	}

	//출근,퇴근(save로 처음 등록, 퇴근 등록시 save로 수정)
	public WorkInOutRecordDto save(WorkInOutRecordDto dto) {
		WorkInOutRecord w = dao.save(new WorkInOutRecord(dto.getDaynum(),dto.getUser(),dto.getDayOfWeek(),dto.getDay(),dto.getWorkinTime(),dto.getWorkOutTime(),dto.getWorkHours(),dto.getState()));
		return new WorkInOutRecordDto(w.getDaynum(),w.getUser(),w.getDayOfWeek(),w.getDay(),w.getWorkinTime(),w.getWorkOutTime(),w.getWorkHours(),w.getState());
	}
	
	//한개 기록 조회용(퇴근용)
	public WorkInOutRecordDto select(int memberid) {
		WorkInOutRecord w = dao.findById(memberid).orElse(null);
		if(w == null) {
			return null;
		}
		return new WorkInOutRecordDto(w.getDaynum(),w.getUser(),w.getDayOfWeek(),w.getDay(),w.getWorkinTime(),w.getWorkOutTime(),w.getWorkHours(),w.getState());
	}

	//직원 월별 기록 조회
	public ArrayList<MemRecord> selectUser(int month,int year,int user){
		ArrayList<WorkInOutRecord> wo = dao.selectMonthByUser(month, year, user);
		ArrayList<MemRecord> list = new ArrayList<>();
		for(WorkInOutRecord w : wo) {
			list.add(new MemRecord(w.getDayOfWeek(),w.getDay(),w.getWorkHours(),w.getState()));
		}
		return list;
	}
	
	//부서별 직원 월별 기록 조회하기
//	public ArrayList<DeptMonthRecord> selectDept(int month,int year,int dept){
//		List<Object[]> deptArray = dao.selectMonth(month, year, dept);
//		ArrayList<DeptMonthRecord> list = new ArrayList<DeptMonthRecord>();
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
//	        
//		for(Object[] a: deptArray) {
//			DeptMonthRecord d = new DeptMonthRecord();
//			d.setUsernm((String)a[0]);
//
//			Timestamp timestamp = (Timestamp) a[1];
//			LocalDateTime localDateTime = timestamp.toLocalDateTime();
//			LocalDate localDate = localDateTime.toLocalDate();
//			d.setDay(localDate);
//
//			d.setDay_of_week((String)a[2]);
//			d.setWorkinTime((String)a[3]);
//			d.setWorkOutTime((String)a[4]);
//			d.setWorkHours((String)a[5]);
//			d.setState((String)a[6]);
//			list.add(d);
//		}
//	
//		return list;
//	}
	
	//부서 직원 통계기록
	public ArrayList<ChartDeptMember> chartMonthandDept(int month,int year,int dept){
		List<Object[]> deptlist = dao.chartDept(month, year, dept);
		ArrayList<ChartDeptMember> list = new ArrayList<ChartDeptMember>();
		for(Object[] a: deptlist) {
			ChartDeptMember c = new ChartDeptMember();
			c.setId(((Number) a[0]).intValue());
			c.setName((String) a[1]);
			c.setDeptNum((String) a[2]); 
			c.setJoblv((String) a[3]);
			c.setTotalRecords(((Number) a[4]).intValue());
			c.setLateCount(((Number) a[5]).intValue());
			c.setWorkTime((String) a[6]);
			list.add(c);
		}
		return list;
	}
	
	
	
	
}
