package Main;
import NoteSystem.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

    private static List<Personal> personalNotes = new ArrayList<>();
    private static List<Work> workNotes = new ArrayList<>();
    @FXML
    private ChoiceBox<String> noteTypeChoiceBox;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField detailsTextField;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private Button addNoteButton;
    @FXML
    private Button deleteNoteButton;
    @FXML
    private Button displayNotesButton;
    @FXML
    private Button saveNotes;
    @FXML
    private Button loadNotes;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private TextField meetingLinkTextField;

    public void initialize() {
        noteTypeChoiceBox.setOnAction(event -> {
            try {
                String selectedNoteType = noteTypeChoiceBox.getValue();

                if (selectedNoteType.equals("Personal")) {
                    titleTextField.setDisable(false);
                    descriptionTextField.setDisable(false);
                    detailsTextField.setDisable(false);
                    deadlineDatePicker.setDisable(false);
                    meetingLinkTextField.setDisable(true);
                } else if (selectedNoteType.equals("Work")) {
                    titleTextField.setDisable(false);
                    descriptionTextField.setDisable(false);
                    detailsTextField.setDisable(false);
                    deadlineDatePicker.setDisable(false);
                    meetingLinkTextField.setDisable(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    private void addNote(ActionEvent event) {
        String noteType = noteTypeChoiceBox.getValue();
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String details = detailsTextField.getText();
        LocalDate selectedDate = deadlineDatePicker.getValue();
        LocalDateTime deadline = null;
        if (selectedDate != null) {
            deadline = selectedDate.atStartOfDay();
        }

        if (noteType.equals("Personal")) {
            addPersonalNote(title, description, details, deadline);
        } else if (noteType.equals("Work")) {
            String meetingLink = meetingLinkTextField.getText();
            addWorkNote(title, description, details, deadline, meetingLink);
        } else {
            System.out.println("Invalid note type. Please try again.");
        }
    }

    private void addPersonalNote(String title, String description, String details, LocalDateTime deadline) {
        try {
            Personal note = new Personal(title, description, details, deadline);
            personalNotes.add(note);
            System.out.println("Personal note added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid deadline format. Note not added.");
        }
    }

    private void addWorkNote(String title, String description, String details, LocalDateTime deadline, String meetingLink) {
        try {
            Work note = new Work(title, description, details, deadline, meetingLink);
            workNotes.add(note);
            System.out.println("Work note added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid deadline format. Note not added.");
        }
    }


    @FXML
    private void deleteNote(ActionEvent event) {
        // Delete note functionality
    }

    @FXML
    private void displayNotes(ActionEvent event) {
        String noteType = noteTypeChoiceBox.getValue();

        if (noteType != null) {
            if (noteType.equals("Personal")) {
                displayPersonalNotes();
            } else if (noteType.equals("Work")) {
                displayWorkNotes();
            } else {
                displayAllNotes();
            }
        } else {
            System.out.println("Invalid note type. Please try again.");
        }
    }

    private void displayPersonalNotes() {
        StringBuilder sb = new StringBuilder("Personal Notes:\n");
        for (int i = 0; i < personalNotes.size(); i++) {
            sb.append("[ ").append(i).append(" ] ").append(personalNotes.get(i)).append("\n");
        }
        notesTextArea.setText(sb.toString());
    }

    private void displayWorkNotes() {
        StringBuilder sb = new StringBuilder("Work Notes:\n");
        for (int i = 0; i < workNotes.size(); i++) {
            sb.append("[ ").append(i).append(" ] ").append(workNotes.get(i)).append("\n");
        }
        notesTextArea.setText(sb.toString());
    }

    private void displayAllNotes() {
        StringBuilder sb = new StringBuilder("All Notes:\n");
        sb.append("Personal Notes:\n");
        for (int i = 0; i < personalNotes.size(); i++) {
            sb.append("[ ").append(i).append(" ] ").append(personalNotes.get(i)).append("\n");
        }
        sb.append("\nWork Notes:\n");
        for (int i = 0; i < workNotes.size(); i++) {
            sb.append("[ ").append(i).append(" ] ").append(workNotes.get(i)).append("\n");
        }
        notesTextArea.setText(sb.toString());
    }

    @FXML
    private void saveNotes() {
        Thread thread = new Thread(new SaveButtonThread(personalNotes, workNotes));
        thread.start();
    }

    @FXML
    private void loadNotes() {
        Thread thread = new Thread(new LoadButtonThread(personalNotes, workNotes));
        thread.start();
    }

};