package byoutline.com.hackfest.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;

import com.byoutline.secretsauce.activities.HostActivity;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class SteamFiveFragment extends Fragment {

    protected HostActivity mHostActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mHostActivity = (HostActivity) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException(activity.getClass().getSimpleName()
                    + " does not implement " + HostActivity.class.getSimpleName()
                    + " interface");
        }
    }

    @Override
    public void onDestroyView() {
        if(!isAdded()) {
            mHostActivity.hideKeyboard();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHostActivity = new StubHostActivity();
    }


    public static interface HostActivity extends com.byoutline.secretsauce.activities.HostActivity {


        void showSteamListFragment();
    }

    private static class StubHostActivity extends com.byoutline.secretsauce.activities.StubHostActivity implements HostActivity {


        @Override
        public void showSteamListFragment() {

        }
    }


}
