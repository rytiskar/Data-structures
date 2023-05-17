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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;

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
                    addNoteButton.setDisable(false);
                    deleteNoteButton.setDisable(false);
                    displayNotesButton.setDisable(false);
                } else if (selectedNoteType.equals("Work")) {
                    titleTextField.setDisable(false);
                    descriptionTextField.setDisable(false);
                    detailsTextField.setDisable(false);
                    deadlineDatePicker.setDisable(false);
                    meetingLinkTextField.setDisable(false);
                    addNoteButton.setDisable(false);
                    deleteNoteButton.setDisable(false);
                    displayNotesButton.setDisable(false);
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
            notesTextArea.setText("Invalid note type. Please try again.");
        }
    }

    private void addPersonalNote(String title, String description, String details, LocalDateTime deadline) {
        try {
            Personal note = new Personal(title, description, details, deadline);
            if(title == null || deadline == null)
                throw new Exception("Empty fields");

            personalNotes.add(note);
            notesTextArea.setText("Personal note added successfully!");
        } catch (Exception e) {
            notesTextArea.setText("Invalid deadline format - note not added.");
        }
    }

    private void addWorkNote(String title, String description, String details, LocalDateTime deadline, String meetingLink) {
        try {
            Work note = new Work(title, description, details, deadline, meetingLink);
            if(title == null || deadline == null)
                throw new Exception("Empty fields");

            workNotes.add(note);
            notesTextArea.setText("Work note added successfully!");
        } catch (Exception e) {
            notesTextArea.setText("Invalid deadline format - note not added.");
        }
    }

    @FXML
    private void deleteNote(ActionEvent event) {
        String noteType = noteTypeChoiceBox.getValue();

        if (noteType != null) {
            displayNotes(event);
            Platform.runLater(() -> {
                int index = getIndexFromUserInput();

                if (index >= 0) {
                    if (noteType.equals("Personal")) {
                        deletePersonalNoteAtIndex(index);
                    } else if (noteType.equals("Work")) {
                        deleteWorkNoteAtIndex(index);
                    } else {
                        displayErrorMessage("Invalid note type. Please try again.");
                    }
                } else {
                    displayErrorMessage("Invalid note index. Please try again.");
                }
            });
        } else {
            displayErrorMessage("Invalid note type. Please try again.");
        }
    }

    private int getIndexFromUserInput() {
        String indexInput = JOptionPane.showInputDialog("Enter the index of the note to delete:");
        int index = -1;

        try {
            index = Integer.parseInt(indexInput);
        } catch (NumberFormatException e) {
            // Handle parsing error
        }

        return index;
    }

    private void deletePersonalNoteAtIndex(int index) {
        if (index >= 0 && index < personalNotes.size()) {
            Personal note = personalNotes.remove(index);
            displaySuccessMessage("Personal note deleted: " + note.getTitle());
        } else {
            displayErrorMessage("Invalid personal note index. Please try again.");
        }
    }

    private void deleteWorkNoteAtIndex(int index) {
        if (index >= 0 && index < workNotes.size()) {
            Work note = workNotes.remove(index);
            displaySuccessMessage("Work note deleted: " + note.getTitle());
        } else {
            displayErrorMessage("Invalid work note index. Please try again.");
        }
    }

    private void displayErrorMessage(String message) {
        notesTextArea.setText(message);
    }

    private void displaySuccessMessage(String message) {
        notesTextArea.setText(message);
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
            notesTextArea.setText("Invalid note type. Please try again.");
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
        displayPersonalNotes();
        displayWorkNotes();
    }


    @FXML
    private void saveNotes() {
        Thread thread = new Thread(new SaveButtonThread(personalNotes, workNotes));
        thread.start();
        try {
            thread.join();
            notesTextArea.setText("Notes saved successfully!");
        } catch (InterruptedException e) {
            notesTextArea.setText("Error occurred while saving notes: " + e.getMessage());
        }
    }

    @FXML
    private void loadNotes() {
        Thread thread = new Thread(new LoadButtonThread(personalNotes, workNotes));
        thread.start();
        try {
            thread.join();
            notesTextArea.setText("Notes loaded successfully!");
        } catch (InterruptedException e) {
            notesTextArea.setText("Error occurred while loading notes: " + e.getMessage());
        }
    }

};