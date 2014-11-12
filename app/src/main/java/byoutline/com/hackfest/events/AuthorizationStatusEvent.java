package byoutline.com.hackfest.events;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class AuthorizationStatusEvent {
    public final boolean loggedIn;

    public AuthorizationStatusEvent(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
