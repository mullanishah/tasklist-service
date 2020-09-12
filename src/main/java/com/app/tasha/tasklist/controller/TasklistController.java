package com.app.tasha.tasklist.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.app.tasha.tasklist.pojo.Task;
import com.app.tasha.tasklist.service.TasklistService;

@RestController
@RequestMapping(value = "tasklist")
public class TasklistController {
	
	@Autowired
	private TasklistService tasklistService;
	
	@RequestMapping(method = RequestMethod.GET, value = "summary")
	public ResponseEntity<List<Task>> getTaskSummary(@RequestHeader Map<String, String> requestHeaders) {
		
		return new ResponseEntity<List<Task>>(tasklistService.getAllTasks(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "hello")
	public ResponseEntity<String> testApi(){
		return ResponseEntity.ok("Hello from boot");
	}

}
