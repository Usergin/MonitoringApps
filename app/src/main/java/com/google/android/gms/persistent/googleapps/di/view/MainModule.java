package com.google.android.gms.persistent.googleapps.di.view;

import com.google.android.gms.persistent.googleapps.network.api.ApiService;
import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;
import com.google.android.gms.persistent.googleapps.ui.MainInteractor;
import com.google.android.gms.persistent.googleapps.ui.MainInteractorImpl;
import com.google.android.gms.persistent.googleapps.ui.MainPresenter;
import com.google.android.gms.persistent.googleapps.ui.MainPresenterImpl;
import com.google.android.gms.persistent.googleapps.ui.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 08.04.2017.
 */
@Module
public class MainModule {
    private final MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    MainView provideView() {
        return view;
    }

    @Provides
    MainPresenter provideMainPresenter(MainInteractor interactor) {
        return new MainPresenterImpl(view, interactor);
    }

    @Provides
    MainInteractor provideInteractor(NetworkRepo repo) {
        return new MainInteractorImpl(repo);
    }

}
