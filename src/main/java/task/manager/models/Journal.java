package task.manager.models;


import java.util.HashMap;

// todo use default code formatting
// 4 indents instead of 8
public class Journal {

	private HashMap<Integer, Task> tasksMap;

	// todo please, create constructor WO parameters

	// todo looks like this constructor is not security, read comments below
	public Journal(HashMap<Integer, Task> tasksMap) {
		this.tasksMap = tasksMap;
	}

	// todo manage map responsibility is task for journal class, please, return map and other private collections only in RO mode
	// use unmodifiable map instead of real
	public HashMap<Integer, Task> getTasksMap() {
		return tasksMap;
	}

	// todo do not give developers chance to do hard reset of journal - it is bad
	// developer should manipulate with tasks via crud methods - add, delete, update
	public void setTasksMap(HashMap<Integer, Task> tasksMap) {
		this.tasksMap = tasksMap;
	}

	// todo implement default Object methods
}
