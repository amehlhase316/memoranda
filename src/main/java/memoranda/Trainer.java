package main.java.memoranda;

public class Trainer {
    private Student studentProfile;
    private final String name;
    private String trainingRank;
    private String belt;
    /*
    The belt ranks are as follows:
    white, yellow, orange, purple, blue, blue stripe, green, green stripe, brown1, brown2,
    brown3, black1, black2, black3
    */
    public Trainer(String name, String trainingRank, String belt) {
        this.name = name;
        this.trainingRank = trainingRank;
        this.belt = belt;
        this.studentProfile = new Student(name, belt);
    }

    public void setTrainingRank(String trainingRank) {
        this.trainingRank = trainingRank;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public String getBelt() {
        return belt;
    }

    public String getName() {
        return name;
    }

    public String getTrainingRank() {
        return trainingRank;
    }

    public Student getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(Student studentProfile) {
        this.studentProfile = studentProfile;
    }
}
