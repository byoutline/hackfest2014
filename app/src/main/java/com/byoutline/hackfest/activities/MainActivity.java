package com.byoutline.hackfest.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import com.byoutline.hackfest.App;
import com.byoutline.hackfest.R;
import com.byoutline.hackfest.fragments.AuthorizeFragment;
import com.byoutline.hackfest.fragments.SteamPlayersFragment;

import static com.byoutline.hackfest.events.Events.ShowGamersListFragment;


public class MainActivity extends Activity {

    @Inject
    Bus bus;

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AuthorizeFragment())
                    .commit();
        }
        App.doDaggerInject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSteamListFragment(){
        showFragment(SteamPlayersFragment.getInstance(),true);
    }

    public void showFragment(android.app.Fragment fragment, boolean addToBackStack)
    {
        showFragmentWithReplacing(fragment, true, addToBackStack, null);
    }

    public void showFragmentWithReplacing(android.app.Fragment fragment, boolean replace, boolean addToBackStack, int[] animations)
    {
        if (this.mFragmentManager == null) {
            this.mFragmentManager = getFragmentManager();
        }
        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        if (animations != null)
        {
            fragmentTransaction.setTransition(4097);
            fragmentTransaction.setTransitionStyle(R.style.FragAnimation);
            if (animations.length == 2) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1]);
            } else if (animations.length == 4) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
            }
        }
        if (replace) {
            fragmentTransaction.replace(R.id.container, fragment);
        } else {
            fragmentTransaction.add(R.id.container, fragment);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        fragmentTransaction.setCustomAnimations(17498112, 17498113);
    }



    @Subscribe
    public void onAuthorizationChange(ShowGamersListFragment event) {
        showSteamListFragment();
    }

}
