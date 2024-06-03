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
}
