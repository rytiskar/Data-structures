package NoteSystem;

public class NegativeDaysException extends NoteException
{
    public int days;
    public NegativeDaysException(String message, int days)
    {
        super(message);
        this.days = days;
    }
}
