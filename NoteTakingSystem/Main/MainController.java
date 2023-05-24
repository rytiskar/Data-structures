package Main;
import NoteSystem.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeParseException;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
    private Button sortNotesButton;
    @FXML
    private Button pushDeadlineButton;
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
                    sortNotesButton.setDisable(false);
                    pushDeadlineButton.setDisable(true);
                } else if (selectedNoteType.equals("Work")) {
                    titleTextField.setDisable(false);
                    descriptionTextField.setDisable(false);
                    detailsTextField.setDisable(false);
                    deadlineDatePicker.setDisable(false);
                    meetingLinkTextField.setDisable(false);
                    addNoteButton.setDisable(false);
                    deleteNoteButton.setDisable(false);
                    displayNotesButton.setDisable(false);
                    sortNotesButton.setDisable(false);
                    pushDeadlineButton.setDisable(false);

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
            if(title.isEmpty() || deadline == null)
                throw new Exception("Empty fields");

            personalNotes.add(note);
            notesTextArea.setText("Personal note added successfully!");
        } catch (Exception e) {
            notesTextArea.setText("Empty Title and Deadline fields - note not added");
        }
    }

    private void addWorkNote(String title, String description, String details, LocalDateTime deadline, String meetingLink) {
        try {
            Work note = new Work(title, description, details, deadline, meetingLink);
            if(title.isEmpty() || deadline == null)
                throw new Exception("Empty fields");

            workNotes.add(note);
            notesTextArea.setText("Work note added successfully!");
        } catch (Exception e) {
            notesTextArea.setText("Empty Title and Deadline fields - note not added");
        }
    }

    @FXML
    private void deleteNote(ActionEvent event) {
        String noteType = noteTypeChoiceBox.getValue();

        displayNotes(event);
        try {
            if (noteType.equals("Personal") && personalNotes.isEmpty())
                throw new IllegalArgumentException("List is empty");
            else if (noteType.equals("Work") && workNotes.isEmpty())
                throw new IllegalArgumentException("List is empty");
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Delete note");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the index of the note you want to delete");

            TextField noteIndexField = new TextField();
            noteIndexField.setPromptText("Note Index");

            dialog.getDialogPane().setContent(new VBox(10, noteIndexField));

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int selectedIndex = Integer.parseInt(noteIndexField.getText());
                    if (noteType.equals("Personal")) {
                        if (selectedIndex >= 0 && selectedIndex < personalNotes.size()) {
                            deletePersonalNoteAtIndex(selectedIndex);
                        } else {
                            throw new IllegalArgumentException("Index is out of bounds");
                        }
                    } else if (noteType.equals("Work")) {
                        if (selectedIndex >= 0 && selectedIndex < workNotes.size()) {
                            deleteWorkNoteAtIndex(selectedIndex);
                        } else {
                            throw new IllegalArgumentException("Index is out of bounds");
                        }
                    }
                } catch (NumberFormatException e) {
                    notesTextArea.setText("Provided input is invalid");
                } catch (IllegalArgumentException e) {
                    notesTextArea.setText("Index is out of bounds");
                }
            }
        } catch (IllegalArgumentException e) {
            notesTextArea.setText("The note list is empty [DELETE]");
        }
    }

    private void deletePersonalNoteAtIndex(int index) {
        Personal note = personalNotes.remove(index);
        notesTextArea.setText("Personal note deleted: " + note.getTitle());
    }

    private void deleteWorkNoteAtIndex(int index) {
        Work note = workNotes.remove(index);
        notesTextArea.setText("Work note deleted: " + note.getTitle());
    }

    @FXML
    private void displayNotes(ActionEvent event) {
        String noteType = noteTypeChoiceBox.getValue();

        try {
            if (noteType != null) {
                if (noteType.equals("Personal")) {
                    if (personalNotes.isEmpty())
                        throw new IllegalArgumentException("The list is empty");
                    displayPersonalNotes();
                } else if (noteType.equals("Work")) {
                    if (workNotes.isEmpty())
                        throw new IllegalArgumentException("The list is empty");
                    displayWorkNotes();
                }
            } else {
                notesTextArea.setText("Invalid note type. Please try again.");
            }
        }catch (IllegalArgumentException e)
        {
            notesTextArea.setText("The note list is empty [DISPLAY]");
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

    @FXML
    private void saveNotes() {
        SaveButtonThread saveThread = new SaveButtonThread(personalNotes, workNotes);
        Thread thread = new Thread(saveThread);
        thread.start();
        try {
            thread.join();
            if (saveThread.getSaveStatus()) {
                notesTextArea.setText("Notes saved successfully!");
            } else {
                notesTextArea.setText("Error occurred while saving notes. Files could not be saved.");
            }
        } catch (InterruptedException e) {
            notesTextArea.setText("Error occurred while saving notes: " + e.getMessage());
        }
    }

    @FXML
    private void loadNotes() {
        LoadButtonThread loadThread = new LoadButtonThread(personalNotes, workNotes);
        Thread thread = new Thread(loadThread);
        thread.start();
        try {
            thread.join();
            if (loadThread.getLoadStatus()) {
                notesTextArea.setText("Notes loaded successfully!");
            } else {
                notesTextArea.setText("Error occurred while loading notes. Files could not be loaded.");
            }
        } catch (InterruptedException e) {
            notesTextArea.setText("Error occurred while loading notes: " + e.getMessage());
        }
    }

    public void pushDeadline(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Push Deadline");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the note index and number of days to push the deadline:");

        TextField noteIndexField = new TextField();
        noteIndexField.setPromptText("Note Index");
        TextField daysToPushField = new TextField();
        daysToPushField.setPromptText("Days to Push");

        try{
            if(workNotes.isEmpty())
                throw new IllegalArgumentException("The list is empty");
            dialog.getDialogPane().setContent(new VBox(10, noteIndexField, daysToPushField));

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int selectedIndex = Integer.parseInt(noteIndexField.getText());
                    int daysToPush = Integer.parseInt(daysToPushField.getText());

                    if (selectedIndex >= 0 && selectedIndex < workNotes.size()) {
                        Work workNote = workNotes.get(selectedIndex);
                        try {
                            workNote.pushDeadlineByDays(daysToPush);
                            notesTextArea.setText("Note deadline pushed successfully!\n" +
                                    "New deadline is: " + workNote.getDeadline());
                        } catch (NoteException e) {
                            notesTextArea.setText("The deadline cannot be modified");
                        }
                    }
                    else
                        notesTextArea.setText("Cannot access note at such index");
                } catch (NumberFormatException e) {
                    notesTextArea.setText("Provided input is invalid");
                }
            }
        } catch (IllegalArgumentException e)
        {
            notesTextArea.setText("The note list is empty [PUSH_DEADLINE]");
        }
    }

    public void sortNotes(ActionEvent actionEvent) {
        String noteType = noteTypeChoiceBox.getValue();
        try{
            if(noteType.equals("Personal"))
            {
                if(personalNotes.isEmpty())
                    throw new IllegalArgumentException("The list is empty");
                Collections.sort(personalNotes);
                notesTextArea.setText("Personal notes sorted successfully");
            }
            else if(noteType.equals("Work")) {
                if(workNotes.isEmpty())
                    throw new IllegalArgumentException("The list is empty");
                Collections.sort(workNotes);
                notesTextArea.setText("Work notes sorted successfully");
            }
        } catch (IllegalArgumentException e)
        {
            notesTextArea.setText("The note list is empty [SORT]");
        }
    }
};