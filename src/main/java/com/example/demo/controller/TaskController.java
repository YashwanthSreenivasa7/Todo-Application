package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.TaskDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.model.Task;
import com.example.demo.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/todo")
public class TaskController {	
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@PostMapping("/userRegistration")
	public ResponseEntity< Map<String, Object> > createTask(@Valid @RequestBody User emp) {
		String token=" ";
		emp.setToken(token);
		emp.setUpassword(userDAO.encode(emp.getUpassword()));
		 userDAO.save(emp);
		 HashMap<String, Object> map1 = new HashMap<>();
		 HashMap<String, Object> map2 = new HashMap<>();
		    map1.put("userId", emp.getUid());
		    map2.put("userDetails",map1);
		    return ResponseEntity.ok().body(map2);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/userLogin")
	public ResponseEntity< Map<String, Object> > getuserByUname(@Valid @RequestBody User emp){
		String uname=emp.getUname();
		String upassword=emp.getUpassword();
		User res=userDAO.findByUname(uname,upassword);
		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		if(res!=null) {
			String token=userDAO.generateToken(uname,upassword);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			res.setUname(emp.getUname());
			//res.setUpassword(emp.getUpassword());
			res.setUpassword(userDAO.encode(emp.getUpassword()));
			res.setToken(token);
			userDAO.save(res);
			map1.put("userId", res.getUid());
			map1.put("token", res.getToken());
			map2.put("userDetails",map1);
			return ResponseEntity.ok().body(map2);
		}
		   return ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/Logout/{id}")
	
	public ResponseEntity< Map<String, Object> > getUserLoggedOutById(@PathVariable(value="id") Long empid,@RequestHeader("token") String token){
		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		System.out.println("Getting tasks by ID....="+empid);
		User prev=userDAO.findbyUidandToken(empid,token);
		if(prev!=null) {
			System.out.println("user found loging out..");
			User res = userDAO.findbyUidandToken(empid, token);
			res.setUid(prev.getUid());
			res.setUname(prev.getUname());
			res.setUpassword(prev.getUpassword());
			res.setToken(" ");
			userDAO.save(res);
			map1.put("user", "LoggedOut");
			map2.put("user",map1);
			ResponseEntity.ok().body(map2);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping("/tasks")
	public ResponseEntity< Map<String, Object> > createTask(@Valid @RequestBody Task emp,@RequestHeader("token") String token) {
		//System.out.println("Pushing tasks id:"+emp.getUid());
		//System.out.println("Pushing tasks token:"+token);
		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		User res=userDAO.findbyUidandToken(emp.getUid(),token);
		//System.out.println("Pushing tasks id:"+res.getUid());
		//System.out.println("Pushing tasks token:"+res.getToken());
		if(res!=null) {
		taskDAO.save(emp);
		map1.put("taskAdded", "successfull");
		map2.put("TaskDetails",map1);
		ResponseEntity.ok().body(map2);	
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity< Map<String, Object> > getTaskById(@PathVariable(value="id") Long empid,@RequestHeader("token") String token){
		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		System.out.println("Getting tasks by ID....="+empid);
		System.out.println("Getting tasks by ID....="+token);
		User res=userDAO.findbyUidandToken(empid,token);
		if(res!=null) {
			System.out.println("sending tasks");
			List<Task> emp=taskDAO.findByUid(empid);
			map1.put("tasks",emp);
			return ResponseEntity.ok().body(map1);	
		}
		return ResponseEntity.notFound().build();
	}	

	/*update an task by tid*/
	@PutMapping("/tasks/{uid}/{tid}")
	public ResponseEntity< Map<String, Object> > updateTask(@PathVariable(value="uid") Long uid,@PathVariable(value="tid") Long tid,@Valid @RequestBody Task taskDetails,@RequestHeader("token") String token){

		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		User res=userDAO.findbyUidandToken(uid,token);
		if(res!=null && res.getUid().equals(uid)) {
			Task task=taskDAO.findOne(tid);
			if(task!=null && task.getUid().equals(uid)) {
				task.setTname(taskDetails.getTname());
				task.setTdesc(taskDetails.getTdesc());
				Task updateEmployee=taskDAO.save(task);
				map1.put("UPDATED","succesfull");
				map1.put("userId",uid);
				map1.put("tasks",tid);
				map2.put("Tasks",map1);
				return ResponseEntity.ok().body(map2);	
			}
			return ResponseEntity.notFound().build();
		}
		 return ResponseEntity.notFound().build();
	}
	
	/*Delete a Task*/
	@DeleteMapping("/tasks/{uid}/{tid}")
	public ResponseEntity< Map<String, Object> > deleteTask(@PathVariable(value="uid") Long uid,@PathVariable(value="tid") Long tid,@RequestHeader("token") String token){
		System.out.println("deleting..");
		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		User res=userDAO.findbyUidandToken(uid,token);
		if(res!=null && res.getUid().equals(uid)) {
			Task task=taskDAO.findbyid(tid);	
			if(task==null) {
				return ResponseEntity.notFound().build();
			}
			else if(task!=null && task.getUid().equals(uid)) {
				System.out.println("deleting.."+task.getTname());
			taskDAO.delete(task);
			map1.put("DELETE","succesfull");
			map1.put("userId",uid);
			map1.put("tasks",tid);
			}
			else {
				map1.put("DELETE","unSuccesfull");
			}
			map2.put("Tasks",map1);
			return ResponseEntity.ok().body(map2);	
		}
		return ResponseEntity.notFound().build();
	}
	

}