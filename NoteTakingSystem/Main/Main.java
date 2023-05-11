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

public class Main {
    public static void main(String[] args) {
        ManageNote noteTakingSystem = new ManageNote();
        noteTakingSystem.start();
    }
}