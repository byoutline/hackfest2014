package com.byoutline.hackfest.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.byoutline.hackfest.App;
import com.byoutline.hackfest.R;
import com.byoutline.hackfest.adapters.GamerAdapter;
import com.byoutline.hackfest.api.ApiClient;
import com.byoutline.hackfest.api.PlayerDetails;
import com.byoutline.hackfest.api.SteamDetails;
import com.byoutline.hackfest.views.RadioButtonFont;
import com.byoutline.secretsauce.utils.ViewUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.razer.android.nabuopensdk.NabuOpenSDK;
import com.razer.android.nabuopensdk.interfaces.Hi5Listener;
import com.razer.android.nabuopensdk.interfaces.PulseListener;
import com.razer.android.nabuopensdk.models.Hi5Data;
import com.razer.android.nabuopensdk.models.PulseData;
import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class SteamPlayersFragment extends Fragment {


    GamerAdapter adapter;

    @Inject
    Bus bus;
    @Inject
    NabuOpenSDK nabuSDK;
    @Inject
    ApiClient.SteamApiClient steamApi;

    @InjectView(R.id.players_lv)
    ListView gamersLv;
    @InjectView(R.id.fav_gamers_rbtn)
    RadioButtonFont favGamers;
    @InjectView(R.id.nearby_gamers_rbtn)
    RadioButtonFont nearbyGamers;
    @InjectView(R.id.logo_ll)
    View logoLl;
    @InjectView(R.id.dummy_id)
    RadioGroup radioGroup;


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
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                ViewUtils.showView(gamersLv,true);
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
                ViewUtils.showView(gamersLv,true);
            }
        });
    }

    private void setUpListeners() {
        gamersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlayerDetails item = adapter.getItem(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.profileurl));
                startActivity(browserIntent);
            }
        });



//        nearbyGamers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//
//                }
//            }
//        });

    }


    @OnClick(R.id.nearby_gamers_rbtn)
    public void onClick(){
        adapter.clear();
        queryPulseData();;
    }

    private void startPulseAnimation() {
        Animation pulseAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pulse_animation);
        logoLl.startAnimation(pulseAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
        startPulseAnimation();
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                queryPulseData();
            }
        },2000);

    }

    @Override
    public void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    private void queryPulseData() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -1);

        nabuSDK.getPulseData(getActivity(), c.getTimeInMillis(), System.currentTimeMillis(), new PulseListener() {

            @Override
            public void onReceiveFailed(String arg0) {
                Timber.e(arg0);
            }

            @Override
            public void onReceiveData(PulseData[] arg0) {
                final String steamKey = "7A529CCA4DDA1D8E9375E81AC460B27F";
                Set<String> openIds = new HashSet<>();
                for (PulseData pulse : arg0) {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(pulse.timeStamp * 1000);
                    openIds.addAll(Arrays.asList(pulse.openIds));
                }
                for (String openId : openIds) {
                    Timber.d("Nearby openid: " + openId);
                }
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("SteamUser").whereContainedIn("openid", openIds);
//                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("SteamUser");//.whereEqualTo("openid", sebastianOpenId);
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        Timber.d("Parse returned");

                        for (ParseObject parseObject : parseObjects) {
                            Timber.d("Returned steamId: " + parseObject.getString("steamid"));
                            steamApi.getSteamDetails(steamKey, parseObject.getString("steamid"), new Callback<SteamDetails>() {
                                @Override
                                public void success(SteamDetails steamDetails, Response response) {
                                    PlayerDetails playerDetails = steamDetails.response.players.get(0);
                                    Timber.d("Steam returned:" + playerDetails.profileurl);
                                    adapter.clear();
                                    adapter.add(playerDetails);
                                    gamersLv.setAdapter(adapter);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Timber.e(error.getCause(), "Steam api call failed with error");
                                }
                            });
                        }

                    }
                });
            }
        });
    }

    private void queryHi5Data() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        nabuSDK.getHi5Data(getActivity(), c.getTimeInMillis(), System.currentTimeMillis(), new Hi5Listener() {

            @Override
            public void onReceiveFailed(String arg0) {
                Timber.e(arg0);
            }

            @Override
            public void onReceiveData(Hi5Data[] arg0) {
                String pawelOpenId = "8605da3c1da1659ef6507ac841a75acb93edd8e32c2257484986bbb0286fc30b";
                String sebastianOpenId = "83bc1c62fa51afee8db0a982574aacf32184ac1418419957e46ebaeadc7632e2";

                Timber.d("User high fived");
                for (Hi5Data hi5Data : arg0) {
                    ParseObject parseObject = new ParseObject("SteamFriend");
                    parseObject.put("from", pawelOpenId);
                    parseObject.put("to", hi5Data.openId);
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Timber.d("high five saved");
                        }
                    });
                }
            }
        });
    }

}
