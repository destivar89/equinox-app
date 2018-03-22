package com.telefonica.movistarhome.templateapp.presentation.commons;

public interface BaseView {
    void showLoading();

    void hideLoading();

    void showConnectionError();

    void showDefaultError();

    void showError(String errorDescription);
}
