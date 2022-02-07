package fsad.week2.todoapplication.dataModel;

import java.time.LocalDate;

public class toDoItem {
    private String shortDescription;
    private String details;
    private LocalDate deadline;

    public toDoItem(String shortDescription, String details, LocalDate deadline) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
    }

}
