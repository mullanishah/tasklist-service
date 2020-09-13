package com.app.tasha.tasklist.model;

import java.sql.Date;
import lombok.Data;

@Data
public class Task {
	private Long id;
	private String title;
	private String description;
	private String category;
	private Date insertDate;
	private String insertDateFormatted;
	private String status;
	
	public Task() {
		
	}

	public Task(Long id, String title, String description, String category, Date insertDate, String status) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.insertDate = insertDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getInsertDateFormatted() {
		return insertDateFormatted;
	}

	public void setInsertDateFormatted(String insertDateFormatted) {
		this.insertDateFormatted = insertDateFormatted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", insertDateFormatted=" + insertDateFormatted + ", status=" + status + "]";
	}
}
