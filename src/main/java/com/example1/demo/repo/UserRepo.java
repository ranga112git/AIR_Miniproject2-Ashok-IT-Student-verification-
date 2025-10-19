package com.example1.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example1.demo.entity.UserEntity;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {

     public UserEntity findByEmail(String email);
     public UserEntity findByEmailAndPassword(String email,String password);
}
