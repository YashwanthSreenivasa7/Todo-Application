package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.dao.TaskDAO;
import com.example.demo.model.Employee;
import com.example.demo.model.Task;

@RestController
@RequestMapping("/company")
public class EmployeeController {
	
	@Autowired
	EmployeeDAO employeeDAO;
	
	@Autowired
	TaskDAO taskDAO;
	
	/* to save an employee*/
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee emp) {
		System.out.println("Pushing emplyees");
		return employeeDAO.save(emp);
	}
	
	@PostMapping("/tasks")
	public Task createTask(@Valid @RequestBody Task emp) {
		System.out.println("Pushing tasks");
		return taskDAO.save(emp);
	}
	
	
	/*get all test*/
	@GetMapping("/test")
	public String test(){
		System.out.println("Getting emplyees");
		return "test madi";
	}
	
	
	/*get all employees*/
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		System.out.println("Getting emplyees");
		return employeeDAO.findAll();
	}
	
	/*get all tasks*/
	@GetMapping("/tasks")
	public List<Task> getAllTasks(){
		System.out.println("Getting tasks");
		return taskDAO.findAll();
	}
	
	
	
	/*get employee by empid*/
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") Long empid){
		System.out.println("Getting emplyees by ID....="+empid);
		Employee emp=employeeDAO.findOne(empid);
		System.out.println("Getting emplyees by ID....="+emp);
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(emp);
	}
	
	
	/*update an employee by empid*/
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long empid,@Valid @RequestBody Employee empDetails){
		Employee emp=employeeDAO.findOne(empid);
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		
		emp.setName(empDetails.getName());
		emp.setDesignation(empDetails.getDesignation());
		emp.setExpertise(empDetails.getExpertise());
		
		Employee updateEmployee=employeeDAO.save(emp);
		return ResponseEntity.ok().body(updateEmployee);	
	}
	
	/*Delete an employee*/
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable(value="id") Long empid){
		
		Employee emp=employeeDAO.findOne(empid);
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		employeeDAO.delete(emp);
		
		return ResponseEntity.ok().build();	
	}
	
	

}