package com.app.tasha.tasklist.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.service.TasklistServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TasklistControllerTest {
	
	@InjectMocks
	private TasklistController tasklistController;
	@Mock
	private TasklistServiceImpl tasklistServiceMock;
	@Captor
	private ArgumentCaptor<Long> captorLong;
	@Captor
	private ArgumentCaptor<List<Task>> captorList;
	@Captor
	private ArgumentCaptor<Task> captorTask;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testGetTaskSummary() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("authorization", "BlahBlah");
		
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		
		Mockito.when(tasklistServiceMock.getAllTasks()).thenReturn(tasklist);
		
		//methodToTest
		ResponseEntity<List<Task>> response = tasklistController.getTaskSummary(requestHeaders);
		
		//validation
		Mockito.verify(tasklistServiceMock).getAllTasks();
		
		Assert.assertEquals(tasklist, response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetTaskDetail() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("authorization", "BlahBlah");
		
		long id = 101L;
		Task task = populateTasks(HttpMethod.GET).get(0);
		Mockito.when(tasklistServiceMock.getTask(Mockito.anyLong())).thenReturn(task);
		
		//methodToTest
		ResponseEntity<Object> response = tasklistController.getTaskDetail(requestHeaders, id);
		
		//validation
		Mockito.verify(tasklistServiceMock).getTask(captorLong.capture());
		Assert.assertEquals(id, captorLong.getValue(), 1);
		
		Assert.assertEquals(task, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetTaskDetailWithNull() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		Long id = null;
		
		//methodToTest
		ResponseEntity<Object> response = tasklistController.getTaskDetail(requestHeaders, id);
		
		//validation
		Assert.assertEquals("Tasklist Id should not be null", response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testCreateTask() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("authorization", "BlahBlah");
		
		List<Task> tasklist = populateTasks(HttpMethod.POST);
		Mockito.when(tasklistServiceMock.createTask(Mockito.anyList())).thenReturn(tasklist);
		
		//methodToTest
		ResponseEntity<List<Task>> response = tasklistController.createTask(requestHeaders, tasklist);
		
		//validation
		Mockito.verify(tasklistServiceMock).createTask(captorList.capture());
		Assert.assertEquals(tasklist, captorList.getValue());
		Assert.assertNull(tasklist.get(0).getId());
		
		Assert.assertEquals(tasklist, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void testUpdateTask() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("authorization", "BlahBlah");
		
		List<Task> tasklist = populateTasks(HttpMethod.PUT);
		Mockito.when(tasklistServiceMock.updateTask(Mockito.anyList())).thenReturn(tasklist);
		
		//methodToTest
		ResponseEntity<List<Task>> response = tasklistController.updateTask(requestHeaders, tasklist);
		
		//validation
		Mockito.verify(tasklistServiceMock).updateTask(captorList.capture());
		Assert.assertEquals(tasklist, captorList.getValue());
		Assert.assertNotNull(tasklist.get(0).getId());
		
		Assert.assertEquals(tasklist, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testDeleteTask() {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("authorization", "BlahBlah");
		
		Task task = populateTasks(HttpMethod.GET).get(0);
		String deleteStatus = "Record deleted successfully";
		Mockito.when(tasklistServiceMock.deleteTask(Mockito.any())).thenReturn(deleteStatus);
		
		//methodToTest
		ResponseEntity<Object> response = tasklistController.deleteTask(requestHeaders, task.getId(), task);
		
		//validation
		Mockito.verify(tasklistServiceMock).deleteTask(captorTask.capture());
		Assert.assertEquals(task, captorTask.getValue());
		
		Assert.assertEquals(deleteStatus, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetPendingTasks() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		Mockito.when(tasklistServiceMock.getPendingTasks()).thenReturn(tasklist);
		
		//methodToTest
		ResponseEntity<List<Task>> response = tasklistController.getPendingTasks(requestHeaders);
		
		//validation
		Assert.assertEquals(tasklist, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetCompletedTasks() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		Mockito.when(tasklistServiceMock.getCompletedTasks()).thenReturn(tasklist);
		
		//methodToTest
		ResponseEntity<List<Task>> response = tasklistController.getCompletedTasks(requestHeaders);
		
		//validation
		Assert.assertEquals(tasklist, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetTasksByDate() throws Exception {
		//init
		Map<String, String> requestHeaders = new HashMap<String, String>();
		String taskDate = "2020-09-23";
		
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		Mockito.when(tasklistServiceMock.getTasksByDate(Mockito.anyString())).thenReturn(tasklist);
		
		//methodToTest
		ResponseEntity<List<Task>> response = tasklistController.getTasksByDate(requestHeaders, taskDate);
		
		//validation
		Assert.assertEquals(tasklist, response.getBody());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	private List<Task> populateTasks(HttpMethod method) {
		List<Task> tasks = new ArrayList<Task>();
		Date today = new Date(System.currentTimeMillis());
		
		Task task1 = new Task(101L, "Laundry", "Put cloths in machine", "Home", today, "New");
		tasks.add(task1);
		Task task2 = new Task(102L, "Breakfast", "Prepare breakfast", "Home", today, "Pending");
		tasks.add(task2);
		if(method.equals(HttpMethod.POST)) {
			task1.setId(null);
			task2.setId(null);
		}
		return tasks;
	}
}
