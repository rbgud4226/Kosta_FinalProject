package com.example.demo.workinoutrecords;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkInOutRecordDao extends JpaRepository<WorkInOutRecord, Integer> {
	@Query(value="SELECT * FROM work_in_out_record WHERE user_id =:user_id and workindt >= TRUNC(SYSDATE) AND workindt < TRUNC(SYSDATE) + 1",nativeQuery = true)
	ArrayList<WorkInOutRecord> selectDay(@Param("user_id")String user);
	
	//연/월별 (부서)전체 직원 조회
//	@Query(value="SELECT *	FROM work_in_out_record	WHERE EXTRACT(MONTH FROM workindt) = :month AND EXTRACT(YEAR FROM workindt) = :year;",nativeQuery = true)
//	ArrayList<WorkInOutRecord> selectMonth(@Param("month")int month,@Param("year")int year);
	
	//개인의 월(연) 근태기록 조회
	@Query(value="SELECT *	FROM work_in_out_record	WHERE EXTRACT(MONTH FROM workindt) = :month AND EXTRACT(YEAR FROM workindt) = :year and user_id =:user_id",nativeQuery = true)
	ArrayList<WorkInOutRecord> selectMonthByUser(@Param("month")int month,@Param("year")int year,@Param("user_id")String user);
}
