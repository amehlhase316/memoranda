package main.java.memoranda;

public interface NoteListener {
  void noteChange(Note note, boolean toSaveCurrentNote);
}
