package com.google.android.apps.di.view;

import com.google.android.apps.di.AppComponent;
import com.google.android.apps.ui.MainActivity;

import dagger.Component;

/**
 * Created by OldMan on 08.04.2017.
 */
@ViewScope
@Component(modules = {MainModule.class}, dependencies = AppComponent.class)
public interface MainComponent extends AppComponent {
//    MainPresenter getPresenter();
//    MainInteractor getInteractor();

    void inject(MainActivity mainActivity);

//    void inject(MainInteractorImpl interactor);
//    void inject(MainPresenterImpl presenter);
}