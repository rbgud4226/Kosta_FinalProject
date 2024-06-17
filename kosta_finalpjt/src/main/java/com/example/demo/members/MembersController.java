package com.example.demo.members;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.users.Users;
import com.example.demo.users.UsersDto;
import com.example.demo.users.UsersService;

@Controller
public class MembersController {

	@Autowired
	private MembersService mservice;

	@Autowired
	private UsersService uservice;

	@Autowired
	private EduWorkExperienceInfoService eservice;

	@Value("${spring.servlet.multipart.location}")
	private String path;

	private String dirName = "";

	
	@GetMapping("/member/memberlist")
	public ModelMap memberlist(ModelMap map) {
		ArrayList<MembersDto> mlist = mservice.getAll();
		return map.addAttribute("mlist", mlist);
//		return "member/memberlist";
	}

	@ResponseBody
	@GetMapping("/member/getdeptby")
	public Map getmemberby(String val, int type) {
		ArrayList<MembersDto> mlist = new ArrayList<MembersDto>();
		if (!val.equals("")) {
			if (type == 1) {
				if (val.equals("0")) {
					mlist = null;
				} else {
					mlist = mservice.getByDeptNm(val);
				}
			} else if (type == 2) {
				UsersDto udto = uservice.getByUsernm(val);
				if (udto == null) {
					mlist.add(null);
				} else {
					mlist.add(mservice.getByuserNm(new Users(udto.getId(), "", "", "", 0, null)));
				}
//				System.out.println(mlist);
			} else if (type == 3) {
				mlist = mservice.getByJobLv(Integer.parseInt(val));
			}
		} else {
			mlist = null;
		}
//		ModelAndView mav = new ModelAndView("member/memberlist");
//		mav.addObject("type", type);
//		mav.addObject("val", val);
		Map map = new HashMap<>();
		map.put("mlist", mlist);
		return map;
	}

	@GetMapping("/member/memberinfo")
	public String memberinfo(String id, ModelMap map) {
		MembersDto mdto = mservice.getByuserId(id);
		System.out.println(mdto.getUserid().getAprov());
		String aprovStr = "";
		if (mdto.getUserid().getAprov() == 0) {
			aprovStr = "승인대기상태";
		} else if (mdto.getUserid().getAprov() == 1) {
			aprovStr = "재직상태";
		} else if (mdto.getUserid().getAprov() == 2) {
			aprovStr = "휴직상태";
		} else if (mdto.getUserid().getAprov() == 3) {
			aprovStr = "퇴직상태";
		}

		map.addAttribute("member", mdto);
		map.addAttribute("aprovStr", aprovStr);
		return "member/memberinfo";
	}

	@GetMapping("/member/memberimg")
	public ResponseEntity<byte[]> read_img(String memberimgnm) {
		ResponseEntity<byte[]> result = null;
		if (memberimgnm != "") {
			File f = new File(path + dirName + memberimgnm);
			HttpHeaders header = new HttpHeaders();
			try {
				header.add("Content-Type", Files.probeContentType(f.toPath()));
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			result = new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return result;
	}

	@GetMapping("/member/memberedit")
	public String membereditform(String id, ModelMap map) {
		MembersDto mdto = mservice.getByuserId(id);
		map.addAttribute("member", mdto);
		map.addAttribute("userid", id);
		return "member/memberedit";
	}

	@PostMapping("/member/memberadd")
	public String memberadd(MembersDto dto) {
//		System.out.println("memberimgnm:" + dto.getMemberimgnm());
		System.out.println(dto);
		MembersDto mdto = mservice.save(dto);
//		MembersDto mdto = null;
//		if (dto.getMemberid() == 0) {
//			mdto = mservice.save(dto);
//		} else {
//			mdto = mservice.update(dto);
//		}
		
		if (!dto.getMemberimgf().isEmpty()) {
			String oname = dto.getMemberimgf().getOriginalFilename();
			String f1 = oname.substring(oname.lastIndexOf("."));
			String f2 = oname.substring(oname.lastIndexOf(".") + 1, oname.length());
			String f3 = oname.substring(0, oname.lastIndexOf("."));
			String fname = f3 + " (" + mdto.getUserid().getUsernm() + ")." + f2;
			File newFile = new File(path + dirName + fname);
			try {
				dto.getMemberimgf().transferTo(newFile);
				mdto.setMemberimgnm(newFile.getName());
//				System.out.println(mdto.getMemberimgnm());
				mservice.save(mdto);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return "redirect:/user/userinfo?id=" + dto.getUserid().getId();
	}

	@PostMapping("/member/eweiadd")
	public String eweiadd(EduWorkExperienceInfoDto edto) {
		System.out.println("eweidto:" + edto);
		EduWorkExperienceInfoDto eweidto = eservice.save(edto);
		eweidto.setMemberid(edto.getMemberid());
		eservice.save(eweidto);
		return "redirect:/user/userinfo?id=" + edto.getMemberid().getUserid().getId();
	}

}
