package de.hochschulestralsund.quizapp.Entities;

// Enum for Difficulties
public enum Difficulty {
    EASY("Easy","easy"),
    MEDIUM("Medium","medium"),
    HARD("Hard","hard");

    private String name;
    private String value;

    // Constructor for enum
    Difficulty(String name, String value) {
        this.name=name;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    // Override toString() to correctly display name in Spinner
    @Override
    public String toString(){
        return name;
    }
}
