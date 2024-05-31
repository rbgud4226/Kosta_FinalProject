package com.example.demo.charts;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth/chart")
public class ChartsController {
  @Autowired
  private ChartsService service;

  @PostMapping("/add")
  public String addChart(ChartsDto dto){
    service.save(dto);
    return "redirect:/index_emp";
  }

  @GetMapping("/gantt")
  public String ganttForm(){
    return "auth/charts/gantt";
  }

  @GetMapping("/calendar")
  public String calendarForm(){
    return "auth/charts/calendar";
  }

  @GetMapping("/data")
  @ResponseBody
  public ArrayList<ChartsDto> data(){
    ArrayList<ChartsDto> list = service.getAll();
    return list;
  }

  @GetMapping("/cdata")
  @ResponseBody
  public ArrayList<Map> Cdata(){
    ArrayList<Map> list = new ArrayList<>();
    ArrayList<ChartsDto> l = service.getAll();
    for(ChartsDto c : l){
      Map map = new HashMap<>();
      map.put("title", c.getTitle());
      map.put("start", c.getSt());
      map.put("end", c.getEd());
      list.add(map);

    }
    return list;
  }

  @RequestMapping("/del")
  public String delbyid(int id){
    service.del(id);
    return "redirect:/index_emp";
  }
}
