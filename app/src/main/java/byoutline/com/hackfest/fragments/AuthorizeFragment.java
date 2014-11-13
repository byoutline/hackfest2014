package byoutline.com.hackfest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byoutline.secretsauce.utils.ViewUtils;
import com.razer.android.nabuopensdk.AuthCheckCallback;
import com.razer.android.nabuopensdk.NabuOpenSDK;
import com.razer.android.nabuopensdk.interfaces.NabuAuthListener;
import com.razer.android.nabuopensdk.interfaces.UserProfileListener;
import com.razer.android.nabuopensdk.models.Scope;
import com.razer.android.nabuopensdk.models.UserProfile;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import byoutline.com.hackfest.App;
import byoutline.com.hackfest.R;
import byoutline.com.hackfest.events.AuthorizationStatusEvent;
import byoutline.com.hackfest.events.Events;
import byoutline.com.hackfest.managers.AuthorizeManager;

import static timber.log.Timber.d;
import static timber.log.Timber.e;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorizeFragment extends Fragment {

    @Inject
    NabuOpenSDK nabuSDK;
    @Inject
    AuthorizeManager authorizeManager;
    @Inject
    Bus bus;

    public AuthorizeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authorize, container, false);
        ButterKnife.inject(this, rootView);
        App.doDaggerInject(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        nabuSDK.onDestroy(getActivity());
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    private void openListFragment() {
        // TODO: write me.
        bus.post(new Events.ShowGamersListFragment());
//        mHostActivity.showSteamListFragment();
        d("open list fragment");

    }

    @OnClick(R.id.authorize_continue_tv)
    public void onAuthorizeClicked() {
        //TODO: remove me

        bus.post(new Events.ShowGamersListFragment());
        nabuSDK.initiate(getActivity(), "ec47877454906fd268286676ef549d0736965485", new String[]{Scope.SCOPE_FITNESS}, new NabuAuthListener() {

            @Override
            public void onAuthSuccess(String arg0) {
                d("Authentication Success", arg0);
                authorizeManager.logIn("steamIdTODO", "openIdTODO");
//                queryUserProfileData();
            }

            @Override
            public void onAuthFailed(String arg0) {
                e("Authentication Failed", arg0);
                authorizeManager.logOut();
            }
        });

    }

    private void queryUserProfileData() {
        nabuSDK.getUserProfile(getActivity(), new UserProfileListener() {


            @Override
            public void onReceiveData(UserProfile arg0) {
                authorizeManager.logIn(arg0.nickName, arg0.razerid);
            }

            @Override
            public void onReceiveFailed(String arg0) {
                e(arg0);
            }
        });
    }

    @Subscribe
    public void onAuthorizationChange(AuthorizationStatusEvent event) {
        if (event.loggedIn) {
            openListFragment();
        }
    }


    private void checkApp() {
        nabuSDK.checkAppAuthorized(getActivity().getApplicationContext(), new AuthCheckCallback() {

            @Override
            public void onSuccess(boolean isAuthorized) {
                // LOGIN SUCCESSFUL
                d("Checking auth succeeded: result " + isAuthorized);
            }

            @Override
            public void onFailed(String errorMessage) {
                e("Checking auth failed");
            }
        });
    }
}
