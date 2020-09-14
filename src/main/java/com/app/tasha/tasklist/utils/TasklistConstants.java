package com.app.tasha.tasklist.utils;

public class TasklistConstants {

	public static String GET_ALL_TASKS = "Select * from task";
	
	public static String GET_TASK_BY_ID = "Select * from task where task_id = ?";
	
	public static String CREATE_TASK = "insert into task(task_title, task_description, task_category, insert_date, task_status) "
								+ "values(?,?,?,?,?)";
	
	public static String UPDATE_TASK = "update task set task_title=?, task_description=?, task_category=?, "
								+ "insert_date=?, task_status=? where task_id=?";
	
	public static String DELETE_TASK = "delete from task where task_id=?";
	
	public static String DATE_FORMAT = "dd/MM/yyyy";
}
