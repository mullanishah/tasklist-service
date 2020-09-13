package com.app.tasha.tasklist.dao;

import java.util.Collection;

import com.app.tasha.tasklist.model.Task;

public interface TasklistDao {
	public Collection<Task> getAllTasks() throws Exception;
	public Task getTask(Long id) throws Exception;
	public Task createTask(Task task);
	public Task updateTask(Task task);
}
