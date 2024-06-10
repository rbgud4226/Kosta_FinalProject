package com.example.demo.users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.members.MembersDto;
import com.example.demo.members.MembersService;

@Controller
public class UsersController {
	@Autowired
	private UsersService uservice;

	@Autowired
	private MembersService mservice;

	@GetMapping("/user/userjoin")
	public String userjoinform() {
		return "user/userjoin";
	}

	@ResponseBody
	@GetMapping("/user/idcheck")
	public Map idcheck(String id) {
		Map map = new HashMap();
		UsersDto udto = uservice.getById(id);
		boolean flag = false;
		if (udto == null) {
			flag = true;
		}
		map.put("flag", flag);
		return map;
	}

	@PostMapping("/user/userjoin")
	public String userjoin(UsersDto dto) {
		dto.setAprov(0);
		uservice.save(dto);
		return "redirect:/";
	}

	@GetMapping("/user/useredit")
	public String usereditform(String id, ModelMap map) {
		UsersDto udto = uservice.getById(id);
		udto.setMemberdto(mservice.getByuserId(udto.getId()));
		map.addAttribute("user", udto);
		return "user/useredit";
	}

	@PostMapping("/user/useredit")
	public String useredit(UsersDto udto) {
		uservice.save(udto);
		return "redirect:/user/userinfo?id=" + udto.getId();
	}

	@GetMapping("/user/userinfo")
	public String myinfo(String id, ModelMap map) {
//		System.out.println("user:" + uservice.getById(id));
		UsersDto udto = uservice.getById(id);
		udto.setMemberdto(mservice.getByuserId(udto.getId()));
		map.addAttribute("user", udto);
		return "user/userinfo";
	}

	@GetMapping("/admin/user/userlist")
	public String userlist(ModelMap map) {
		ArrayList<UsersDto> ulist = uservice.getAll();
		for (UsersDto udto : ulist) {
			mservice.getByuserId(udto.getId());
			udto.setMemberdto(mservice.getByuserId(udto.getId()));
		}
//		System.out.println("ulist:" + ulist);
		map.addAttribute("ulist", ulist);
		return "user/userlist";
	}

	@PostMapping("/admin/user/getdeptby")
	public ModelAndView getuserby(String val, int type) {
		ArrayList<UsersDto> ulist = null;
		if (type == 1) {
			ulist = uservice.getByIdLike(val);
			for (UsersDto udto : ulist) {
				MembersDto mdto = mservice.getByuserId(udto.getId());
				try {
					if (mdto.getUserid() == null) {
						udto.setMemberdto(
								new MembersDto(null, 0, null, null, null, null, null, null, null, null, 0, null, null));
					} else if (mdto.getUserid() != null && udto.getId() == mdto.getUserid().getId()) {
						udto.setMemberdto(mdto);
					}
				} catch (NullPointerException e) {
//					e.printStackTrace();
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		} else if (type == 2) {
			ulist = new ArrayList<UsersDto>();
			for (UsersDto udto : uservice.getAll()) {
				ArrayList<MembersDto> mlist = mservice.getByDeptNm(val);
				for (MembersDto mdto : mlist) {
					if (udto.getMemberdto() != null && udto.getId() == mdto.getUserid().getId()) {
						udto.setMemberdto(mdto);
						ulist.add(udto);
					}
				}
			}
		} else if (type == 3) {
			ulist = uservice.getByUsernmLike(val);
		} else if (type == 4) {
			ulist = new ArrayList<UsersDto>();
			for (UsersDto udto : uservice.getAll()) {
				ArrayList<MembersDto> mlist = mservice.getByJobLv(Integer.parseInt(val));
				for (MembersDto mdto : mlist) {
					if (udto.getMemberdto() != null && udto.getId() == mdto.getUserid().getId()) {
						udto.setMemberdto(mdto);
						ulist.add(udto);
					}
				}
			}
		} else if (type == 5) {
			ulist = uservice.getByAprov(Integer.parseInt(val));
			for (UsersDto udto : ulist) {
				MembersDto mdto = mservice.getByuserId(udto.getId());
				try {
					if (mdto.getUserid() == null) {
						udto.setMemberdto(
								new MembersDto(null, 0, null, null, null, null, null, null, null, null, 0, null, null));
					} else if (mdto.getUserid() != null && udto.getId() == mdto.getUserid().getId()) {
						udto.setMemberdto(mdto);
					}
				} catch (NullPointerException e) {
//					e.printStackTrace();
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
//			System.out.println("valint:" + uservice.getByAprov(Integer.parseInt(val)));
		}
		System.out.println("ulist:" + ulist);
		ModelAndView mav = new ModelAndView("user/userlist");
		mav.addObject("type", type);
		mav.addObject("val", val);
		mav.addObject("ulist", ulist);
		return mav;
	}
	
	@GetMapping("/admin/user/useraprov")
	public String useraprov(String id, int aprov) {
		UsersDto udto = uservice.getById(id);
		MembersDto mdto = mservice.getByuserId(id);
		udto.setAprov(aprov);
		uservice.save(udto);
		if (aprov == 3) {
			mdto.setLeavedt(LocalDate.from(LocalDateTime.now()));
			mservice.save(mdto);
		} else {
			mdto.setLeavedt(null);
			mservice.save(mdto);
		}
		return "redirect:/admin/user/userlist";
	}
}
