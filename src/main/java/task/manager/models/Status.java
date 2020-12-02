package task.manager.models;


public enum Status {
	
	SCHEDULED("Scheduled"),
	DONE("Done"),
	POSTPONED("Postponed"),
	EXPIRED("Expired"),
	CANCELLED("Cancelled");
	
	private final String title;
	
	Status(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}

