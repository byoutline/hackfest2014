package byoutline.com.hackfest;

import com.razer.android.nabuopensdk.NabuOpenSDK;

import javax.inject.Singleton;

import byoutline.com.hackfest.fragments.AuthorizeFragment;
import dagger.Module;
import dagger.Provides;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
@Module(
        injects = {
                AuthorizeFragment.class
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
}
