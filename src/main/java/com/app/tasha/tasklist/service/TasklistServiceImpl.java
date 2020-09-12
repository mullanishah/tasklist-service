package com.app.tasha.tasklist.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.tasha.tasklist.dao.TasklistDao;
import com.app.tasha.tasklist.pojo.Task;

@Service
public class TasklistServiceImpl implements TasklistService {
	
	@Autowired
	private TasklistDao tasklistDao;
	
	@Override
	public List<Task> getAllTasks() {
		List<Task> taskList = (List<Task>) tasklistDao.getAllTasks();
		return taskList;
	}

	@Override
	public Task getTask(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
