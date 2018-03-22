package com.telefonica.movistarhome.templateapp.presentation.films.presenter;


import com.telefonica.movistarhome.templateapp.domain.GetFilmsBaseUseCase;
import com.telefonica.movistarhome.templateapp.domain.commons.BaseObserver;
import com.telefonica.movistarhome.templateapp.domain.model.Film;
import com.telefonica.movistarhome.templateapp.presentation.commons.BasePresenter;
import com.telefonica.movistarhome.templateapp.presentation.films.FilmsContract;

import java.util.List;

import javax.inject.Inject;

public class FilmsPresenter extends BasePresenter<FilmsContract.View> implements FilmsContract.Presenter{

    private GetFilmsBaseUseCase getFilmsUseCase;

    @Inject
    public FilmsPresenter(FilmsContract.View view, GetFilmsBaseUseCase getFilmsUseCase) {
        init(view);
        this.getFilmsUseCase = getFilmsUseCase;
    }

    @Override
    public void start() {
        loadFilms();
    }

    @Override
    public void stop() {
    }

    private void loadFilms() {
        view.showLoading();
        getFilmsUseCase.execute(new BaseObserver<List<Film>>() {
            @Override
            public void onNext(List<Film> films) {
                view.showFilms(films);
            }

            @Override
            public void onComplete() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable exception) {
                view.hideLoading();
                view.showError(exception.getLocalizedMessage());
            }

        }, null);
    }

}
