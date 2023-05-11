package NoteSystem;
import java.time.LocalDateTime;

public abstract class Note implements Creatable
{
    private static final int MAX_NUMBER_OF_NOTES = 100;
    private static int numberOfNotes = 0;

    private String title;
    private String shortDescription;
    private String details;
    protected LocalDateTime creationDate;
    private LocalDateTime deadline;
    private boolean afterDeadline = false;
    private boolean noteCreated = false;

    // constructors
    public Note() {}
    public Note(String title, String shortDescription, String details, LocalDateTime deadline)
    {
        this();
        this.title = title;
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
        this.noteCreated = true;
    }


    // get functions
    public static int getNumberOfNotes() { return numberOfNotes; }
    public String getTitle() { return this.title; }
    public String getShortDescription() { return this.shortDescription; }
    public String getDetails() { return this.details; }
    public LocalDateTime getDeadline() { return this.deadline; }
    public boolean getNoteStatus() { return this.noteCreated; }

    // set functions
    public void setTitle(String title) { this.title = title; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setDetails(String details) { this.details = details; }
    public void setDeadline(LocalDateTime deadline){ this.deadline = deadline; }
    public void setNoteStatus(boolean status) { this.noteCreated = status; }


    public static void incrementNumberOfNotes()
    {
        if(numberOfNotes < MAX_NUMBER_OF_NOTES)
            numberOfNotes++;
    }
    @Override
    public String toString()
    {
        return "\nTitle: " + title + "\nShort description: " + shortDescription +
                "\nImportant details: " + details + "\nDeadline: " + deadline +
                "\nCreation date: " + creationDate;
    }
    public void createNote() throws NoteException
    {
        if (getNoteStatus())
            throw new NoteException("Note is already created");
        else
        {
            this.creationDate = LocalDateTime.now();
            setNoteStatus(true);
            incrementNumberOfNotes();
        }
    }
    public void deleteNote() throws NoteException
    {
        if(!getNoteStatus())
            throw new NoteException("Note is already deleted");
        else
        {
            setNoteStatus(false);
            this.title = null;
            this.shortDescription = null;
            this.details = null;
            this.creationDate = null;
            this.deadline = null;
            numberOfNotes--;
        }
    }

    // methods that cannot be overridden
    public final boolean isEventToday()
    {
        LocalDateTime today = LocalDateTime.now();
        return (today.getMonthValue() == today.getMonthValue() &&
                today.getDayOfMonth() == today.getDayOfMonth());
    }
    public final void isOverdue()
    {
        LocalDateTime today = LocalDateTime.now();
        afterDeadline = (getDeadline() != null && today.isAfter(getDeadline()));
    }

}
