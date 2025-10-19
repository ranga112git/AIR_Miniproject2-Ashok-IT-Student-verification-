package com.example1.demo.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example1.demo.entity.CoursesEntity;
import com.example1.demo.entity.EnqStatus;
import com.example1.demo.repo.CoursesRepo;
import com.example1.demo.repo.EnqStatusRepo;
@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private CoursesRepo courseRepo;
	@Autowired
	private EnqStatusRepo statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepo.deleteAll();
		CoursesEntity c1=new CoursesEntity();
		c1.setCourses("java");
		
		CoursesEntity c2=new CoursesEntity();
		c2.setCourses("phython");
		CoursesEntity c3=new CoursesEntity();
		c3.setCourses("devops");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		
		statusRepo.deleteAll();
		
		EnqStatus e1=new EnqStatus();
		e1.setEnqStatus("New");
		
		EnqStatus e2=new EnqStatus();
		e2.setEnqStatus("Enrolled");
		EnqStatus e3=new EnqStatus();
		e3.setEnqStatus("Lost");
		
		statusRepo.saveAll(Arrays.asList(e1,e2,e3));
	}

}
