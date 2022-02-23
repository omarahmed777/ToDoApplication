package fsad.week2.todoapplication;

import fsad.week2.todoapplication.dataModel.ToDoData;
import fsad.week2.todoapplication.dataModel.ToDoItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField shortDescrip;
    @FXML
    private TextArea details;
    @FXML
    private DatePicker deadline;

    public String getShortDescrip() { return shortDescrip.getText(); }

    public void setShortDescrip(ToDoItem item) { shortDescrip.setText(item.getShortDescription()); }

    public ToDoItem processResults() {
        String shortS = shortDescrip.getText().trim();
        String detailsS = details.getText().trim();
        LocalDate deadlineS = deadline.getValue();
        ToDoItem newItem = new ToDoItem(shortS, detailsS, deadlineS);
        ToDoData.getInstance().addToDoItem(newItem);
        return newItem;
    }

    public ToDoItem processEditResults(ToDoItem item) {
        ObservableList<ToDoItem> allItems = ToDoData.getInstance().getToDoItems();
        allItems.remove(item);
        String shortS = shortDescrip.getText().trim();
        String detailsS = details.getText().trim();
        LocalDate deadlineS = deadline.getValue();
        ToDoItem changedItem = new ToDoItem(shortS, detailsS, deadlineS);
        ToDoData.getInstance().addToDoItem(changedItem);
        return changedItem;
    }

    public void setToEdit(ToDoItem item) {
        shortDescrip.setText(item.getShortDescription());
        details.setText(item.getDetails());
        deadline.setValue(item.getDeadline());
    }
}
