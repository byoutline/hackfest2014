package com.byoutline.hackfest.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public abstract class ApiClient {
    public static final String API_URL = "http://api.steampowered.com/ISteamUser";

    public static interface SteamApiClient {
        @GET("/GetPlayerSummaries/v0002/")
        void getSteamDetails(@Query("key") String steamId, @Query("steamids") String steamIds,
                             Callback<SteamDetails> cb);
    }
}
