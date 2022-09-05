package com.khj.exam.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.khj.exam.demo.service.GenFileService;
import com.khj.exam.demo.service.MemberService;
import com.khj.exam.demo.utill.Ut;
import com.khj.exam.demo.vo.Article;
import com.khj.exam.demo.vo.Board;
import com.khj.exam.demo.vo.Member;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;

@Controller
public class AdmMemberController {
	private MemberService memberService;
	private GenFileService genFileService;
	private Rq rq;

	public AdmMemberController(MemberService memberService, GenFileService genFileService, Rq rq) {
		this.memberService = memberService;
		this.genFileService = genFileService;
		this.rq = rq;
	}

	@RequestMapping("/adm/member/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") int authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		int membersCount = memberService.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);

		int itemsCountInAPage = 10;
		int pagesCount = (int) Math.ceil((double) membersCount / itemsCountInAPage);
		List<Member> members = memberService.getForPrintMembers(authLevel, searchKeyword, searchKeywordTypeCode, itemsCountInAPage,
				page);

		model.addAttribute("authLevel",authLevel);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		
		model.addAttribute("membersCount", membersCount);
		model.addAttribute("members", members);

		return "adm/member/list";
	}
}
