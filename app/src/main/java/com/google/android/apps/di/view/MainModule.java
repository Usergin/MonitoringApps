package com.google.android.apps.di.view;

import com.google.android.apps.data_collection.AboutDevice;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.ui.MainInteractor;
import com.google.android.apps.ui.MainInteractorImpl;
import com.google.android.apps.ui.MainPresenter;
import com.google.android.apps.ui.MainPresenterImpl;
import com.google.android.apps.ui.MainView;
import com.google.android.apps.utils.Preferences;

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
    MainInteractor provideInteractor(NetworkRepo repo, Preferences preferences, AboutDevice device) {
        return new MainInteractorImpl(repo, preferences, device);
    }

}
