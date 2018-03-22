package com.telefonica.movistarhome.templateapp.infraestructure.di.commons;

import com.telefonica.movistarhome.templateapp.infraestructure.di.films.FilmsActivityModule;
import com.telefonica.movistarhome.templateapp.presentation.films.activity.FilmsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FilmsActivityModule.class)
    abstract FilmsActivity bindMainActivity();

}
