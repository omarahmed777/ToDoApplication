module fsad.week2.todoapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens fsad.week2.todoapplication to javafx.fxml;
    exports fsad.week2.todoapplication;
}