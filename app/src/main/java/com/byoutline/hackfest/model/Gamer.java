package com.byoutline.hackfest.model;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class Gamer {

    public Gamer(String photoUrl, String nick, String games) {
        this.photoUrl = photoUrl;
        this.nick = nick;
        this.games = games;
    }

    public String photoUrl;
    public String nick;
    public String games;
}
