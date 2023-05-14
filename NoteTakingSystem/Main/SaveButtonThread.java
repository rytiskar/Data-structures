package Main;
import NoteSystem.*;

import java.io.*;
import java.util.List;


public class SaveButtonThread implements Runnable
{
    private List<Personal> personalNotes;
    private List<Work> workNotes;

    public SaveButtonThread(List<Personal> personalNotes, List<Work> workNotes)
    {
        this.personalNotes = personalNotes;
        this.workNotes = workNotes;
    }
    @Override
    public void run()
    {
        try (FileOutputStream personalOutputStream = new FileOutputStream("personal_notes.dat");
             ObjectOutputStream personalObjectOutputStream = new ObjectOutputStream(personalOutputStream);
             FileOutputStream workOutputStream = new FileOutputStream("work_notes.dat");
             ObjectOutputStream workObjectOutputStream = new ObjectOutputStream(workOutputStream))
        {

            personalObjectOutputStream.writeObject(personalNotes);
            System.out.println("Personal notes saved successfully!");

            workObjectOutputStream.writeObject(workNotes);
            System.out.println("Work notes saved successfully!");

        } catch (IOException e)
        {
            System.out.println("Error saving notes: " + e.getMessage());
        }
    }
}