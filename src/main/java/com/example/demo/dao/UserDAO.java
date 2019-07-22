package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



@Service

public class UserDAO {
	@Autowired
	UserRepository userRepository;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	/*to save an employee*/
	
	
	public String encode(String str) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(str);
	}
	
	public User save(User emp) {
		System.out.println("Pushing users");
		return userRepository.save(emp);
	}
	
	public String generateToken(String uname,String upassword)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(uname+upassword);
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
		System.out.println("In DAO="+Id);
		return userRepository.getOne(Id);
	}
	
	
	
	/*delete an employee*/
	
	public void delete(User emp) {
		userRepository.delete(emp);
	}

	
	public User findByUname(String uname,String upassword) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//upassword=passwordEncoder.encode(upassword);
		System.out.println("Getting tasks by UserName....="+upassword);
		//$2a$10$K7moL8yg58nyIYcSqs5ZjeXuBer/ZzknilE7OuPhmqkXUkH4IQ.Wy
		List<User> users= userRepository.findByuname(uname);
		if(users.size()>0) {
		for(int i=0;i<users.size();i++) {
			users.get(i);
			if(passwordEncoder.matches(upassword,users.get(i).getUpassword())){
				System.out.println("Getting tasks by UserName with password matched....="+upassword);
				//System.out.println("Getting tasks by UserName with password matched....="+users.get(i).get);
				return users.get(i);
			}
		}
		} 
		User user1 = null;
		return user1;
	}
	public User findbyUidandToken(Long id,String token) {
		List<User> users= userRepository.findByUidAndToken(id,token);
		if(users.size()>0) {
			return users.get(0);
			} 
			User user1 = null;
			return user1;
	}
}
