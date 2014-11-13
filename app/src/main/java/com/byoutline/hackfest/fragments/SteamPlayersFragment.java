package com.byoutline.hackfest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.byoutline.hackfest.App;
import com.byoutline.hackfest.R;
import com.byoutline.hackfest.adapters.GamerAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class SteamPlayersFragment extends Fragment {


    GamerAdapter adapter;

    @Inject
    Bus bus;

    @InjectView(R.id.players_lv)
    ListView gamersLv;


    public SteamPlayersFragment() {
    }


    public static SteamPlayersFragment getInstance() {
        SteamPlayersFragment fragment = new SteamPlayersFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steam_users_list, container, false);
        ButterKnife.inject(this, rootView);
        App.doDaggerInject(this);
        setUpListeners();
        setUpAdapters();
        return rootView;
    }

    private void setUpAdapters() {
        adapter = new GamerAdapter(getActivity().getApplication());
        gamersLv.setAdapter(adapter);
    }

    private void setUpListeners() {

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
}
