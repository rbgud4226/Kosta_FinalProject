package com.example.demo.charts;

import com.example.demo.users.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChartsService {
  @Autowired
  private ChartsDao dao;

  public ChartsDto save(ChartsDto dto){
    Charts g = dao.save(new Charts(dto.getUsers(), 0,dto.getResource(), dto.getTitle(), dto.getSt(),
        dto.getEd(), dto.getPercent(), dto.getDependencies(), dto.getStatus()));
    return new ChartsDto(g.getUsers(), g.getTaskid(),g.getResource(), g.getTitle(), g.getSt(), g.getEd(),
        g.getPercent(), g.getDependencies(), g.getStatus());
  }

  public ChartsDto get(int id){
    Optional<Charts> optional = dao.findById(id);
    Charts g = optional.get();
    return new ChartsDto(g.getUsers(), g.getTaskid(), g.getResource(), g.getTitle(), g.getSt(),
        g.getEd(), g.getPercent(), g.getDependencies(), g.getStatus());
  }

  public ArrayList<ChartsDto> getAll(){
    List<Charts> l = dao.findAll();
    ArrayList<ChartsDto> list = new ArrayList<>();
    for(Charts g : l){
      list.add(new ChartsDto(g.getUsers(), g.getTaskid(), g.getResource(), g.getTitle(), g.getSt(),
          g.getEd(), g.getPercent(), g.getDependencies(), g.getStatus()));
    }
    return list;
  }

  public ArrayList<ChartsDto> getbyUsers(String id){
    List<Charts> l = dao.findByUsersOrderByTaskidDesc(new Users(id,null,null,null,0,null));
    ArrayList<ChartsDto> list = new ArrayList<>();
    for(Charts g : l){
      list.add(new ChartsDto(g.getUsers(), g.getTaskid(), g.getResource(), g.getTitle(), g.getSt(),
          g.getEd(), g.getPercent(), g.getDependencies(), g.getStatus()));
    }
    return list;
  }

  public ArrayList<ChartsDto> ganttList(String id){
    List<Charts> l = dao.findByUsersOrderByTaskidDesc(new Users(id,null,null,null,0,null));
    ArrayList<ChartsDto> fillteredList = l.stream()
        .map(g -> new ChartsDto(g.getUsers(), g.getTaskid(), g.getResource(), g.getTitle(), g.getSt(), g.getEd(), g.getPercent(), g.getDependencies(), g.getStatus()))
        .filter(chartsDto -> chartsDto.getStatus().contains("yes"))
        .collect(Collectors.toCollection(ArrayList::new));
    return fillteredList;
  }

  public void del(int id){
    dao.deleteById(id);
  }
}
