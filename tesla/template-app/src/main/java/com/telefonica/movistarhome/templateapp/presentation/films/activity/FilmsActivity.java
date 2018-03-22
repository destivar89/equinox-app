package com.telefonica.movistarhome.templateapp.presentation.films.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.telefonica.movistarhome.templateapp.domain.model.Film;
import com.telefonica.movistarhome.templateapp.presentation.commons.BaseActivity;
import com.telefonica.movistarhome.templateapp.presentation.films.FilmsContract;
import com.telefonica.movistarhome.templateapp.presentation.films.presenter.FilmsPresenter;

import java.util.List;

import javax.inject.Inject;


public class FilmsActivity extends BaseActivity implements FilmsContract.View {

    @Inject
    FilmsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.start();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showConnectionError() {
    }

    @Override
    public void showDefaultError() {
    }

    @Override
    public void showError(String errorDescription) {
    }

    @Override
    public void showFilms(List<Film> films) {

    }
}
