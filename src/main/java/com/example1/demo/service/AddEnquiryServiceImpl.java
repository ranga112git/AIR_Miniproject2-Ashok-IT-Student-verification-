package com.example1.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example1.demo.binding.AddEnqData;
import com.example1.demo.binding.DashBoard;
import com.example1.demo.binding.SearchEnqData;
import com.example1.demo.entity.AddEnquiry;
import com.example1.demo.entity.CoursesEntity;
import com.example1.demo.entity.EnqStatus;
import com.example1.demo.entity.UserEntity;
import com.example1.demo.repo.AddEnquiryRepo;
import com.example1.demo.repo.CoursesRepo;
import com.example1.demo.repo.EnqStatusRepo;
import com.example1.demo.repo.UserRepo;

@Service
public class AddEnquiryServiceImpl implements AddEnquiryService {
  @Autowired
	UserRepo Userrepo;
  @Autowired
  private CoursesRepo coursesrepo;
  @Autowired
  private EnqStatusRepo statusRepo;
  @Autowired
  private HttpSession session;
  @Autowired
  private AddEnquiryRepo addenq;
  
	
	@Override
	public DashBoard getDashBoard(Integer userId) {
		 DashBoard response=new DashBoard();
		
	Optional<UserEntity> entity=Userrepo.findById(userId);
	if(entity.isPresent()) {
	       UserEntity userEntity=entity.get();
	            List<AddEnquiry>   enquiries= userEntity.getEnquiries();
	
	     Integer total=(int) enquiries.size();
	  Integer enrolled  = (int) enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled")).collect(Collectors.toList()).size();
	  Integer lost  = (int) enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost")).collect(Collectors.toList()).size();
	  response.setTotalCnt(total);
	  response.setEnrolledCnt(enrolled);
	  response.setLostCnt(lost);
	}
	
	
	
		return response;
	}

	@Override
	public List<String> getCourses() {
	List<CoursesEntity>	findAll=coursesrepo.findAll();
	List<String> names=new ArrayList<String>();
	
	for(CoursesEntity entity:findAll) {
		names.add(entity.getCourses());
	}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnqStatus>	findAll=statusRepo.findAll();
		List<String> statusList=new ArrayList<String>();
		
		for(EnqStatus entity:findAll) {
			statusList.add(entity.getEnqStatus());
		}
		return statusList;
	}

	@Override
	public boolean saveEnquiry(AddEnqData data) {
		
		 AddEnquiry addData = new AddEnquiry();
		    
		    if (data.getAddId() != null) {
		        // Editing existing enquiry
		        Optional<AddEnquiry> existing = addenq.findById(data.getAddId());
		        if (existing.isPresent()) {
		            addData = existing.get(); // use existing entity
		        }
		    }

		    BeanUtils.copyProperties(data, addData, "addId", "user", "dateCreated", "lastUpdated");
		
		
		
		
		Integer userId= (Integer) session.getAttribute("id");
		
		if(userId==null) {
			return false;
		}
	Optional<UserEntity> en=Userrepo.findById(userId);
	
	
	if(!en.isPresent()) {
		return false;
		
	}
	
	UserEntity e1=en.get();
	addData.setUser(e1);
	
		addenq.save(addData);
		
		return true;
	}

	
	public List<AddEnquiry> getEnquiries() {
	Integer	userId=(Integer)session.getAttribute("id");
    Optional<UserEntity>	entity=Userrepo.findById(userId);
    if(entity.isPresent()) {
    UserEntity	en=entity.get();
    List<AddEnquiry>    enquiries=en.getEnquiries();
    return enquiries;
    }



		return null;
	}

	
	public List<AddEnquiry> getFilterEnqs(SearchEnqData data, Integer userID) {
		Integer	userId=(Integer)session.getAttribute("id");
	    Optional<UserEntity>	entity=Userrepo.findById(userId);
	    if(entity.isPresent()) {
	    UserEntity	en=entity.get();
	    List<AddEnquiry>    enquiries=en.getEnquiries();
	    
	    
	    if(null!=data.getCourseName() && !"".equals(data.getCourseName())) {
	    	enquiries	=enquiries.stream().filter(e-> e.getCourseName().equals(data.getCourseName())).collect(Collectors.toList());
	    }
	    if(null!=data.getClassMode() && !"".equals(data.getClassMode())) {
	    	enquiries=enquiries.stream().filter(e-> e.getClassMode().equals(data.getClassMode())).collect(Collectors.toList());
	    }
	    if(null!=data.getEnqStatus() && !"".equals(data.getEnqStatus())) {
	    	enquiries=	enquiries.stream().filter(e-> e.getEnqStatus().equals(data.getEnqStatus())).collect(Collectors.toList());
	    }
	    return enquiries;
	    }
		
		return null;
	}

	
	

	
	public AddEnqData getEnquiryById(Integer enqId) {
		 Optional<AddEnquiry> optional = addenq.findById(enqId);

		    if (optional.isPresent()) {
		        AddEnquiry entity = optional.get();
		        AddEnqData data = new AddEnqData();
		        BeanUtils.copyProperties(entity, data);
		        return data;
		    }
		    return null;
	}

}
