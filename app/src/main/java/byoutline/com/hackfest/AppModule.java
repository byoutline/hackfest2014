package byoutline.com.hackfest;

import com.razer.android.nabuopensdk.NabuOpenSDK;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import byoutline.com.hackfest.fragments.AuthorizeFragment;
import byoutline.com.hackfest.managers.AuthorizeManager;
import dagger.Module;
import dagger.Provides;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
@Module(
        injects = {
                AuthorizeFragment.class,
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
