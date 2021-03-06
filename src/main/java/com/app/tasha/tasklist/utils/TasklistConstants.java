package com.app.tasha.tasklist.utils;

public class TasklistConstants {
	
	// ------------------- DB Queries ------------------- // 
	public static String GET_ALL_TASKS = "Select * from task";
	
	public static String GET_TASK_BY_ID = "Select * from task where task_id = ?";
	
	public static String CREATE_TASK = "insert into task(task_title, task_description, task_category, insert_date, task_status) "
								+ "values(?,?,?,?,?)";
	
	public static String UPDATE_TASK = "update task set task_title=?, task_description=?, task_category=?, "
								+ "insert_date=?, task_status=? where task_id=?";
	
	public static String DELETE_TASK = "delete from task where task_id=?";
	
	public static String GET_TASKS_BY_STATUS = "Select * from task where task_status=?";
	
	public static String GET_TASKS_BY_DATE = "Select * from task where insert_date = ?";
	
	public static String DATE_FORMAT = "dd/MM/yyyy";
	
	// ------------------- Task Status ------------------- // 
	public static String TASK_STATUS_NEW = "NEW";
	
	public static String TASK_STATUS_PENDING = "PENDING";
	
	public static String TASK_STATUS_COMPLETE = "COMPLETE";
	
	// ------------------- Task Delete ------------------- //
	public static String TASK_DELETE_SUCCESS = "Record deleted successfully";
	
	public static String TASK_DELETE_FAILURE = "Error occurred while deleting record";
}
