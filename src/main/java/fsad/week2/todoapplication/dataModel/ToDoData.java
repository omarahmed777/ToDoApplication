package fsad.week2.todoapplication.dataModel;

import javafx.collections.FXCollections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

//Singleton class
public class ToDoData {
    //Step 1 to create Singleton class
    private static ToDoData instance = new ToDoData();
    private static String filename = "ToDoListItems.txt";

    private List<ToDoItem> toDoItems;
    private DateTimeFormatter formatter;
    //Step 2
    private ToDoData(){
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    //Step 3
    public static ToDoData getInstance(){
        return instance;
    }
    public List<ToDoItem> getToDoItems() { return toDoItems; }

    public void setToDoItems(List<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }


    public void storeToDoItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<ToDoItem> it = toDoItems.iterator();
            while (it.hasNext()) {
                ToDoItem item = it.next();
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void loadToDoItems() throws IOException {
        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);
        String input;

        try {
            while((input = br.readLine()) != null) {
                String [] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                ToDoItem toDoItem = new ToDoItem(shortDescription, details, date);
                toDoItems.add(toDoItem);
            }
        } finally {
            if (br != null)
                br.close();
        }
    }
}