//package com.gable.dos.compare.controller;
//
//
//import javax.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class CompareController {
//
//
//	@GetMapping("/")
//	public String home(HttpServletRequest request){
//		request.setAttribute("mode", "MODE_HOME");
//		return "compare";
//	}
//
//	@PostMapping("/compare")
//	public String saveWork(HttpServletRequest request){
//		System.out.println("compare files1=" + request.getAttribute("files1"));
//		System.out.println("compare files2=" + request.getAttribute("files2"));
//
//		Object files1 = request.getAttribute("files1");
////		work.setCreateBy(1);
////		work.setCreateDate(new Date());
////		work.setUpdateBy(1);
////		work.setUpdateDate(new Date());
////		workService.save(work);
////		request.setAttribute("works", workService.findAll());
////		request.setAttribute("mode", "MODE_WORKS");
//		return "compare";
//	}
//
//}
