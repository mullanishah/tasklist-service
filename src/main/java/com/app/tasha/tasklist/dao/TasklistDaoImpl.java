package com.app.tasha.tasklist.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.app.tasha.tasklist.model.Task;
import com.app.tasha.tasklist.utils.DatabaseUtils;
import com.app.tasha.tasklist.utils.TasklistConstants;

@Repository
public class TasklistDaoImpl implements TasklistDao {
	
	private Connection connection;
	private PreparedStatement pst_getAllTasks, pst_getTaskById, pst_createTask, pst_updateTask, pst_deleteTask, 
			pst_pendingTasks, pst_completedTasks, pst_tasksByDate;
	
	public TasklistDaoImpl() throws Exception {
		connection = DatabaseUtils.getConnection();
		pst_getAllTasks = connection.prepareStatement(TasklistConstants.GET_ALL_TASKS);
		pst_getTaskById = connection.prepareStatement(TasklistConstants.GET_TASK_BY_ID);
		pst_createTask = connection.prepareStatement(TasklistConstants.CREATE_TASK, Statement.RETURN_GENERATED_KEYS);
		pst_updateTask = connection.prepareStatement(TasklistConstants.UPDATE_TASK, Statement.RETURN_GENERATED_KEYS);
		pst_deleteTask = connection.prepareStatement(TasklistConstants.DELETE_TASK);
		pst_pendingTasks = connection.prepareStatement(TasklistConstants.GET_TASKS_BY_STATUS);
		pst_completedTasks = connection.prepareStatement(TasklistConstants.GET_TASKS_BY_STATUS);
		pst_tasksByDate = connection.prepareStatement(TasklistConstants.GET_TASKS_BY_DATE);
	}
	
	public void cleanUp() throws Exception {
		if(null != pst_tasksByDate)
			pst_tasksByDate.close();
		if(null != pst_completedTasks)
			pst_completedTasks.close();
		if(null != pst_pendingTasks)
			pst_pendingTasks.close();
		if(null != pst_deleteTask)
			pst_deleteTask.close();
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
	
	/*---- --CRUD Methods-- ----*/
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

	@Override
	public String deleteTask(long id) {
		String deleteResult = null;
		try {
			pst_deleteTask.setLong(1, id);
			int status = pst_deleteTask.executeUpdate();
			if(status == 1)
				deleteResult = TasklistConstants.TASK_DELETE_SUCCESS;
			else
				deleteResult = TasklistConstants.TASK_DELETE_FAILURE;
		}catch (Exception e) {
			if (e instanceof SQLException) 
				e.printStackTrace();
			else
				e.printStackTrace();
		}
		return deleteResult;
	}

	@Override
	public List<Task> getPendingTasks(String status) {
		List<Task> tasksFromDB = new ArrayList<Task>();
		try {
			pst_pendingTasks.setString(1, status);
			ResultSet rst = pst_pendingTasks.executeQuery();
			while(rst.next()) {
				tasksFromDB.add(new Task(rst.getLong(1), rst.getString(2), rst.getString(3), 
						rst.getString(4), rst.getDate(5), rst.getString(6)));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tasksFromDB;
	}

	@Override
	public List<Task> getCompletedTasks(String status) {
		List<Task> tasksFromDB = new ArrayList<Task>();
		try {
			pst_completedTasks.setString(1, status);
			ResultSet rst = pst_completedTasks.executeQuery();
			while(rst.next()) {
				tasksFromDB.add(new Task(rst.getLong(1), rst.getString(2), rst.getString(3), 
						rst.getString(4), rst.getDate(5), rst.getString(6)));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tasksFromDB;
	}

	@Override
	public List<Task> getTasksByDate(Date date) {
		List<Task> tasksFromDB = new ArrayList<Task>();
		try {
			pst_tasksByDate.setDate(1, date);
			ResultSet rst = pst_tasksByDate.executeQuery();
			while(rst.next()) {
				tasksFromDB.add(new Task(rst.getLong(1), rst.getString(2), rst.getString(3), 
						rst.getString(4), rst.getDate(5), rst.getString(6)));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tasksFromDB;
	}

}
