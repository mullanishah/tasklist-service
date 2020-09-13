package com.app.tasha.tasklist.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.tasha.tasklist.dao.TasklistDao;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.utils.TasklistUtils;

@Service
public class TasklistServiceImpl implements TasklistService {
	
	@Autowired
	private TasklistDao tasklistDao;
	
	@Override
	public List<Task> getAllTasks() throws Exception {
		List<Task> tasklist = (List<Task>) tasklistDao.getAllTasks();
		tasklist.forEach(task -> {
			task.setInsertDateFormatted(TasklistUtils.formatInsertDate(task.getInsertDate()));
		});
		return tasklist;
	}

	@Override
	public Task getTask(Long taskId) throws Exception {
		Task task = tasklistDao.getTask(taskId);
		task.setInsertDateFormatted(TasklistUtils.formatInsertDate(task.getInsertDate()));
		return task;
	}
	
	@Override
	public List<Task> createTask(List<Task> tasklist) throws Exception {
		List<Task> tasks = new ArrayList<Task>(); 
		tasklist.forEach(task -> {
			//task.setInsertDate(TasklistUtils.parseInsertDate(task.getInsertDateFormatted()));
			task.setInsertDate(new Date(System.currentTimeMillis()));
			Task taskDetail = tasklistDao.createTask(task);
			tasks.add(taskDetail);
		});
		return tasks;
	}

	@Override
	public List<Task> updateTask(List<Task> tasklist) throws Exception {
		List<Task> tasks = new ArrayList<Task>(); 
		tasklist.forEach(task -> {
			Task taskDetail = tasklistDao.updateTask(task);
			tasks.add(taskDetail);
		});
		return tasks;
	}

}
