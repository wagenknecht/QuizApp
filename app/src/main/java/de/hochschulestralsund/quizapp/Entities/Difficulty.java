package de.hochschulestralsund.quizapp.Entities;

public enum Difficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String value;

    Difficulty(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
