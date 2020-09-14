package com.app.tasha.tasklist.service;

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
			task.setInsertDateUI(TasklistUtils.formatInsertDate(task.getInsertDate()));
		});
		return tasklist;
	}

	@Override
	public Task getTask(Long taskId) throws Exception {
		Task task = tasklistDao.getTask(taskId);
		task.setInsertDateUI(TasklistUtils.formatInsertDate(task.getInsertDate()));
		return task;
	}
	
	@Override
	public List<Task> createTask(List<Task> tasklist) throws Exception {
		List<Task> tasks = new ArrayList<Task>(); 
		tasklist.forEach(task -> {
			Task taskDetail = tasklistDao.createTask(task);
			taskDetail.setInsertDateUI(TasklistUtils.formatInsertDate(task.getInsertDate()));
			tasks.add(taskDetail);
		});
		return tasks;
	}

	@Override
	public List<Task> updateTask(List<Task> tasklist) throws Exception {
		List<Task> tasks = new ArrayList<Task>();  
		tasklist.forEach(task -> {
			Task taskDetail = tasklistDao.updateTask(task);
			taskDetail.setInsertDateUI(TasklistUtils.formatInsertDate(task.getInsertDate()));
			tasks.add(taskDetail);
		});
		return tasks;
	}

	@Override
	public String deleteTask(Task task) {
		String status = tasklistDao.deleteTask(task.getId());
		return status;
	}

}
