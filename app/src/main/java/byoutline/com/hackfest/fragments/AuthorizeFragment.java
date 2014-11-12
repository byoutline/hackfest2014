package byoutline.com.hackfest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import byoutline.com.hackfest.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorizeFragment extends Fragment {

    public AuthorizeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authorize, container, false);
        return rootView;
    }
}
