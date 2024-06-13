package com.example.demo.users;

import java.util.List;

import com.example.demo.chat.RoomUser.RoomUser;
import com.example.demo.members.MembersDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UsersDto {
	
	@NotBlank(message = "아이디: 필수 입력 정보입니다.")
	private String id;
	@NotBlank(message = "이름: 필수 입력 정보입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름: 특수문자를 제외한 한글이어야 합니다.")
	private String usernm;
	
	@NotBlank(message = "비밀번호: 필수 입력 정보입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호: 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String pwd;
	
	@NotBlank(message = "사용자유형: 필수 입력 정보입니다.")
	private String type;
	private int aprov;
	private MembersDto memberdto;
	private List<RoomUser> roomUsers;
}
