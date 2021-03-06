package com.app.tasha.tasklist.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.service.TasklistService;

@RestController
@RequestMapping(value = "tasklist")
public class TasklistController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TasklistController.class);
	
	@Autowired
	private TasklistService tasklistService;
	
	@RequestMapping(method = RequestMethod.GET, value = "summary")
	public ResponseEntity<List<Task>> getTaskSummary(@RequestHeader Map<String, String> requestHeaders) throws Exception{
		
		LOG.info("event=FETCH_TASK_SUMMARY, requestId= ");
		List<Task> tasklist = tasklistService.getAllTasks();
		return new ResponseEntity<List<Task>>(tasklist, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "task/{id}")
	public ResponseEntity<Object> getTaskDetail(@RequestHeader Map<String, String> requestHeaders,
								@PathVariable("id") Long id) throws Exception{
		
		LOG.info("event=FETCH_TASK_ON_TASK_ID, TASK_ID= " + id);
		if(id == null) {
			return new ResponseEntity<Object>("Tasklist Id should not be null", HttpStatus.OK);
		}
		Task task = tasklistService.getTask(id);
		return new ResponseEntity<Object>(task, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "task")
	public ResponseEntity<List<Task>> createTask(@RequestHeader Map<String, String> requestHeaders,
								@RequestBody List<Task> tasklist) throws Exception{
		
		LOG.info("event=CREATE_TASK_AND_RETURN_WITH_GENERATED_ID, TASK_ID= " + tasklist.get(0).getId());
		List<Task> tasks = tasklistService.createTask(tasklist);
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "task")
	public ResponseEntity<List<Task>> updateTask(@RequestHeader Map<String, String> requestHeaders,
								@RequestBody List<Task> tasklist) throws Exception{
		
		LOG.info("event=UPDATE_TASK_AND_RETURN_UPDATED_TASK, TASK_ID= " + tasklist.get(0).getId());
		List<Task> tasks = tasklistService.updateTask(tasklist);
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "task/delete")
	public ResponseEntity<Object> deleteTask(@RequestHeader Map<String, String> requestHeaders, 
											 @RequestParam(value = "taskId", required = true) long taskId,
											 @RequestBody(required = true) Task task) {
		
		LOG.info("event=DELETE_TASK_AND_RETURN_STATUS, TASK_ID= " + task.getId());
		String deleteStatus = tasklistService.deleteTask(task);
		return new ResponseEntity<Object>(deleteStatus, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "pending", 
								consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> getPendingTasks(@RequestHeader Map<String, String> requestHeaders) {
		
		LOG.info("event=GET_PENDING_TASKS");
		List<Task> tasklist = tasklistService.getPendingTasks();
		return ResponseEntity.ok(tasklist);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "complete", 
								consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> getCompletedTasks(@RequestHeader Map<String, String> requestHeaders) {
		
		LOG.info("event=GET_COMPLETED_TASKS");
		List<Task> tasklist = tasklistService.getCompletedTasks();
		return ResponseEntity.ok(tasklist);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "task-by-date", 
								consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> getTasksByDate(@RequestHeader Map<String, String> requestHeaders, 
							@RequestParam(value = "date", required = true) String taskDate) {
		
		LOG.info("event=GET_TASKS_BY_DATE, Requested Task Date=" + taskDate);
		List<Task> tasklist = tasklistService.getTasksByDate(taskDate);
		return ResponseEntity.ok(tasklist);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "hello")
	public ResponseEntity<String> testApi(){
		
		return ResponseEntity.ok("Hello from boot");
	}
}
