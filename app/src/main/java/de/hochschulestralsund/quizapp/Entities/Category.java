package de.hochschulestralsund.quizapp.Entities;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Category {
    GENERAL_KNOWLEDGE("General Knowledge","9"),
    ENTERTAINMENT_BOOKS("Entertainment: Books","10"),
    ENTERTAINMENT_FILM("Entertainment: Film","11"),
    ENTERTAINMENT_MUSIC("Entertainment: Music","12"),
    ENTERTAINMENT_MUSICALS_AND_THEATRES("Musicals and Theatres","13"),
    ENTERTAINMENT_TELEVISION("Entertainment: Television","14"),
    ENTERTAINMENT_VIDEO_GAMES("Entertainment: Video Games","15"),
    ENTERTAINMENT_BOARD_GAMES("Entertainment: Board Games","16"),
    SCIENCE_AND_NATURE("Science & Nature","17"),
    SCIENCE_COMPUTERS("Science: Computers","18"),
    SCIENCE_MATH("Science: Math","19"),
    MYTHOLOGY("Mythology","20"),
    SPORTS("Sports","21"),
    GEOGRAPHY("Geography","22"),
    HISTORY("History","23"),
    POLITICS("Politics","24"),
    ART("Art","25"),
    CELEBRITIES("Celebrities","26"),
    ANIMALS("Animals","27"),
    VEHICLES("Vehicles","28"),
    ENTERTAINMENT_COMICS("Entertainment: Comics","29"),
    SCIENCE_GADGETS("Science: Gadgets","30"),
    ENTERTAINMENT_JAPANESE_ANIME_AND_MANGA("Entertainment: Japanese Anime & Manga","31"),
    ENTERTAINMENT_CARTOON_ANIMATIONS("Entertainment: Cartoon & Animations", "32");

    private String name;
    private String id;

    Category(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return name;
    }

}
