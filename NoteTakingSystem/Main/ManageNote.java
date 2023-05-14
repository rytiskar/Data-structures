package Main;
import NoteSystem.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class ManageNote {
    private static List<Personal> personalNotes = new ArrayList<>();
    private static List<Work> workNotes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public void start() {
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNote();
                    break;
                case 2:
                    deleteNote();
                    break;
                case 3:
                    displayNotes();
                    break;
                case 4:
                    saveNotes();
                    break;
                case 5:
                    loadNotes();
                    break;
                case 6:
                    sortNotes();
                    break;
                case 7:
                    pushNoteDeadline();
                    break;
                case 8:
                    readInput();
                    break;
                case 9:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
        } while (choice != 9);
    }


    private static void displayMenu() {
        System.out.println("Note Taking System");
        System.out.println("--------------------------");
        System.out.println("1. Add a note");
        System.out.println("2. Delete a note");
        System.out.println("3. Display notes");
        System.out.println("4. Save notes");
        System.out.println("5. Load notes");
        System.out.println("6. Sort notes");
        System.out.println("7. Push note deadline");
        System.out.println("8. Read input");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addNote() {
        System.out.println("Select note type:");
        System.out.println("1. Personal note");
        System.out.println("2. Work note");
        System.out.print("Enter your choice: ");
        int noteType = scanner.nextInt();
        scanner.nextLine();

        if (noteType == 1) {
            addPersonalNote();
        } else if (noteType == 2) {
            addWorkNote();
        } else {
            System.out.println("Invalid note type. Please try again.");
        }
    }
    private static void addPersonalNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        System.out.print("Enter note description: ");
        String description = scanner.nextLine();
        System.out.print("Enter note details: ");
        String details = scanner.nextLine();
        System.out.print("Enter note deadline (YYYY-MM-DD HH:mm): ");
        String deadlineString = scanner.nextLine();

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineString, Note.DATE_TIME_FORMATTER);
            Personal note = new Personal(title, description, details, deadline);
            personalNotes.add(note);
            System.out.println("Personal note added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid deadline format. Note not added.");
        }
    }
    private static void addWorkNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        System.out.print("Enter note description: ");
        String description = scanner.nextLine();
        System.out.print("Enter note details: ");
        String details = scanner.nextLine();
        System.out.print("Enter note deadline (YYYY-MM-DD HH:mm): ");
        String deadlineString = scanner.nextLine();
        System.out.print("Enter metting link: ");
        String mettingLink = scanner.nextLine();

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineString, Note.DATE_TIME_FORMATTER);
            Work note = new Work(title, description, details, deadline, mettingLink);
            workNotes.add(note);
            System.out.println("Work note added successfully!");
        } catch (Exception e)
        {
            System.out.println("Invalid deadline format. Note not added.");
        }
    }
    private static void deleteNote() {
        System.out.print("Enter the index of the note to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the type of note to delete (1 for Personal, 2 for Work): ");
        int noteType = scanner.nextInt();
        scanner.nextLine();

        if (noteType == 1) {
            if (index >= 0 && index < personalNotes.size()) {
                Note note = personalNotes.remove(index);
                System.out.println("Personal note deleted: " + note.getTitle());
            } else {
                System.out.println("Invalid personal note index. Please try again.");
            }
        } else if (noteType == 2) {
            if (index >= 0 && index < workNotes.size()) {
                Note note = workNotes.remove(index);
                System.out.println("Work note deleted: " + note.getTitle());
            } else {
                System.out.println("Invalid work note index. Please try again.");
            }
        } else {
            System.out.println("Invalid note type. Please try again.");
        }
    }
    private static void displayNotes() {
        System.out.println("Select note type:");
        System.out.println("1. Personal note");
        System.out.println("2. Work note");
        System.out.println("3. All existing notes");
        int noteType = scanner.nextInt();
        scanner.nextLine();

        if (noteType == 1) {
            displayPersonalNotes();
        } else if (noteType == 2) {
            displayWorkNotes();
        } else if (noteType == 3){
            displayAllNotes();
        }
        else{
            System.out.println("Invalid note type. Please try again.");
        }
    }
    private static void displayPersonalNotes()
    {
        System.out.println("Personal Notes:");
        for(int i = 0; i < personalNotes.size(); i++)
            System.out.println( "[ " + i + " ] " + personalNotes.get(i));
    }
    private static void displayWorkNotes()
    {
        System.out.println("Work Notes:");
        for(int i = 0; i < workNotes.size(); i++)
            System.out.println("[ " + i + " ] " + workNotes.get(i));
    }
    private static void displayAllNotes()
    {
        displayPersonalNotes();
        displayWorkNotes();
    }
    private void saveNotes()
    {
        Thread thread = new Thread(new SaveButtonThread(personalNotes, workNotes));
        thread.start();
    }
    private void loadNotes() {
        Thread thread = new Thread(new LoadButtonThread(personalNotes, workNotes));
        thread.start();
    }
    private static void readInput() {
        System.out.print("Enter the path of the input file: ");
        String filePath = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.equals("Note Type: Personal")) {
                        readPersonalNote(reader);
                    } else if (line.equals("Note Type: Work")) {
                        readWorkNote(reader);
                    }
                }
            }
            System.out.println("Notes loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error occurred while loading notes: " + e.getMessage());
        }
    }
    private static void readPersonalNote(BufferedReader reader) throws IOException {
        String title = reader.readLine().substring(7).trim();
        String description = reader.readLine().substring(13).trim();
        String details = reader.readLine().substring(9).trim();
        String deadlineString = reader.readLine().substring(10).trim();
        reader.readLine();

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineString, Note.DATE_TIME_FORMATTER);
            Personal note = new Personal(title, description, details, deadline);
            personalNotes.add(note);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid deadline format. Note not added.");
        }
    }
    private static void readWorkNote(BufferedReader reader) throws IOException {
        String title = reader.readLine().substring(7).trim();
        String description = reader.readLine().substring(13).trim();
        String details = reader.readLine().substring(9).trim();
        String deadlineString = reader.readLine().substring(10).trim();
        String meetingLink = reader.readLine().substring(13).trim();
        reader.readLine();

        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineString, Note.DATE_TIME_FORMATTER);
            Work note = new Work(title, description, details, deadline, meetingLink);
            workNotes.add(note);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid deadline format. Note not added.");
        }
    }
    private static void sortNotes() {
        System.out.println("Select note type:");
        System.out.println("1. Personal note");
        System.out.println("2. Work note");
        int noteType = scanner.nextInt();
        scanner.nextLine();

        switch (noteType) {
            case 1:
                Collections.sort(personalNotes);
                System.out.println("Personal notes sorted successfully!");
                break;
            case 2:
                Collections.sort(workNotes);
                System.out.println("Work notes sorted successfully!");
                break;
            default:
                System.out.println("Invalid note type. Please try again.");
                break;
        }
    }
    private static void pushNoteDeadline() {
        System.out.print("Enter the index of the work note to push the deadline: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index >= 0 && index < workNotes.size()) {
            Work workNote = workNotes.get(index);
            System.out.print("Enter the number of days to push the deadline: ");
            int days = scanner.nextInt();
            scanner.nextLine();

            try {
                workNote.pushDeadlineByDays(days);
                System.out.println("Note deadline pushed successfully!");
            } catch (NoteException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid work note index. Please try again.");
        }
    }
}
