# Note Taking System

The Note Taking System is a Java application implemented using JavaFX. It provides a graphical user interface (GUI) for users to create, manage, and interact with personal and work-related notes. The program offers various functionalities such as adding, deleting, displaying, sorting, and modifying notes based on their type.

## Purpose

The purpose of the program is to allow users to efficiently organize their personal and work-related tasks. The application simplifies the process of creating and managing notes, helping users stay organized and productive.

## How to run

### Requirements

To ensure the successful execution of the program, ensure that your system meets the following requirements:

- `JDK`: The program relies on the Java Development Kit (JDK), make sure you have the latest version of JDK installed.

- `Java 20.0.1 or above`: Additionally, the program requires Java version 20.0.1 higher to be installed.

### Directory

To run the program, make sure that the following three files are located in the same folder:

- `NoteTakingSystem.jar`: This is the main program file that needs to be present in the same directory.

- `personal_notes.dat`: This binary file is responsible for storing personal notes.

- `work_notes.dat`: This binary file is responsible for storing work-related notes.

### Running

Follow these steps:

1. Open a `Terminal` or `Command line` and navigate to the directory containing the three mentioned files:
   ```
   cd directory
   ```
2. Run the following command:
   ```
   java -jar NoteTakingSystem.jar
   ```
   
## Functionality

- **Add Note:** Users can create personal or work notes by providing the necessary details such as title, description, details, and deadline.

- **Delete Note:** Users can remove a note by specifying an index of the note to be deleted.

- **Display Notes:** Users can view all the notes of a selected type.

- **Sort Notes:** Notes can be sorted by deadline in ascending order.

- **Push Deadline (Work Notes Only):** Users can push the deadline by entering the note index and the number of days to extend the deadline.

- **Save Notes:** Users can save their notes to separate files for personal and work notes.

- **Load Notes:** Users can load previously saved notes from the two separate files.

## Main Classes

### Note Class
- Abstract class representing a note.
- Attributes: `title`, `shortDescription`, `details`, `creationDate`, `deadline`, `afterDeadline`, `noteCreated`.
- Methods: `compareTo(Note other)`, `createNote()`, `deleteNote()`, `isEventToday()`, `isOverdue()`, getters and setters.

### Work Class
- Subclass of `Note` representing work-related notes.
- Additional attributes: `numberOfWorkNotes`, `personNames`, `credentials`, `meetingLink`.
- Methods: `clone()`, overrides `createNote()` and `deleteNote()`, `pushDeadlineByDays()`, `pushDeadlineByDays()`, getters and setters.

### Personal Class
- Subclass of `Note` representing personal notes.
- Additional attributes: `numberOfPersonalNotes`, `shoppingList`, `birthdayGiftIdeas`.
- Methods: `clone()`, overrides `createNote()` and `deleteNote()`, `addItemToShoppingList()`, `addBirthdayGiftIdea()`, getters and setters.

## UML diagram

## Future perspectives
- **Enhanced user interface:** Improve the visual usability of the application interface.
- **Reminder and notifications system:** Implement alerts and notifications for approaching deadlines or important events related to notes.
- **Search and filtering capabilities:** Enable users to search and filter notes based on various criteria such as title, keywords, or tags.
- **Advanced features for creating and modifying notes.** 

## Design patterns
- `Note`: `Serializable` allows object serialization for storage or transmission.
- `Personal`: `Serializable` and `Cloneable` enable serialization and cloning operations.
- `Work`: `Serializable` and `Cloneable` support serialization and cloning operations.


