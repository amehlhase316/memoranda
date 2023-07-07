package main.java.memoranda.util;

public class Trainer {
    private String name;
    private String trainingRank;
    private String belt;

    public Trainer(String name, String trainingRank, String belt) {
        this.name = name;
        this.trainingRank = trainingRank;
        this.belt = belt;
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
}
