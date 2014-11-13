package com.byoutline.hackfest;

import com.razer.android.nabuopensdk.NabuOpenSDK;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import com.byoutline.hackfest.activities.MainActivity;
import com.byoutline.hackfest.fragments.AuthorizeFragment;
import com.byoutline.hackfest.fragments.SteamPlayersFragment;
import com.byoutline.hackfest.managers.AuthorizeManager;
import dagger.Module;
import dagger.Provides;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
@Module(
        injects = {
                App.class,
                AuthorizeFragment.class,
                SteamPlayersFragment.class,
                MainActivity.class
        }
)
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    NabuOpenSDK provideNabu() {
        return NabuOpenSDK.getInstance(app);
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Singleton
    @Provides
    AuthorizeManager provideAuthorizeManager(Bus bus) {
        AuthorizeManager instance = new AuthorizeManager(bus);
        bus.register(instance);
        return instance;
    }
}
