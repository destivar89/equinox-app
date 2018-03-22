package com.telefonica.movistarhome.templateapp.data;


import com.telefonica.movistarhome.templateapp.data.entity.mapper.FilmsMapper;
import com.telefonica.movistarhome.templateapp.domain.model.Film;

import java.util.List;

import io.reactivex.Observable;

public class FilmsDataSource {

    private LoginService filmsService;
    private FilmsMapper mapper;

    public FilmsDataSource(LoginService filmsService, FilmsMapper mapper) {
        this.filmsService = filmsService;
        this.mapper = mapper;
    }

    public Observable<List<Film>> getFilms() {
        return filmsService.getFilms().map(filmEntities -> mapper.transformList(filmEntities));
    }
}
