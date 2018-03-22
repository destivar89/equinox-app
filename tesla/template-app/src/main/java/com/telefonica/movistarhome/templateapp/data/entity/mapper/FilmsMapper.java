package com.telefonica.movistarhome.templateapp.data.entity.mapper;

import com.telefonica.movistarhome.templateapp.data.entity.FilmEntity;
import com.telefonica.movistarhome.templateapp.domain.model.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmsMapper {

    public Film transform(FilmEntity filmEntity) {
        Film film = null;
        if (filmEntity != null) {
            film = new Film(filmEntity.getId(),
                    filmEntity.getName(),
                    filmEntity.getDirector(),
                    filmEntity.getSynopsis(),
                    filmEntity.getImage());
        }
        return film;
    }

    public List<Film> transformList(List<FilmEntity> filmEntities) {
        List<Film> films = new ArrayList<>();
        for (FilmEntity filmEntity : filmEntities) {
            if (filmEntity != null) {
                films.add(transform(filmEntity));
            }
        }
        return films;
    }
}
