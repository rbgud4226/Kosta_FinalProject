package com.example.demo.workinoutrecords;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.example.demo.charts.ChartsDto;
import com.example.demo.charts.ChartsService;
import com.example.demo.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.members.Members;
import com.example.demo.members.MembersDto;
import com.example.demo.members.MembersService;


@Controller
@RequestMapping("/auth/record")
public class WorkInOutRecordController {
  @Autowired
  private WorkInOutRecordService service;

  @Autowired
  private MembersService mservice;

  @Autowired
  private ChartsService cservice;

  //개인
  //출퇴근 기록 페이지로 이동하기
  @GetMapping("/my")
  public String myrecord(String Members, ModelMap map) {
    MembersDto md = mservice.getByuserId(Members);
    Members m = new Members(md.getUserid(), md.getMemberid(), md.getBirthdt(), md.getEmail(), md.getCpnum(), md.getAddress(), md.getMemberimgnm(), md.getHiredt(), md.getLeavedt(), md.getDeptid(), md.getJoblvid(), md.getMgrid(), null);
    if (m == null) {
      return "error";
    }
    //출근기록x
    boolean flag = false;
    ArrayList<WorkInOutRecordDto> list = service.selectByDate(m.getMemberid());
    //출근기록O
    if (!list.isEmpty()) {
      flag = true;
      //오늘날짜 출근 등록번호(퇴근시 필요)
      map.put("num", list.get(0).getDaynum());
    }
    //내 근무기록
    // 현재 달/년도 가져오기
    LocalDate currentDate = LocalDate.now();
    int currentMonth = currentDate.getMonthValue() - 1;
    int currentYear = currentDate.getYear();
    ArrayList<MemRecord> mylist = service.selectUser(currentMonth, currentYear, m.getMemberid());
    //이번달 출근 기록
    map.put("list", mylist);
    //오늘 날짜 출근 여부
    map.put("flag", flag);
    //사원번호 반환
    map.put("mynum", m.getMemberid());
    return "record/my";
  }

  //출근하기
  @ResponseBody
  @PostMapping("/in")
  public Map workin(String Members) {
    MembersDto md = mservice.getByuserId(Members);
    Members m = new Members(md.getUserid(), md.getMemberid(), md.getBirthdt(), md.getEmail(), md.getCpnum(), md.getAddress(), md.getMemberimgnm(), md.getHiredt(), md.getLeavedt(), md.getDeptid(), md.getJoblvid(), md.getMgrid(), null);
    String type = "출근";

    //지각 체크
    LocalTime currentTime = LocalTime.now();
    LocalTime targetTime = LocalTime.of(9, 0);

    // 현재 시간이 9시 이전인지 확인
    if (currentTime.isAfter(targetTime)) {
      type = "지각";
    }
    WorkInOutRecordDto d = service.save(new WorkInOutRecordDto(0, m, null, null, null, null, null, type));
    System.out.println(d);
    Map map = new HashMap<>();
    map.put("num", d.getDaynum());
    map.put("flag", "true");
    map.put("state", type);
    return map;
  }


  //퇴근하기
  @ResponseBody
  @PostMapping("/out")
  public void workout(String Members, int memberid) {
    WorkInOutRecordDto w = service.select(memberid);
    String type = "정상근무";

    //근무 시간 확인
    LocalTime currentTime = LocalTime.now();
    LocalTime targetTime = LocalTime.of(18, 30);
    LocalTime targetTime2 = LocalTime.of(17, 50);
    //퇴근시간 기록
    w.setWorkOutTime(String.format("%02d:%02d", currentTime.getHour(), currentTime.getMinute()));
    if (currentTime.isAfter(targetTime)) {
      type = "추가근무";
    } else if (currentTime.isBefore(targetTime2)) {
      type = "조기퇴근";
    }
    if (w.getState().equals("지각")) {
      type = "지각";
    }

    //근무한 총 시간 확인
    LocalTime workinTime = LocalTime.parse(w.getWorkinTime());
    LocalTime workoutTime = LocalTime.parse(w.getWorkOutTime());
    Duration duration = Duration.between(workinTime, workoutTime);

    // 시간 차이
    long hours = duration.toHours();
    long minutes = duration.toMinutes() % 60;
    if (hours >= 9) {
      hours--;
    }
    String worktime = String.format("%d:%02d", hours, minutes);

    w.setState(type);
    w.setWorkHours(worktime);
    service.save(w);
  }

