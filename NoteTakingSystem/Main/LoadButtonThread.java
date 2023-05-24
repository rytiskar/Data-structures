package Main;
import NoteSystem.*;

import java.io.*;
import java.util.List;

public class LoadButtonThread implements Runnable {
    private List<Personal> personalNotes;
    private List<Work> workNotes;
    private boolean loadedSuccessfully;

    public LoadButtonThread(List<Personal> personalNotes, List<Work> workNotes) {
        this.personalNotes = personalNotes;
        this.workNotes = workNotes;
        this.loadedSuccessfully = false;
    }

    public boolean getLoadStatus() {
        return this.loadedSuccessfully;
    }

    @Override
    public void run() {
        File personalFile = new File("personal_notes.dat");
        File workFile = new File("work_notes.dat");

        if (!personalFile.exists() || !workFile.exists()) {
            System.out.println("Error loading notes: Files not found");
            loadedSuccessfully = false;
            return;
        }

        try (FileInputStream personalInputStream = new FileInputStream(personalFile);
             ObjectInputStream personalObjectInputStream = new ObjectInputStream(personalInputStream);
             FileInputStream workInputStream = new FileInputStream(workFile);
             ObjectInputStream workObjectInputStream = new ObjectInputStream(workInputStream)) {

            List<Personal> loadedPersonalNotes = (List<Personal>) personalObjectInputStream.readObject();
            //personalNotes.clear(); // Clear existing notes before adding loaded notes
            personalNotes.addAll(loadedPersonalNotes);
            System.out.println("Personal notes loaded successfully!");

            List<Work> loadedWorkNotes = (List<Work>) workObjectInputStream.readObject();
            //workNotes.clear(); // Clear existing notes before adding loaded notes
            workNotes.addAll(loadedWorkNotes);
            System.out.println("Work notes loaded successfully!");

            loadedSuccessfully = true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading notes: " + e.getMessage());
            loadedSuccessfully = false;
        }
    }
}
