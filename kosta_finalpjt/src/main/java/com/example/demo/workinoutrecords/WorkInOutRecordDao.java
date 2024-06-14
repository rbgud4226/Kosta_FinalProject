package com.example.demo.workinoutrecords;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkInOutRecordDao extends JpaRepository<WorkInOutRecord, Integer> {
	//오늘 날짜 등록 여부 확인
	@Query(value="SELECT * FROM work_in_out_record WHERE user_id =:user_id and day >= TRUNC(SYSDATE) AND day < TRUNC(SYSDATE) + 1",nativeQuery = true)
	ArrayList<WorkInOutRecord> selectDay(@Param("user_id")int user);
	
	//연/월별 (부서)전체 직원 조회
	@Query(value="SELECT usernm, day, day_of_week, workin_Time, work_Out_Time, work_hours, state " +
			"from work_in_out_record W join members M on W.user_id = M.memberid " + 
			"join users u on u.id = m.userid_id " + 
			"WHERE EXTRACT(MONTH FROM W.day) = :month " + 
			"AND EXTRACT(YEAR FROM W.day) = :year " +
			"AND DEPTID_DEPTID = :dept " + 
			"ORDER by day", nativeQuery = true)
	List<Object[]> selectMonth(@Param("month")int month,@Param("year")int year,@Param("dept")int dept);

	//연/월별 (부서)직원 통계
	//사원번호(user_id) 이름 부서번호 직급레벨 총_출근횟수 지각횟수 총_근무시간
	@Query(value="SELECT M.memberid, u.USERNM, m.deptid_deptid, m.joblv, " +
            "COUNT(*) AS total_records, " +
            "SUM(CASE WHEN W.state = '지각' THEN 1 ELSE 0 END) AS total_late_records, " +
            "FLOOR(SUM(TO_NUMBER(SUBSTR(work_hours, 1, 1)) * 60 + TO_NUMBER(SUBSTR(work_hours, 3, 2))) / 60) " +
            "|| ':' || " +
            "MOD(SUM(TO_NUMBER(SUBSTR(work_hours, 1, 1)) * 60 + TO_NUMBER(SUBSTR(work_hours, 3, 2))), 60) AS total_time " +
            "FROM work_in_out_record W " +
            "JOIN members M ON W.user_id = M.memberid " +
            "JOIN users u ON u.id = m.userid_id " +
            "WHERE EXTRACT(MONTH FROM W.day) = :month " +
            "AND EXTRACT(YEAR FROM W.day) = :year " +
            "AND M.deptid_deptid = :dept " +
            "GROUP BY M.memberid, u.USERNM, m.deptid_deptid, m.joblv", nativeQuery = true)
	List<Object[]> chartDept(@Param("month") int month, @Param("year") int year, @Param("dept") int dept);
	
	
	
	//개인의 월(연) 근태기록 조회
	@Query(value="SELECT *	FROM work_in_out_record	WHERE EXTRACT(MONTH FROM day) = :month AND EXTRACT(YEAR FROM day) = :year and user_id =:user_id ORDER by day",nativeQuery = true)
	ArrayList<WorkInOutRecord> selectMonthByUser(@Param("month")int month,@Param("year")int year,@Param("user_id")int user);
}
