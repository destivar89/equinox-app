package com.telefonica.movistarhome.templateapp.infraestructure.di.films;


import com.telefonica.movistarhome.templateapp.data.FilmsDataSource;
import com.telefonica.movistarhome.templateapp.data.LoginService;
import com.telefonica.movistarhome.templateapp.data.entity.mapper.FilmsMapper;
import com.telefonica.movistarhome.templateapp.domain.GetFilmsBaseUseCase;
import com.telefonica.movistarhome.templateapp.domain.commons.BaseUseCase;
import com.telefonica.movistarhome.templateapp.domain.model.Film;
import com.telefonica.movistarhome.templateapp.presentation.films.FilmsContract;
import com.telefonica.movistarhome.templateapp.presentation.films.activity.FilmsActivity;
import com.telefonica.movistarhome.templateapp.presentation.films.presenter.FilmsPresenter;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FilmsActivityModule {

    @Provides
    static FilmsMapper provideFilmsMapper(){
        return new FilmsMapper();
    }

    @Provides
    static FilmsDataSource provideFilmsDataStore(LoginService apiService, FilmsMapper mapper) {
        return new FilmsDataSource(apiService, mapper);
    }

    @Binds
    abstract FilmsContract.View provideMainView(FilmsActivity filmsActivity);


    @Binds
    abstract FilmsContract.Presenter provideMainPresenter(FilmsPresenter presenter);

    @Binds
    abstract BaseUseCase<List<Film>,Void> provideFilmsUseCase(GetFilmsBaseUseCase filmsBaseUseCase);

}
