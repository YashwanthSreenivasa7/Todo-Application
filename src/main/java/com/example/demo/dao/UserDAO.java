package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



@Service

public class UserDAO {
	@Autowired
	UserRepository userRepository;
	
	/*to save an employee*/
	
	public User save(User emp) {
		return userRepository.save(emp);
	}
	
	
	/* search all employees*/
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	/*
	public List<Task> findByUid(Long id){
		return taskRepository.findByuid(id);
	}*/
	
	/*get an employee by id*/
	
	public User findOne(Long Id) {
		Long x=(long) 2;
		System.out.println("In DAO="+Id);
		return userRepository.getOne(Id);
	}
	
	/*delete an employee*/
	
	public void delete(User emp) {
		userRepository.delete(emp);
	}

	
	public List<User> findByUname(String uname,String upassword) {
		// TODO Auto-generated method stub
		return userRepository.findByunameAndUpassword(uname,upassword);
	}
}
