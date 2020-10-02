package com.app.tasha.tasklist.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import com.app.tasha.tasklist.dao.TasklistDao;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.utils.TasklistConstants;

@RunWith(JUnit4.class)
public class TasklistServiceTest {
	@InjectMocks
	private TasklistServiceImpl tasklistService;
	@Mock
	private TasklistDao tasklistDaoMock;
	@Captor
	private ArgumentCaptor<Long> captorLong;
	@Captor
	private ArgumentCaptor<String> captorString;
	@Captor
	private ArgumentCaptor<Date> captorDate;
	@Captor
	private ArgumentCaptor<Task> captorTask;
	@Captor
	private ArgumentCaptor<List<Long>> captorList;
	
	@SuppressWarnings("deprecation")
	@Before 
	public void setUp() { 
		MockitoAnnotations.initMocks(this); 
	}
	
	@Test
	public void testGetAllTasks() throws Exception {
		//init
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		Mockito.when(tasklistDaoMock.getAllTasks()).thenReturn(tasklist);
		
		//methodToTest
		List<Task> response = tasklistService.getAllTasks();
		
		//validation
		Assert.assertEquals(tasklist, response);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.get(0).getInsertDateUI());
	}
	
	@Test
	public void testGetTask() throws Exception{
		//init
		Long taskId = 101L;
		Task task = populateTasks(HttpMethod.GET).get(0);
		Mockito.when(tasklistDaoMock.getTask(Mockito.anyLong())).thenReturn(task);
		
		//methodToTest
		Task response = tasklistService.getTask(taskId);
		
		//validation
		Mockito.verify(tasklistDaoMock).getTask(captorLong.capture());
		Assert.assertEquals(taskId, captorLong.getValue());
		
		Assert.assertEquals(task, response);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getInsertDateUI());
	}
	
	@Test
	public void testCreateTask() throws Exception {
		//init
		Task task = populateTasks(HttpMethod.GET).get(0);
		Mockito.when(tasklistDaoMock.createTask(Mockito.any(Task.class))).thenReturn(task);
		
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		
		//methodToTest
		List<Task> response = tasklistService.createTask(tasklist);
		
		//validation
		Mockito.verify(tasklistDaoMock, Mockito.atMost(5)).createTask(captorTask.capture());
		Assert.assertNotNull(captorTask);
		
		Assert.assertEquals(tasklist.get(0).getTitle(), response.get(0).getTitle());
		Assert.assertEquals(tasklist.get(0).getCategory(), response.get(0).getCategory());
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.get(0).getInsertDateUI());
	}
	
	@Test
	public void testUpdateTask() throws Exception {
		//init
		Task task = populateTasks(HttpMethod.GET).get(0);
		Mockito.when(tasklistDaoMock.updateTask(Mockito.any(Task.class))).thenReturn(task);
		
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		
		//methodToTest
		List<Task> response = tasklistService.updateTask(tasklist);
		
		//validation
		Mockito.verify(tasklistDaoMock, Mockito.atMost(5)).updateTask(captorTask.capture());
		Assert.assertNotNull(captorTask);
		
		Assert.assertEquals(tasklist.get(0).getTitle(), response.get(0).getTitle());
		Assert.assertEquals(tasklist.get(0).getCategory(), response.get(0).getCategory());
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.get(0).getInsertDateUI());
	}
	
	@Test
	public void testDeleteTaskWithSuccess() {
		//init 
		Task task = populateTasks(HttpMethod.GET).get(0);
		Mockito.when(tasklistDaoMock.deleteTask(Mockito.anyLong())).thenReturn(TasklistConstants.TASK_DELETE_SUCCESS);
		
		//methodToTest
		String response = tasklistService.deleteTask(task);
		//validate
		Mockito.verify(tasklistDaoMock).deleteTask(captorLong.capture());
		Assert.assertNotNull(captorLong);
		Assert.assertEquals(task.getId(), captorLong.getValue());
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response, TasklistConstants.TASK_DELETE_SUCCESS);
	}
	
	@Test
	public void testDeleteTaskWithFail() {
		//init 
		Task task = populateTasks(HttpMethod.GET).get(0);
		Mockito.when(tasklistDaoMock.deleteTask(Mockito.anyLong())).thenReturn(TasklistConstants.TASK_DELETE_FAILURE);
		
		//methodToTest
		String response = tasklistService.deleteTask(task);
		
		//validate
		Mockito.verify(tasklistDaoMock).deleteTask(captorLong.capture());
		Assert.assertNotNull(captorLong);
		Assert.assertEquals(task.getId(), captorLong.getValue());
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response, TasklistConstants.TASK_DELETE_FAILURE);
	}
	
	@Test
	public void testGetPendingTasks() {
		//init
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		tasklist.get(0).setStatus(TasklistConstants.TASK_STATUS_PENDING);
		
		Mockito.when(tasklistDaoMock.getPendingTasks(Mockito.anyString())).thenReturn(tasklist);
		
		//methodToTest
		List<Task> response = tasklistService.getPendingTasks();
		
		//validate
		Mockito.verify(tasklistDaoMock).getPendingTasks(captorString.capture());
		Assert.assertNotNull(captorString);
		Assert.assertEquals(TasklistConstants.TASK_STATUS_PENDING, captorString.getValue());
		
		Assert.assertEquals(tasklist, response);
		Assert.assertNotNull(response);
		Assert.assertEquals(TasklistConstants.TASK_STATUS_PENDING, response.get(0).getStatus());
		Assert.assertNotNull(response.get(0).getInsertDateUI());
	}
	
	@Test
	public void testGetCompletedTasks() {
		//init
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		tasklist.get(0).setStatus(TasklistConstants.TASK_STATUS_COMPLETE);

		Mockito.when(tasklistDaoMock.getCompletedTasks(Mockito.anyString())).thenReturn(tasklist);

		//methodToTest
		List<Task> response = tasklistService.getCompletedTasks();

		//validate
		Mockito.verify(tasklistDaoMock).getCompletedTasks(captorString.capture());
		Assert.assertNotNull(captorString);
		Assert.assertEquals(TasklistConstants.TASK_STATUS_COMPLETE, captorString.getValue());

		Assert.assertEquals(tasklist, response);
		Assert.assertNotNull(response);
		Assert.assertEquals(TasklistConstants.TASK_STATUS_COMPLETE, response.get(0).getStatus());
		Assert.assertNotNull(response.get(0).getInsertDateUI());
	}
	
	@Test
	public void testGetTasksByDate() {
		//init
		String insertDate = "2020-10-02";
		List<Task> tasklist = populateTasks(HttpMethod.GET);
		
		Mockito.when(tasklistDaoMock.getTasksByDate(Mockito.any())).thenReturn(tasklist);

		//methodToTest
		List<Task> response = tasklistService.getTasksByDate(insertDate);

		//validate
		Mockito.verify(tasklistDaoMock).getTasksByDate(captorDate.capture());
		Assert.assertNotNull(captorDate);
		Assert.assertEquals(insertDate, captorDate.getValue().toString());
		
		Assert.assertEquals(tasklist, response);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.get(0).getInsertDateUI());
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
