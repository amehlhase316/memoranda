package main.java.memoranda;

public class Student {
    private String name;
    private String belt;
    /*
    The belt ranks are as follows:
    white, yellow, orange, purple, blue, blue stripe, green, green stripe, brown1, brown2,
    brown3, black1, black2, black3
    */

    public Student(String name, String belt) {
        this.name = name;
        this.belt = belt;
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
}
