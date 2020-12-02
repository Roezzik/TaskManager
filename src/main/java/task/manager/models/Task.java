package task.manager.models;


import java.time.LocalDateTime;


public class Task {
	
	private final int             id;
	private       String          name;
	private       String          description;
	private       LocalDateTime   date;
	private       Status status;
	
	public Task(int id,
	            String name,
	            String description,
	            LocalDateTime date,
	            Status status) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
