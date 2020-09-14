package com.app.tasha.tasklist.service;

import java.util.List;
import com.app.tasha.tasklist.model.Task;

public interface TasklistService {
	
	 public List<Task> getAllTasks() throws Exception;
	 
	 public Task getTask(Long id) throws Exception;
	 
	 public List<Task> createTask(List<Task> tasklist) throws Exception;
	 
	 public List<Task> updateTask(List<Task> tasklist) throws Exception;
	 
	 public String deleteTask(Task task);
}
