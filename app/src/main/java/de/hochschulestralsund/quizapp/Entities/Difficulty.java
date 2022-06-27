package de.hochschulestralsund.quizapp.Entities;

public enum Difficulty {
    EASY("Easy","easy"),
    MEDIUM("Medium","medium"),
    HARD("Hard","hard");

    private String name;
    private String value;

    Difficulty(String name, String value) {
        this.name=name;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return name;
    }
}
