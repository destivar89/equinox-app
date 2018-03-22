package com.telefonica.movistarhome.templateapp.domain.model;

public class Film {
    private int id;
    private String name;
    private String director;
    private String synopsis;
    private String image;

    public Film(int id, String name, String director, String synopsis, String image) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.synopsis = synopsis;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