  //휴가 기록
  @ResponseBody
  @PostMapping("/offday")
  public Map offRecord(String members, String res, String date1, String date2) {
    LocalDate startDate = LocalDate.parse(date1);
    LocalDate endDate = LocalDate.parse(date2);
    //멤버 정보
    MembersDto md = mservice.getByuserId(members);
    Members m = new Members(md.getUserid(), md.getMemberid(), md.getBirthdt(), md.getEmail(), md.getCpnum(), md.getAddress(), md.getMemberimgnm(), md.getHiredt(), md.getLeavedt(), md.getDeptid(), md.getJoblvid(), md.getMgrid(), null);
    String type = res;

    //주말 제외 저장하기
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      if (!isWeekend(currentDate)) {
        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        ;
        service.save(new WorkInOutRecordDto(0, m, dayOfWeek, currentDate, "00:00", "00:00", "00:00", type));
      }
      currentDate = currentDate.plusDays(1);
    }
    String chartTitle = members + " 의 " + res;
    cservice.save(new ChartsDto(new Users(members, null, null, null, 0, null), 0, "휴가", chartTitle, date1, date2, 100, null, null));
    Map map = new HashMap<>();
    map.put("flag", "success");
    return map;
  }

  // 주말확인
  private static boolean isWeekend(LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
  }

  //내 근태기록 확인하기
  @ResponseBody
  @GetMapping("/getmonth")
  public Map myrecord(String Members, int count) {
    MembersDto md = mservice.getByuserId(Members);
    Members m = new Members(md.getUserid(), md.getMemberid(), md.getBirthdt(), md.getEmail(), md.getCpnum(), md.getAddress(), md.getMemberimgnm(), md.getHiredt(), md.getLeavedt(), md.getDeptid(), md.getJoblvid(), md.getMgrid(), null);
    // 현재 날짜 가져오기
    LocalDate currentDate = LocalDate.now();
    //필요 달/년도 가져오기
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();
    // 이전 달로 이동
    currentMonth = currentMonth + count - 1;
    if (currentMonth == 0) {
      currentMonth = 12;
      currentYear--;
    }
    ArrayList<MemRecord> list = service.selectUser(currentMonth, currentYear, m.getMemberid());
    Map map = new HashMap<>();
    map.put("list", list);
    return map;
  }


  //관리자(직원기록 확인하기)
//	@ResponseBody
//	@GetMapping("/deptRecord")
//	public void list(int dept,int cnt) {
//		// 현재 날짜 가져오기
//        LocalDate currentDate = LocalDate.now();
//        // 현재 달/년도 가져오기
//        int currentMonth = currentDate.getMonthValue();
//        int currentYear = currentDate.getYear();
//       
//        // 이전 달로 이동
//        int previousMonth = currentMonth + cnt;
//        int previousYear = currentYear;
//        if (previousMonth == 0) { 
//            previousMonth = 12; 
//            previousYear--;
//        }
//        ArrayList<DeptMonthRecord> list = service.selectDept(previousMonth, previousYear, dept);
//	}

  //부서 근태 부서장 페이지 이동
  @GetMapping("/dept")
  public ModelAndView deptList(int dept) {
    ModelAndView mav = new ModelAndView("record/dept");
    System.out.println(deptRecord(dept, -1));
    mav.addObject("list", deptRecord(dept, -1));
    return mav;
  }

  //관리자
  @ResponseBody
  @GetMapping("/list")
  public Map deptRecord(int dept, int cnt) {
    // 현재 날짜 가져오기
    LocalDate currentDate = LocalDate.now();
    // 현재 달/년도 가져오기
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();

    // 이전 달로 이동
    int previousMonth = currentMonth + cnt;
    int previousYear = currentYear;
    if (previousMonth == 0) {
      previousMonth = 12;
      previousYear--;
    }

    Map map = new HashMap<>();
    map.put("list", service.chartMonthandDept(previousMonth, previousYear, dept));
    return map;
  }
}
