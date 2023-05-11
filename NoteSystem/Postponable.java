package NoteSystem;

interface Postponable extends Creatable
{
    boolean pushDeadlineByDays(int days) throws NoteException;
    boolean pushDeadlineByDays() throws NoteException;
}
