package com.eternal.rolly_roll;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.Leaderboard;

public class GPGSManager {
    private static final String TAG = "GPGS Manager";

    private Context context;

    private GoogleSignInClient signInClient;
    private GoogleSignInAccount account;

    private AchievementsClient achievementsClient;
    private LeaderboardsClient leaderboardsClient;

    public GPGSManager(Context context) {
        this.context = context;

        signInClient = GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());

    }
}
