package de.hochschulestralsund.quizapp.Entities;

public enum Category {
    GENERAL_KNOWLEDGE("9"),
    ENTERTAINMENT_BOOKS("10"),
    ENTERTAINMENT_FILM("11"),
    ENTERTAINMENT_MUSIC("12"),
    ENTERTAINMENT_MUSICALS_AND_THEATRES("13"),
    ENTERTAINMENT_TELEVISION("14"),
    ENTERTAINMENT_VIDEO_GAMES("15"),
    ENTERTAINMENT_BOARD_GAMES("16"),
    SCIENCE_AND_NATURE("17"),
    SCIENCE_COMPUTERS("18"),
    SCIENCE_MATH("19"),
    MYTHOLOGY("20"),
    SPORTS("21"),
    GEOGRAPHY("22"),
    HISTORY("23"),
    POLITICS("24"),
    ART("25"),
    CELEBRITIES("26"),
    ANIMALS("27"),
    VEHICLES("28"),
    ENTERTAINMENT_COMICS("29"),
    SCIENCE_GADGETS("30"),
    ENTERTAINMENT_JAPANESE_ANIME_AND_MANGA("31"),
    ENTERTAINMENT_CARTOON_ANIMATIONS("32");


    private String value;
    Category(String value){ this.value=value;}

    public String getValue(){
        return value;
    }


}
