package com.likith_pp.SearchTypeAhead;


public class Suggestion {
    private Long id;

    public Suggestion(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    private String name;

    private int popularity;
}
