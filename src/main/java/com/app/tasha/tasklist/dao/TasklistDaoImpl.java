package com.app.tasha.tasklist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Repository;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.utils.DatabaseUtils;
import com.app.tasha.tasklist.utils.TasklistConstants;

@Repository
public class TasklistDaoImpl implements TasklistDao {
	
	private Connection connection;
	private PreparedStatement pst_getAllTasks, pst_getTaskById, pst_createTask, pst_updateTask;
	
	public TasklistDaoImpl() throws Exception {
		connection = DatabaseUtils.getConnection();
		pst_getAllTasks = connection.prepareStatement(TasklistConstants.GET_ALL_TASKS);
		pst_getTaskById = connection.prepareStatement(TasklistConstants.GET_TASK_BY_ID);
		pst_createTask = connection.prepareStatement(TasklistConstants.CREATE_TASK, Statement.RETURN_GENERATED_KEYS);
		pst_updateTask = connection.prepareStatement(TasklistConstants.UPDATE_TASK, Statement.RETURN_GENERATED_KEYS);
	}
	
	public void cleanUp() throws Exception {
		if(null != pst_createTask)
			pst_createTask.close();
		if(null != pst_updateTask)
			pst_updateTask.close();
		if(pst_getTaskById != null)
			pst_getTaskById.close();
		if(pst_getAllTasks != null)
			pst_getAllTasks.close();
		if(connection != null)
			connection.close();
	}

	@Override
	public Collection<Task> getAllTasks() throws Exception {
		Collection<Task> taskCollection = new ArrayList<Task>();
		try(ResultSet rst = pst_getAllTasks.executeQuery()) {
			while(rst.next()) {
				taskCollection.add(new Task(rst.getLong(1), 
											rst.getString("task_title"),
											rst.getString("task_description"),
											rst.getString("task_category"),
											rst.getDate(5),
											rst.getString("task_status")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return taskCollection;
	}

	@Override
	public Task getTask(Long id) throws Exception {
		Task task = null;
		pst_getTaskById.setLong(1, id);
		try(ResultSet rst = pst_getTaskById.executeQuery();) {
			while(rst.next()) {
				task = new Task();
				task.setId(rst.getLong("task_id"));
				task.setTitle(rst.getString("task_title"));
				task.setDescription(rst.getNString("task_description"));
				task.setCategory(rst.getString("task_category"));
				task.setInsertDate(rst.getDate("insert_date"));
				task.setStatus(rst.getString("task_status"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return task;
	}

	@Override
	public Task createTask(Task task){
		int generatedKey;
		try { 
			pst_createTask.setString(1, task.getTitle());
			pst_createTask.setString(2, task.getDescription());
			pst_createTask.setString(3, task.getCategory());
			pst_createTask.setDate(4, task.getInsertDate());
			pst_createTask.setString(5, task.getStatus());
			int status = pst_createTask.executeUpdate();
			if(status == 1) {
				ResultSet rst = pst_createTask.getGeneratedKeys();
				if(rst.next()) {
					generatedKey = rst.getInt(1);
					task.setId((long) generatedKey);
				}	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return task;
	}

	@Override
	public Task updateTask(Task task) {
		int generatedKey;
		try {
			pst_updateTask.setString(1, task.getTitle());
			pst_updateTask.setString(2, task.getDescription());
			pst_updateTask.setString(3, task.getCategory());
			pst_updateTask.setDate(4, task.getInsertDate());
			pst_updateTask.setString(5, task.getStatus());
			pst_updateTask.setLong(6, task.getId());
			int status = pst_updateTask.executeUpdate();
			if(status == 1) {
				ResultSet rst = pst_updateTask.getGeneratedKeys();
				if(rst.next()) {
					generatedKey = rst.getInt(1);
					task.setId((long) generatedKey);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return task;
	}

}
