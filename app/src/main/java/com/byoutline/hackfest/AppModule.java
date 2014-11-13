package com.byoutline.hackfest;

import com.byoutline.hackfest.api.ApiClient;
import com.razer.android.nabuopensdk.NabuOpenSDK;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import com.byoutline.hackfest.activities.MainActivity;
import com.byoutline.hackfest.fragments.AuthorizeFragment;
import com.byoutline.hackfest.fragments.SteamPlayersFragment;
import com.byoutline.hackfest.managers.AuthorizeManager;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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

    @Singleton
    @Provides
    ApiClient.SteamApiClient provideRestApi() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(ApiClient.API_URL);

        builder.setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);

        return builder.build().create(ApiClient.SteamApiClient.class);
    }
}
