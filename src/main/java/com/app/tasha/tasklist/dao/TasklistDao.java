package com.app.tasha.tasklist.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.app.tasha.tasklist.model.Task;

public interface TasklistDao {
	
	public Collection<Task> getAllTasks() throws Exception;
	
	public Task getTask(Long id) throws Exception;
	
	public Task createTask(Task task);
	
	public Task updateTask(Task task);
	
	public String deleteTask(long id);
	
	public List<Task> getPendingTasks(String status);
	
	public List<Task> getCompletedTasks(String status);
	
	public List<Task> getTasksByDate(Date date);
}
