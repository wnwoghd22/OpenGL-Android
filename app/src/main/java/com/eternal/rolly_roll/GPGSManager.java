package com.eternal.rolly_roll;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.eternal.rolly_roll.util.LoggerConfig;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GPGSManager {
    private static final String TAG = "GPGS Manager";

    public static final int RC_SIGN_IN = 9001;

    private Context context;

    private GoogleSignInClient signInClient;
    public GoogleSignInClient getSignInClient() {
        return signInClient;
    }
    private GoogleSignInAccount signInAccount;

    private AchievementsClient achievementsClient;
    private LeaderboardsClient leaderboardsClient;

    public GPGSManager(Context context) {
        this.context = context;
    }

    public void signInSilently() {
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // already signed in
            if (LoggerConfig.GOOGLE_LOG) {
                Log.w(TAG, "already signed in");
            }

            signInAccount = account;

            if (LoggerConfig.GOOGLE_LOG) {
                Log.w(TAG, "account info : " + signInAccount.getAccount());
            }

        } else {
            signInClient = GoogleSignIn.getClient(context, signInOptions);
            signInClient.silentSignIn().addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    if (task.isSuccessful()) {
                        GoogleSignInAccount result = task.getResult();

                        if (LoggerConfig.GOOGLE_LOG) {
                            Log.w(TAG, "Task succeed" + result.getAccount() + "\n" +
                                    result.getId());
                        }

                        signInAccount = result;

                        context.startActivity(signInClient.getSignInIntent());

                        achievementsClient = Games.getAchievementsClient(context, result);
                        leaderboardsClient = Games.getLeaderboardsClient(context, result);
                    } else {
                        if (LoggerConfig.GOOGLE_LOG) {
                            Log.w(TAG, "Task failed : " + task.getException());
                        }
                    }
                }
            });
        }
    }

}
