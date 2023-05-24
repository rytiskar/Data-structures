package Main;
import NoteSystem.*;

import java.io.*;
import java.util.List;


public class SaveButtonThread implements Runnable {
    private List<Personal> personalNotes;
    private List<Work> workNotes;
    private boolean savedSuccessfully = false;

    public SaveButtonThread(List<Personal> personalNotes, List<Work> workNotes) {
        this.personalNotes = personalNotes;
        this.workNotes = workNotes;
    }

    public boolean getSaveStatus() {
        return savedSuccessfully;
    }

    @Override
    public void run() {
        File personalFile = new File("personal_notes.dat");
        File workFile = new File("work_notes.dat");

        try {
            if (personalFile.exists()) {
                personalFile.delete();
                personalFile.createNewFile();
            }
            if (workFile.exists()) {
                workFile.delete();
                workFile.createNewFile();
            }

            try (FileOutputStream personalOutputStream = new FileOutputStream(personalFile);
                 ObjectOutputStream personalObjectOutputStream = new ObjectOutputStream(personalOutputStream);
                 FileOutputStream workOutputStream = new FileOutputStream(workFile);
                 ObjectOutputStream workObjectOutputStream = new ObjectOutputStream(workOutputStream)) {

                personalObjectOutputStream.writeObject(personalNotes);
                System.out.println("Personal notes saved successfully!");

                workObjectOutputStream.writeObject(workNotes);
                System.out.println("Work notes saved successfully!");

                savedSuccessfully = true;
            } catch (IOException e) {
                System.out.println("Error saving notes: " + e.getMessage());
                savedSuccessfully = false;
            }
        } catch (IOException e) {
            System.out.println("Error clearing files: " + e.getMessage());
            savedSuccessfully = false;
        }
    }
}
