package com.example.demo.workinoutrecords;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkInOutRecordService {
	@Autowired
	private WorkInOutRecordDao dao;
	
	//당일 출퇴근 기록 여부 조회
	public ArrayList<WorkInOutRecordDto> selectByDate(String user) {
		ArrayList<WorkInOutRecord> wo = dao.selectDay(user);
		ArrayList<WorkInOutRecordDto> list = new ArrayList<>();
		for(WorkInOutRecord w : wo) {
			list.add(new WorkInOutRecordDto(w.getMemberid(),w.getUser(),w.getWorkindt(),w.getWorkoutdt(),w.getState()));
		}
		return list;
	}

	//출근,퇴근(save로 처음 등록, 퇴근 등록시 save로 수정)
	public WorkInOutRecordDto save(WorkInOutRecordDto dto) {
		WorkInOutRecord w = dao.save(new WorkInOutRecord(dto.getMemberid(),dto.getUser(),dto.getWorkindt(),dto.getWorkoutdt(),dto.getState()));
		return new WorkInOutRecordDto(w.getMemberid(),w.getUser(),w.getWorkindt(),w.getWorkoutdt(),w.getState());
	}
	
	//한개 기록 조회용
	public WorkInOutRecordDto select(int memberid) {
		WorkInOutRecord w = dao.findById(memberid).orElse(null);
		if(w == null) {
			return null;
		}
		return new WorkInOutRecordDto(w.getMemberid(),w.getUser(),w.getWorkindt(),w.getWorkoutdt(),w.getState());
	}

	//직원 월별 기록 조회
	public ArrayList<WorkInOutRecordDto> selectUser(int month,int year,String user){
		ArrayList<WorkInOutRecord> wo = dao.selectMonthByUser(month, year, user);
		ArrayList<WorkInOutRecordDto> list = new ArrayList<>();
		for(WorkInOutRecord w : wo) {
			list.add(new WorkInOutRecordDto(w.getMemberid(),w.getUser(),w.getWorkindt(),w.getWorkoutdt(),w.getState()));
		}
		return list;
	}
}
