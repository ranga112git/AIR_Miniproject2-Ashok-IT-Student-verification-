package com.example1.demo.controller;


import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example1.demo.binding.AddEnqData;
import com.example1.demo.binding.DashBoard;
import com.example1.demo.binding.SearchEnqData;
import com.example1.demo.entity.AddEnquiry;


import com.example1.demo.service.AddEnquiryService;

@Controller
public class AddEnquiry_Controller {
	@Autowired
	private HttpSession session;
	@Autowired
	private AddEnquiryService service;
	
	

	@GetMapping("/dashboard")
	public String dashBoardPage(Model model) {
		Integer userId=(Integer)session.getAttribute("id");
		
		DashBoard dashBoardData=service.getDashBoard(userId);
		model.addAttribute("dashboardData", dashBoardData);
		 
		return "dashboard";
	}
	@GetMapping("/addEnquiry")
	public String addEnquiryPage(Model model) {
		
		List<String> courses=service.getCourses();
		 List<String>    enqStatus= service.getEnqStatus();
			
			AddEnqData formObj=new AddEnqData();
			model.addAttribute("courseNames", courses);
			model.addAttribute("enqStatusNames", enqStatus);
			model.addAttribute("formObj", formObj);
		return "addEnq";
	}
	
	@PostMapping("/addEnquiry")
	public String addEnquiryPage(Model model,@ModelAttribute("formObj") AddEnqData data) {
	boolean	status=service.saveEnquiry(data);
		if(status) {
			model.addAttribute("suc", "Enquiry Added");
		}else {
			model.addAttribute("err", "problems occurd");
		}
		List<String> courses=service.getCourses();
		 List<String>    enqStatus= service.getEnqStatus();

		AddEnqData formObj=new AddEnqData();
		model.addAttribute("courseNames", courses);
		model.addAttribute("enqStatusNames", enqStatus);
		model.addAttribute("formObj", formObj);
		
		return "addEnq";
	}
	
	
	@GetMapping("/viewEq")
	public String viewEnquiryPage(SearchEnqData data,Model model) {
		initForm(model);
		
	List<AddEnquiry>	enquiries=service.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "viewEnq";
		}
	@GetMapping("/getFilter")
	public String getFilterData(@RequestParam(required = false)  String cname,
			@RequestParam(required = false)	String status,@RequestParam(required = false) String mode,Model model) {
		
		SearchEnqData enqDAta=new SearchEnqData();
		enqDAta.setClassMode(mode);
		enqDAta.setCourseName(cname);
		enqDAta.setEnqStatus(status);
		
		Integer userId=(Integer)session.getAttribute("id");
		 List<AddEnquiry> filterEnqs=service.getFilterEnqs(enqDAta, userId);
		model.addAttribute("enquiries", filterEnqs);
		
		return "filterEnq";
		
	}
	
	
	private void initForm(Model model) {
	List<String>	courses=service.getCourses();
	List<String>	enqStatus=service.getEnqStatus();
	SearchEnqData searchData=new SearchEnqData();
	
	     model.addAttribute("courses", courses);
	     model.addAttribute("enqStatus", enqStatus);
	     model.addAttribute("searchData", searchData);
	}
	
	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {

	    AddEnqData enqData = service.getEnquiryById(enqId);
	    model.addAttribute("formObj", enqData);

	    // Add dropdown data if needed (status, course names, etc.)
	   
	    List<String> courses=service.getCourses();
		 List<String>    enqStatus= service.getEnqStatus();
			
			
			model.addAttribute("courseNames", courses);
			model.addAttribute("enqStatusNames", enqStatus);
	    return "addEnq";  // this is your add enquiry form page
	}
	
	
	
	
	
	@GetMapping("logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	
}
