package task.manager.models;


import java.util.HashMap;


public class Journal {
	
	private HashMap<Integer, Task> tasksMap;
	
	public Journal(HashMap<Integer, Task> tasksMap) {
		this.tasksMap = tasksMap;
	}
	
	public HashMap<Integer, Task> getTasksMap() {
		return tasksMap;
	}
	
	public void setTasksMap(HashMap<Integer, Task> tasksMap) {
		this.tasksMap = tasksMap;
	}
}
