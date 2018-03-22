package com.telefonica.movistarhome.templateapp.presentation.commons;


public abstract class BasePresenter<T> {

    protected T view;

    public void init(T view){
        this.view = view;
    }

    public abstract void start();
    public abstract void stop();

}
