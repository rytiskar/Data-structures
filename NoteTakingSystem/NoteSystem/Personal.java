package NoteSystem;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Personal extends Note implements Creatable, Cloneable, Serializable
{
    private ArrayList<String> shoppingList;
    private ArrayList<String> birthdayGiftIdeas;
    private static int numberOfPersonalNotes = 0;

    public Personal() {}

    public Personal(String title, String shortDescription, String details, LocalDateTime deadline)
    {
        super(title, shortDescription, details, deadline);
        super.creationDate = LocalDateTime.now();
        shoppingList = new ArrayList<>();
        birthdayGiftIdeas = new ArrayList<>();
        numberOfPersonalNotes++;
    }

    public ArrayList<String> getShoppingList() { return shoppingList; }
    public ArrayList<String> getBirthdayGiftIdeas() { return birthdayGiftIdeas; }
    public static int getNumberOfNotes() { return numberOfPersonalNotes; }
    //public LocalDateTime getCreationDate() { return super.creationDate; }
    public void addItemToShoppingList(String item) { shoppingList.add(item); }
    public void addBirthdayGiftIdea(String idea) { birthdayGiftIdeas.add(idea); }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Personal cloned = (Personal) super.clone();
        cloned.shoppingList = new ArrayList<String>(this.shoppingList);
        cloned.birthdayGiftIdeas = new ArrayList<String>(this.birthdayGiftIdeas);
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
            shoppingList = new ArrayList<>();
            birthdayGiftIdeas = new ArrayList<>();
            numberOfPersonalNotes++;
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
            shoppingList.removeAll(shoppingList);
            birthdayGiftIdeas.removeAll(birthdayGiftIdeas);
            numberOfPersonalNotes--;
        }
    }

    @Override
    public String toString()
    {
        String result = super.toString();
        if (!shoppingList.isEmpty())
        {
            result += "\nShopping List:\n";
            for (String item : shoppingList)
                result += "- " + item + "\n";
        }
        if (!birthdayGiftIdeas.isEmpty())
        {
            result += "Birthday Gift Ideas:\n";
            for (String idea : birthdayGiftIdeas)
                result += "- " + idea + "\n";
        }
        return result;
    }
}