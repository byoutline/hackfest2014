package com.byoutline.hackfest.managers;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.concurrent.atomic.AtomicBoolean;

import com.byoutline.hackfest.events.AuthorizationStatusEvent;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class AuthorizeManager {
    private final AtomicBoolean loggedIn = new AtomicBoolean(false);
    private String steamId = "";
    private String openId = "";
    private final Bus bus;

    public AuthorizeManager(Bus bus) {
        this.bus = bus;
    }

    @Produce
    AuthorizationStatusEvent produceStatusEvent() {
        return new AuthorizationStatusEvent(loggedIn.get());
    }

    public void logIn(String steamId, String openId) {
        changeLogIn(true);
        this.steamId = steamId;
        this.openId = openId;
    }

    public void logOut() {
        changeLogIn(false);
        this.steamId = "";
        this.openId = "";
    }

    private void changeLogIn(boolean newStatus) {
        loggedIn.set(newStatus);
        bus.post(new AuthorizationStatusEvent(newStatus));
    }
}
