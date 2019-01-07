package main.java.memoranda;

/*$Id: ProjectListener.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public interface ProjectListener {
  void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl);
  void projectWasChanged();
}