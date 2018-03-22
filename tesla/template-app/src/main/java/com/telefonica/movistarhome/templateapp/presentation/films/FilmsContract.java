package com.telefonica.movistarhome.templateapp.presentation.films;


import com.telefonica.movistarhome.templateapp.domain.model.Film;
import com.telefonica.movistarhome.templateapp.presentation.commons.BaseView;

import java.util.List;

public interface FilmsContract {

    interface View extends BaseView {
        void showFilms(List<Film> films);
    }

    interface Presenter{
    }

}
