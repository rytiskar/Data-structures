package Main;
import NoteSystem.*;

import java.io.*;
import java.util.List;

public class LoadButtonThread implements Runnable {
    private List<Personal> personalNotes;
    private List<Work> workNotes;

    public LoadButtonThread(List<Personal> personalNotes, List<Work> workNotes) {
        this.personalNotes = personalNotes;
        this.workNotes = workNotes;
    }

    @Override
    public void run() {
        try (FileInputStream personalInputStream = new FileInputStream("personal_notes.dat");
             ObjectInputStream personalObjectInputStream = new ObjectInputStream(personalInputStream);
             FileInputStream workInputStream = new FileInputStream("work_notes.dat");
             ObjectInputStream workObjectInputStream = new ObjectInputStream(workInputStream)) {

            List<Personal> loadedPersonalNotes = (List<Personal>) personalObjectInputStream.readObject();
            personalNotes.addAll(loadedPersonalNotes);
            System.out.println("Personal notes loaded successfully!");

            List<Work> loadedWorkNotes = (List<Work>) workObjectInputStream.readObject();
            workNotes.addAll(loadedWorkNotes);
            System.out.println("Work notes loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading notes: " + e.getMessage());
        }
    }
}
