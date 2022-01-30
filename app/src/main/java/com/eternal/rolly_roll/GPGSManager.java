package com.eternal.rolly_roll;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GPGSManager {
    private static final String TAG = "GPGS Manager";

    private Context context;

    private GoogleSignInClient signInClient;
    private GoogleSignInAccount account;

    private AchievementsClient achievementsClient;
    private LeaderboardsClient leaderboardsClient;

    public GPGSManager(Context context) {
        this.context = context;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build();

        signInClient = GoogleSignIn.getClient(context, gso);

        signInClient.silentSignIn().addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                if (task.isSuccessful()) {
                    achievementsClient = Games.getAchievementsClient(context, task.getResult());
                    leaderboardsClient = Games.getLeaderboardsClient(context, task.getResult());
                } else {

                }
            }
        });

    }
}
