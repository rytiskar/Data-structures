package Main;
import NoteSystem.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class LoadThread implements Runnable {
    private List<Personal> personalNotes;
    private List<Work> workNotes;

    public LoadThread(List<Personal> personalNotes, List<Work> workNotes) {
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
