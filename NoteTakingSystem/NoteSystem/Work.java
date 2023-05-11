package NoteSystem;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Work extends Note implements Postponable, Cloneable, Serializable
{
    private static int numberOfWorkNotes = 0;
    private List<String> personNames;
    private List<String> credentials;
    private String meetingLink;

    // constructors
    public Work()
    {
        super();
    }
    public Work(String title, String shortDescription, String details, LocalDateTime deadline,
                String meetingLink)
    {
        super(title, shortDescription, details, deadline);
        super.creationDate = LocalDateTime.now();
        this.personNames = new ArrayList<>();
        this.credentials = new ArrayList<>();;
        this.meetingLink = meetingLink;
        numberOfWorkNotes++;
    }
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Work cloned = (Work) super.clone();
        cloned.personNames = new ArrayList<>(this.personNames);
        cloned.credentials = new ArrayList<>(this.credentials);
        return cloned;
    }
    @Override
    public void createNote() throws NoteException
    {
        if (super.getNoteStatus())
            throw new NoteException("Note is already created");
        else
        {
            super.createNote();
            super.creationDate = LocalDateTime.now();
            incrementNumberOfNotes();
            personNames = new ArrayList<>();
            credentials = new ArrayList<>();
            numberOfWorkNotes++;
        }
    }
    @Override
    public void deleteNote() throws NoteException
    {
        if(!super.getNoteStatus())
            throw new NoteException("Note is already deleted");
        else
        {
            super.deleteNote();
            personNames.removeAll(personNames);
            credentials.removeAll(credentials);
            meetingLink = null;
            numberOfWorkNotes--;
        }
    }
    public boolean pushDeadlineByDays(int days) throws NoteException
    {
        if(!getNoteStatus())
            throw new NoteException("Note is not created");
        else if(days <= 0)
            throw new NegativeDaysException("Negative days parameter", days);
        else
        {
            if (getDeadline() == null)
                return false;
            setDeadline(getDeadline().plusDays(days));
            return true;
        }
    }
    public boolean pushDeadlineByDays() throws NoteException
    {
        if(!getNoteStatus())
            throw new NoteException("Note is not created");
        return pushDeadlineByDays(1);
    }

    @Override
    public String toString()
    {
        StringBuilder names = new StringBuilder();
        for (String name : this.personNames)
            names.append(name).append(", ");
        if (names.length() > 0)
            names.delete(names.length() - 2, names.length());
        StringBuilder creds = new StringBuilder();
        for (String cred : credentials)
            creds.append(cred).append(", ");
        if (creds.length() > 0)
            creds.delete(creds.length() - 2, creds.length());

        return super.toString() + "\nPerson names: " + names.toString() + "\nCredentials: " + creds.toString() +
                "\nMeeting Link: " + meetingLink;
    }

    public static int getNumberOfNotes() { return numberOfWorkNotes; }
    public void addPerson(String name, String credentials)
    {
        this.personNames.add(name);
        this.credentials.add(credentials);
    }
    public void removePerson(String name)
    {
        int index = personNames.indexOf(name);
        if (index != -1)
        {
            this.personNames.remove(index);
            this.credentials.remove(index);
        }
    }


    // get functions
    public List<String> getPersonNames() { return personNames; }
    public List<String> getCredentials() { return credentials; }
    public String getMeetingLink() { return meetingLink; }

    // set functions
    public void setPersonNames(List<String> personNames) { this.personNames = personNames; }
    public void setCredentials(List<String> credentials) { this.credentials = credentials; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
}
