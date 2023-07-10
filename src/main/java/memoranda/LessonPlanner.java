package main.java.memoranda;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.*;

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

        private String name = "";

        private int lessonStartTime;

        private int lessonEndTime;

        private int month;

        private int date;

        private int privateFlag;
        
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
            //getLessonTimesFromUser(cin);
            getInstructorsFromUser(cin);
            getStudentsFromUser(cin);
            
            cin.close();
        }
        
        public Lesson(int newLessonIDnum, String name, int lessonStartTime, int lessonEndTime, int month, int date, ArrayList<Integer> instructorIDs, ArrayList<Integer> studentIDs, int privateFlag) {
            this.lessonIDnum = newLessonIDnum;
            this.name = name;
            this.lessonStartTime = lessonStartTime;
            this.lessonEndTime = lessonEndTime;
            this.month = month;
            this.date = date;
            this.instructorIDs = instructorIDs;
            this.studentIDs = studentIDs;
            this.privateFlag = privateFlag;
        }

        public Lesson(){

        }
        
//        private void getLessonTimesFromUser(Scanner cin) {
//            GregorianCalendar newStartTime;
//            GregorianCalendar now;
//
//            int duration;
//            // Get date, then check if lesson time is not in the past
//            do {
//                System.out.print("Enter the numerical year: ");
//                int year = Integer.valueOf(cin.nextLine());
//                System.out.print("Enter the numerical month: ");
//                int month = Integer.valueOf(cin.nextLine());
//                System.out.print("Enter the numerical day: ");
//                int dayOfMonth = Integer.valueOf(cin.nextLine());
//                System.out.print("Enter the numerical hour: ");
//                int hourOfDay = Integer.valueOf(cin.nextLine());
//                System.out.print("Enter the numerical minute: ");
//                int minute = Integer.valueOf(cin.nextLine());
//                System.out.print("Enter the duration of the lesson in minutes: ");
//                duration = Integer.valueOf(cin.nextLine());
//
//                newStartTime = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
//                now = new GregorianCalendar();
//
//                if(newStartTime.compareTo(now) <= 0) {
//                    System.out.println("Invalid entry, new lesson cannot take place in the past.");
//                }
//                // If newStartTime.compareTo(now) is greater than 0,
//                // then the newStartTimeis in the future of now.
//            } while (newStartTime.compareTo(now) <= 0);
//
//
//            // Set start time
//            setStartTime(newStartTime);
//
//            // Add duration to start time to calculate end time
//            newStartTime.add(GregorianCalendar.MINUTE, duration);
//            setEndTime(newStartTime);
//        }
        
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
        public int getLessonIDnum() { return lessonIDnum; }
        public String getName(){ return name; }
        public int getStartTime() {
            return lessonStartTime;
        }
        public int getEndTime() {
            return lessonEndTime;
        }
        public int getMonth() { return month;}
        public int getDate(){ return date; }
        public int getPrivateFlag(){ return privateFlag; }
        // Setters
        private void setLessonIDnum(int newLessonIDnum){
            lessonIDnum = newLessonIDnum;
        }
        private void setName(String newName){
            name = newName;
        }
        private void setStartTime(int newStartTime) {
            lessonStartTime = newStartTime;
        }
        
        private void setEndTime(int newEndTime) {
            lessonEndTime = newEndTime;
        }

        private void setMonth(int newMonth){
            month = newMonth;
        }

        private void setDate(int newDate){
            date = newDate;
        }
        
        private void setInstructorIDs(ArrayList<Integer> newInstructorIDs) {
            instructorIDs = newInstructorIDs;
        }
        
        private void setStudentIDs(ArrayList<Integer> newStudentIDs) {
            studentIDs = newStudentIDs;
        }

        private void setPrivateFlag(int newPrivateFlag){
            privateFlag = newPrivateFlag;
        }

        private String getLessonAsJson(Lesson lesson){
            JSONObject json = new JSONObject();
            json.put("id", String.valueOf(lessonIDnum));
            json.put("name", name);
            json.put("start time", String.valueOf(lessonStartTime));
            json.put("end time", String.valueOf(lessonEndTime));
            json.put("month", String.valueOf(month));
            json.put("date", String.valueOf(date));

            JSONArray instructorArray = new JSONArray();
            for(Integer i:instructorIDs){
                instructorArray.put(i);
            }
            JSONArray studentArray = new JSONArray();
            for(Integer i:studentIDs){
                instructorArray.put(i);
            }
            json.put("instructors", instructorArray);
            json.put("students", studentArray);
            json.put("private flag", privateFlag);
            return json.toString();
        }

        private void saveLessons(){
            JSONArray lessonArray = new JSONArray();
            for(Lesson l:lessons){
                lessonArray.put(getLessonAsJson(l));
            }
            String lessonDataPath = System.getProperty("user.home") + File.separator
                    + ".memoranda" + File.separator + "lessons";

            File file = new File(lessonDataPath);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
                buffer.write(lessonArray.toString());
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void readLessons() throws IOException {
            String lessonDataPath = System.getProperty("user.home") + File.separator
                    + ".memoranda" + File.separator + "lessons";

            File lessonDataDir = new File(lessonDataPath);
            if(lessonDataDir.exists()) {
                JSONArray array = new JSONArray();
                String string = Files.readString(Path.of(lessonDataPath));
                array = new JSONArray(string);
                for(int i = 0; i< array.length(); i++){
                    Lesson lesson = new Lesson();
                    JSONObject json = array.getJSONObject(i);
                    lesson.setLessonIDnum(Integer.parseInt(json.get("id").toString()));
                    lesson.setName(json.get("name").toString());
                    lesson.setStartTime(Integer.parseInt(json.get("start time").toString()));
                    lesson.setEndTime(Integer.parseInt(json.get("end time").toString()));
                    lesson.setMonth(Integer.parseInt(json.get("month").toString()));
                    lesson.setDate(Integer.parseInt(json.get("date").toString()));
                    ArrayList<Integer> instructors = new ArrayList<>();
                    JSONArray instructorsArray = new JSONArray(json.get("instructors"));
                    for(int j = 0; j < instructorsArray.length(); j++){
                        instructors.add(Integer.parseInt(instructorsArray.get(j).toString()));
                    }
                    lesson.setInstructorIDs(instructors);
                    ArrayList<Integer> students = new ArrayList<>();
                    JSONArray studentsArray = new JSONArray(json.get("students"));
                    for(int k = 0; k < studentsArray.length(); k++){
                        students.add(Integer.parseInt(studentsArray.get(k).toString()));
                    }
                    lesson.setStudentIDs(students);
                    lesson.setPrivateFlag(Integer.parseInt(json.get("private flag").toString()));
                }
            }

        }
    }
}