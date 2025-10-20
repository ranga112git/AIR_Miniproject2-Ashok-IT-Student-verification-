package com.example1.demo.service;

import java.util.List;

import com.example1.demo.binding.AddEnqData;
import com.example1.demo.binding.DashBoard;
import com.example1.demo.binding.SearchEnqData;
import com.example1.demo.entity.AddEnquiry;

public interface AddEnquiryService {

	
	public DashBoard getDashBoard(Integer userId) ;
	public List<String> getCourses();
	public List<String> getEnqStatus();
	public boolean saveEnquiry(AddEnqData data);
	public List<AddEnquiry> getEnquiries();
	public List<AddEnquiry> getFilterEnqs(SearchEnqData data,Integer userID);
	public AddEnqData getEnquiryById(Integer enqId);
	
}
