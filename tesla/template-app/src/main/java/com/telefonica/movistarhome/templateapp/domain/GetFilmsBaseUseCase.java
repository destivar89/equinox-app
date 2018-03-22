package com.telefonica.movistarhome.templateapp.domain;

import com.telefonica.movistarhome.templateapp.data.FilmsDataSource;
import com.telefonica.movistarhome.templateapp.domain.commons.BaseUseCase;
import com.telefonica.movistarhome.templateapp.domain.model.Film;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class GetFilmsBaseUseCase extends BaseUseCase<List<Film>,Void> {

    private final FilmsDataSource filmsDataSource;

    @Inject
    public GetFilmsBaseUseCase(FilmsDataSource filmsDataSource){
        this.filmsDataSource = filmsDataSource;
    }

    @Override
    public Observable<List<Film>> buildUseCaseObservable(Void unused) {
        return filmsDataSource.getFilms();
    }

}
