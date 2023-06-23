package main.java.memoranda;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Scanner;

public class LessonPlanner {
    private int lessonCounter = 0;
    
    ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    
    public void createLesson() {
        // Create new lesson with current counter number, then increment counter
        lessons.add(new Lesson(lessonCounter++));
        System.out.println("Lesson successfully created. Total number of lessons: " + lessons.size() + ".");
    }
    
    class Lesson {
        private int lessonIDnum;
        // GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute)
        private GregorianCalendar lessonStartTime;
        private GregorianCalendar lessonEndTime;
        
        // TODO Store more information about instructors/students?
        // Also should connect to classes storing relevant data
        private ArrayList<Integer> instructorIDs;
        private ArrayList<Integer> studentIDs;
        

        // Constructor with no inputs, inputs required
        public Lesson(int newLessonIDnum) {
            lessonIDnum = newLessonIDnum;
            
            Scanner cin = new Scanner(System.in);
            
            // TODO Run more checks on these three functions to ensure input is
            // correct and doesn't throw errors, also acquire data through some
            // other way than terminal or properly implement terminal.
            getLessonTimesFromUser(cin);
            getInstructorsFromUser(cin);
            getStudentsFromUser(cin);
            
            cin.close();
        }
        
        public Lesson(int newLessonIDnum, GregorianCalendar lessonStartTime, GregorianCalendar lessonEndTime, ArrayList<Integer> instructorIDs, ArrayList<Integer> studentIDs) {
            lessonIDnum = newLessonIDnum;
            this.lessonStartTime = lessonStartTime;
            this.lessonEndTime = lessonEndTime;
            this.instructorIDs = instructorIDs;
            this.studentIDs = studentIDs;
        }
        
        private void getLessonTimesFromUser(Scanner cin) {
            GregorianCalendar newStartTime;
            GregorianCalendar now;
            
            int duration;
            // Get date, then check if lesson time is not in the past
            do {
                System.out.print("Enter the numerical year: ");
                int year = Integer.valueOf(cin.nextLine());
                System.out.print("Enter the numerical month: ");
                int month = Integer.valueOf(cin.nextLine());
                System.out.print("Enter the numerical day: ");
                int dayOfMonth = Integer.valueOf(cin.nextLine());
                System.out.print("Enter the numerical hour: ");
                int hourOfDay = Integer.valueOf(cin.nextLine());
                System.out.print("Enter the numerical minute: ");
                int minute = Integer.valueOf(cin.nextLine());
                System.out.print("Enter the duration of the lesson in minutes: ");
                duration = Integer.valueOf(cin.nextLine());
                
                newStartTime = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
                now = new GregorianCalendar();
                
                if(newStartTime.compareTo(now) <= 0) {
                    System.out.println("Invalid entry, new lesson cannot take place in the past.");
                }
                // If newStartTime.compareTo(now) is greater than 0,
                // then the newStartTimeis in the future of now.
            } while (newStartTime.compareTo(now) <= 0);

            
            // Set start time
            setStartTime(newStartTime);
            
            // Add duration to start time to calculate end time
            newStartTime.add(GregorianCalendar.MINUTE, duration);
            setEndTime(newStartTime);
        }
        
        private void getInstructorsFromUser(Scanner cin) {
            int instructorNum = 0;
            do {
                System.out.print("\nEnter the number of instructors attending: ");
                instructorNum = Integer.valueOf(cin.nextLine());
                if(instructorNum < 1) {
                    System.out.println("Invalid entry, must have at least one instructor.");
                }
            } while (instructorNum < 1);
            
            ArrayList<Integer> tempInstructorIDs = new ArrayList<Integer>();
            for(int i = 0; i < instructorNum; i++) {
                if(i == 0) {
                    // Head instructor will always be in position 0;
                    System.out.print("Enter the ID number of the head instructor: ");
                } else {
                    System.out.print("Enter the ID number of instructor #" + (i + 1) + ": ");
                }
                tempInstructorIDs.add(Integer.valueOf(cin.nextLine()));
            }
            
            setInstructorIDs(tempInstructorIDs);
        }
        
        private void getStudentsFromUser(Scanner cin) {
            System.out.print("\nEnter the number of students attending: ");
            int studentNum = Integer.valueOf(cin.nextLine());
            
            ArrayList<Integer> tempStudentIDs = new ArrayList<Integer>();
            for(int i = 0; i < studentNum; i++) {
                System.out.print("Enter the ID number of attendee #" + (i + 1) + ": ");
                tempStudentIDs.add(Integer.valueOf(cin.nextLine()));
            }
            cin.close();
            
            setStudentIDs(tempStudentIDs);
        }
        
        // Getters
        public GregorianCalendar getStartTime() {
            return lessonStartTime;
        }
        
        public GregorianCalendar getEndTime() {
            return lessonEndTime;
        }
        
        // Setters
        private void setStartTime(GregorianCalendar newStartTime) {
            lessonStartTime = newStartTime;
        }
        
        private void setEndTime(GregorianCalendar newEndTime) {
            lessonEndTime = newEndTime;
        }
        
        private void setInstructorIDs(ArrayList<Integer> newInstructorIDs) {
            instructorIDs = newInstructorIDs;
        }
        
        private void setStudentIDs(ArrayList<Integer> newStudentIDs) {
            studentIDs = newStudentIDs;
        }
    }
}