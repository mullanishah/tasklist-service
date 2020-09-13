package com.app.tasha.tasklist.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.service.TasklistService;

@RestController
@RequestMapping(value = "tasklist")
public class TasklistController {
	
	@Autowired
	private TasklistService tasklistService;
	
	@RequestMapping(method = RequestMethod.GET, value = "summary")
	public ResponseEntity<List<Task>> getTaskSummary(@RequestHeader Map<String, String> requestHeaders) throws Exception{
		
		return new ResponseEntity<List<Task>>(tasklistService.getAllTasks(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "hello")
	public ResponseEntity<String> testApi(){
		return ResponseEntity.ok("Hello from boot");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "task/{id}")
	public ResponseEntity<Task> getTaskDetail(@RequestHeader Map<String, String> requestHeaders,
							@PathVariable("id") long id) throws Exception{
		Task task = tasklistService.getTask(id);
		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "task")
	private ResponseEntity<List<Task>> createTask(@RequestHeader Map<String, String> requestHeaders,
							@RequestBody List<Task> tasklist) throws Exception{
		List<Task> tasks = tasklistService.createTask(tasklist);
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "task")
	public ResponseEntity<List<Task>> updateTask(@RequestHeader Map<String, String> requestHeaders,
			 			   @RequestBody List<Task> tasklist) throws Exception{
		List<Task> tasks = tasklistService.updateTask(tasklist);
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.NO_CONTENT);
	}
}
