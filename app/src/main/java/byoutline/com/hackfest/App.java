package byoutline.com.hackfest;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.byoutline.secretsauce.Settings;
import com.byoutline.secretsauce.utils.BusWrapper;
import com.byoutline.secretsauce.utils.OttoBusWrapper;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

import static timber.log.Timber.plant;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class App extends Application {


    public static final String DEFAULT_FONT = "BebasNeueBold.ttf";
    private static ObjectGraph objectGraph;
    private Typeface mBebasNoueBold = Typeface.DEFAULT;
    private static App sInstance;
    @Inject
    Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;
        objectGraph = ObjectGraph.create(new AppModule(this));
        doDaggerInject(this);
        if (BuildConfig.DEBUG) {
            plant(new Timber.DebugTree());
        }

        BusWrapper busWrapper = new OttoBusWrapper(bus);
        Settings.set(getApplicationContext(), BuildConfig.DEBUG, R.id.container, busWrapper, DEFAULT_FONT, mBebasNoueBold);
    }

    public static void doDaggerInject(Object o) {
        objectGraph.inject(o);
    }

    public static Context getInstance() {
        if(sInstance == null){
            sInstance = new App();
        }
        return sInstance;
    }
}
