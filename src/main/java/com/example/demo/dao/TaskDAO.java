package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;

@Service

public class TaskDAO {
	@Autowired
	TaskRepository taskRepository;
	
	/*to save an employee*/
	
	public Task save(Task emp) {
		return taskRepository.save(emp);
	}
	
	
	/* search all employees*/
	
	public List<Task> findAll(){
		return taskRepository.findAll();
	}
	
	/*
	public List<Task> findByUid(Long id){
		return taskRepository.findByuid(id);
	}*/
	
	/*get an employee by id*/
	
	public Task findOne(Long Id) {
		Long x=(long) 2;
		System.out.println("In DAO="+Id);
		return taskRepository.getOne(Id);
	}
	
	/*delete an employee*/
	
	public void delete(Task emp) {
		taskRepository.delete(emp);
	}


	public List<Task> findByUid(Long empid) {
		// TODO Auto-generated method stub
		return taskRepository.findByuid(empid);
	}
}
