package com.example.demo.mariadb.users;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class virtual_users_dto {
  private Integer id;
  private Integer domainId;
  private String password;
  private String email;
  private String box;
}
