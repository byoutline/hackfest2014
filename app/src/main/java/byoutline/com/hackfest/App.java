package byoutline.com.hackfest;

import android.app.Application;

import dagger.ObjectGraph;
import timber.log.Timber;

import static timber.log.Timber.plant;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class App extends Application {

    private static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new AppModule(this));
        if (BuildConfig.DEBUG) {
            plant(new Timber.DebugTree());
        }
    }

    public static void doDaggerInject(Object o) {
        objectGraph.inject(o);
    }
}
