package com.example1.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EnqStatus {

	@Id
	@GeneratedValue
	private Integer enqId;
	private String enqStatus;
	public Integer getEnqId() {
		return enqId;
	}
	public void setEnqId(Integer enqId) {
		this.enqId = enqId;
	}
	public String getEnqStatus() {
		return enqStatus;
	}
	public void setEnqStatus(String enqStatus) {
		this.enqStatus = enqStatus;
	}
	
	
	
}
