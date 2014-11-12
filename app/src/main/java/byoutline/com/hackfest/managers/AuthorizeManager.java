package byoutline.com.hackfest.managers;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.concurrent.atomic.AtomicBoolean;

import byoutline.com.hackfest.events.AuthorizationStatusEvent;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class AuthorizeManager {
    private final AtomicBoolean loggedIn = new AtomicBoolean(false);
    private final Bus bus;

    public AuthorizeManager(Bus bus) {
        this.bus = bus;
    }

    @Produce
    AuthorizationStatusEvent produceStatusEvent() {
        return new AuthorizationStatusEvent(loggedIn.get());
    }

    public void logIn() {
        changeLogIn(true);
    }

    public void logOut() {
        changeLogIn(false);
    }

    private void changeLogIn(boolean newStatus) {
        loggedIn.set(newStatus);
        bus.post(new AuthorizationStatusEvent(newStatus));
    }
}
