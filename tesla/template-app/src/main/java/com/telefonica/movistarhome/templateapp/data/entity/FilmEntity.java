package com.telefonica.movistarhome.templateapp.data.entity;

public class FilmEntity {
    private int id;
    private String name;
    private String director;
    private String synopsis;
    private String image;

    public FilmEntity(int id, String name, String director, String synopsis, String image) {
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

    public String getSynopsis() {
        return synopsis;
    }

    public String getImage() {
        return image;
    }
}
