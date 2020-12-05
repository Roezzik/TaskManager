package task.manager.model;


public enum Status {

    SCHEDULED("Scheduled"),
    DONE("Done"),
    POSTPONED("Postponed"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled");

    // what is title for this class? you can set Status.DONE or smthg else for tasks WO this field
    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

