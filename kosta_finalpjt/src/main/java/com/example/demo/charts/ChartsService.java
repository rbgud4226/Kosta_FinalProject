package com.example.demo.charts;

import com.example.demo.users.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChartsService {
  @Autowired
  private ChartsDao dao;

  public ChartsDto save(ChartsDto dto){
    Charts g = dao.save(new Charts(dto.getId(), 0, dto.getTitle(), dto.getSt(),
        dto.getEd(), dto.getPercent(), dto.getDependencies()));
    return new ChartsDto(g.getId(), g.getTaskid(), g.getTitle(), g.getSt(), g.getEd(),
        g.getPercent(), g.getDependencies());
  }

  public ChartsDto get(int id){
    Optional<Charts> optional = dao.findById(id);
    Charts g = optional.get();
    return new ChartsDto(g.getId(), g.getTaskid(), g.getTitle(), g.getSt(),
        g.getEd(), g.getPercent(), g.getDependencies());
  }

  public ArrayList<ChartsDto> getAll(){
    List<Charts> l = dao.findAll();
    ArrayList<ChartsDto> list = new ArrayList<>();
    for(Charts g : l){
      list.add(new ChartsDto(g.getId(), g.getTaskid(), g.getTitle(), g.getSt(),
          g.getEd(), g.getPercent(), g.getDependencies()));
    }
    return list;
  }

  public void del(int id){
    dao.deleteById(id);
  }
}
