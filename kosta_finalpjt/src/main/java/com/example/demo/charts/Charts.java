package com.example.demo.charts;

import com.example.demo.members.Members;
import com.example.demo.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Charts {

  @ManyToOne
  @JoinColumn(nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Users users;

  @Id
  @SequenceGenerator(name = "seq_gen", sequenceName = "seq_charts", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_charts")
  private int taskid;

  private String resource;
  private String title;
  private String st;
  private String ed;
  @ColumnDefault("0")
  private int percent;
  private String dependencies;
  private String status;
}
