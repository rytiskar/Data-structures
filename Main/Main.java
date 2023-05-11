package Main;
import NoteSystem.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        LocalDateTime deadLineDate = LocalDateTime.of(2023, 04, 12, 23, 59);

        // Child class object for work notes
        Personal note1 = new Personal("Birthday", " - ", " - ",
                deadLineDate);
        note1.addItemToShoppingList("Snacks");
        note1.addItemToShoppingList("Drinks");
        Personal note2 = new Personal();

        // Padarau objekto klona
        try{
            note2 = (Personal) note1.clone();
        } catch (CloneNotSupportedException cnse)
        {
            System.out.println(cnse);
        }

        // Isvedu originalaus objekto ir klono duomenis i ekrana
        System.out.println(note1);
        System.out.println(note2);

        // Pakeiciu kurio nors is objektu lauka
        note1.addItemToShoppingList("Gift");
        // Jei klonavimas yra deep, tai keiciamas laukas pasikeicia tik vienam is objektu
        // Jei klonavimas yra shallow, tai laukas pasikeicia abiejuose objektuose
        System.out.println(note1);
        System.out.println(note2);

    }
}