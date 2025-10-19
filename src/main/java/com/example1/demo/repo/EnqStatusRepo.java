package com.example1.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example1.demo.entity.EnqStatus;
@Repository
public interface EnqStatusRepo extends JpaRepository<EnqStatus, Integer> {

}
