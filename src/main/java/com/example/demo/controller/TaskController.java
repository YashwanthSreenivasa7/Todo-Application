package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.demo.dao.UserDAO;
import com.example.demo.model.Employee;
import com.example.demo.model.Task;
import com.example.demo.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/todo")
public class TaskController {
	
	@Autowired
	EmployeeDAO employeeDAO;
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@PostMapping("/post/tasks")
	public Task createTask(@Valid @RequestBody Task emp) {
		System.out.println("Pushing tasks");
		return taskDAO.save(emp);
	}
	
	@PostMapping("/post/user")
	public User createTask(@Valid @RequestBody User emp) {
		System.out.println("Pushing tasks");
		return userDAO.save(emp);
	}
	
	
	/*get all test*/
	@GetMapping("/get/test")
	public String test(){
		System.out.println("Getting tasks");
		return "test madi";
	}
	
	/*get all tasks*/
	@GetMapping("/get/tasks")
	public List<Task> getAllTasks(){
		System.out.println("Getting tasks");
		return taskDAO.findAll();
	}
	
	/*get employee by uname and upassword*/
	@GetMapping("/get/users/{id}")
	public List<User> getuserById(@PathVariable(value="id") Long empid){
		System.out.println("Getting tasks by ID....="+empid);
		List<User> emp=(List<User>) userDAO.findOne(empid);
		//System.out.println("Getting emplyees by ID....="+emp);
		return emp;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/get/user/{uname}/{pass}")
	public List<User> getuserByUname(@PathVariable(value="uname") String uname,@PathVariable(value="pass") String pass){
		System.out.println("Getting tasks by UserName....="+uname);
		List<User> emp=userDAO.findByUname(uname,pass);
		//System.out.println("Getting emplyees by ID....="+emp);
		return emp;
	}
	
	
	/*get employee by empid*/
	@GetMapping("/get/tasks/{id}")
	public List<Task> getTaskById(@PathVariable(value="id") Long empid){
		System.out.println("Getting tasks by ID....="+empid);
		List<Task> emp=taskDAO.findByUid(empid);
		//System.out.println("Getting emplyees by ID....="+emp);
		return emp;
	}
	
	/*get employee by empid*/
	@GetMapping("/get/employees/{id}")
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
	@PutMapping("/put/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable(value="id") Long empid,@Valid @RequestBody Task taskDetails){
		Task emp=taskDAO.findOne(empid);
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		
		emp.setTname(taskDetails.getTname());
		emp.setTdesc(taskDetails.getTdesc());
		
		Task updateEmployee=taskDAO.save(emp);
		return ResponseEntity.ok().body(updateEmployee);	
	}
	
	/*Delete an employee*/
	@DeleteMapping("/delete/task/{id}")
	public ResponseEntity<Task> deleteTask(@PathVariable(value="id") Long empid){
		
		Task emp=taskDAO.findOne(empid);
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		taskDAO.delete(emp);
		
		return ResponseEntity.ok().build();	
	}
	
	

}