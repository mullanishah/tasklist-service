package com.app.tasha.tasklist.dao;

import java.util.Collection;
import com.app.tasha.tasklist.pojo.Task;

public interface TasklistDao {
	public Collection<Task> getAllTasks();
	public Task getTask(Long id);
}
