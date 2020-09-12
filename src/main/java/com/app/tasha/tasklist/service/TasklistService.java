package com.app.tasha.tasklist.service;

import java.util.List;
import com.app.tasha.tasklist.pojo.Task;

public interface TasklistService {
	
	 public List<Task> getAllTasks();
	 public Task getTask(Long id);
}
