package com.app.tasha.tasklist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Repository;
import com.app.tasha.tasklist.pojo.Task;
import com.app.tasha.tasklist.utils.DatabaseUtils;
import com.app.tasha.tasklist.utils.TasklistConstants;

@Repository
public class TasklistDaoImpl implements TasklistDao {
	
	private Connection connection;
	private PreparedStatement pst_getAllTasks, pst_getTaskById;
	
	public TasklistDaoImpl() throws Exception {
		connection = DatabaseUtils.getConnection();
		pst_getAllTasks = connection.prepareStatement(TasklistConstants.GET_ALL_TASKS);
		pst_getTaskById = connection.prepareStatement(TasklistConstants.GET_TASK_BY_ID);
	}
	
	public void cleanUp() throws Exception {
		if(pst_getTaskById != null)
			pst_getTaskById.close();
		if(pst_getAllTasks != null)
			pst_getAllTasks.close();
		if(connection != null)
			connection.close();
	}

	@Override
	public Collection<Task> getAllTasks() {
		Collection<Task> taskCollection = new ArrayList<Task>();
		try(ResultSet rst = pst_getAllTasks.executeQuery()) {
			while(rst.next()) {
				taskCollection.add(new Task(rst.getLong(1), 
											rst.getString("task_title"),
											rst.getString("task_description"),
											rst.getString("task_category"),
											rst.getTimestamp(5),
											rst.getString("task_status")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return taskCollection;
	}

	@Override
	public Task getTask(Long id) {
		Task task = null;
		try {
			pst_getTaskById.setLong(1, id);
			ResultSet rst = pst_getTaskById.executeQuery();
			while(rst.next()) {
				task = new Task();
				task.setId(rst.getLong("task_id"));
				task.setTitle(rst.getString("task_title"));
				task.setDescription(rst.getNString("task_description"));
				task.setCategory(rst.getString("task_category"));
				task.setInsertDate(rst.getTimestamp("insert_date"));
				task.setStatus(rst.getString("task_status"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return task;
	}

}
