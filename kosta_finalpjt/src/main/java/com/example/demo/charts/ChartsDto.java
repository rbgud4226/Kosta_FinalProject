package com.example.demo.charts;

import com.example.demo.members.Members;
import com.example.demo.users.Users;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChartsDto {
  private Users users;
  private int taskid;
  private String title;
  private String st;
  private String ed;
  private int percent;
  private String dependencies;
}
