package byoutline.com.hackfest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.byoutline.secretsauce.utils.ViewUtils;
import com.razer.android.nabuopensdk.NabuOpenSDK;
import com.razer.android.nabuopensdk.interfaces.NabuAuthListener;
import com.razer.android.nabuopensdk.models.Scope;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import byoutline.com.hackfest.App;
import byoutline.com.hackfest.R;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorizeFragment extends Fragment {

    @Inject
    NabuOpenSDK nabuSDK;

    public AuthorizeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authorize, container, false);
        ButterKnife.inject(rootView);
        App.doDaggerInject(this);
        return rootView;
    }

    @OnClick(R.id.authorize_continue_tv)
    public void onAuthorizeClicked() {
        nabuSDK.initiate(getActivity(), "ec47877454906fd268286676ef549d0736965485", new String[]{Scope.SCOPE_FITNESS}, new NabuAuthListener() {

            @Override
            public void onAuthSuccess(String arg0) {
                Timber.e("Authentication Success", arg0);
                ViewUtils.showToast("Huge success");
            }

            @Override
            public void onAuthFailed(String arg0) {
                Timber.d("Authentication Failed", arg0);
            }
        });

    }
}
