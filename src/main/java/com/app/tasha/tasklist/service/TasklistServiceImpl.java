package com.app.tasha.tasklist.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
		taskList.forEach(task -> {
			task.setFormattedTime(getFormattedTimestamp(task.getInsertDate()));
		});
		return taskList;
	}

	@Override
	public Task getTask(Long id) {
		Task task = tasklistDao.getTask(id);
		task.setFormattedTime(getFormattedTimestamp(task.getInsertDate()));
		return task;
	}
	
	private String getFormattedTimestamp(Timestamp insertionTime) {
		String dateFormat = "yyyy.MM.dd.HH.mm.ss";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formattedTime = sdf.format(insertionTime);
		return formattedTime;
	}


}
