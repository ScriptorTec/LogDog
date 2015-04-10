package de.mvm.android.logdog.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import de.mvm.android.logdog.R;

public class SplashScreenActivity extends ActionBarActivity {

    public static final String TAG = SplashScreenActivity.class.getSimpleName();

    // Minimum duration of the splash screen in milliseconds
    public static final long SPLASH_SCREEN_DURATION = 3000;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        context = getApplicationContext();

        ImageView ivSplashscreenLogo = (ImageView) findViewById(R.id.ivSplashscreenLogo);

        Picasso.with(context).load(R.mipmap.splashscreen).into(ivSplashscreenLogo);

        context = getApplicationContext();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String hawkPassword                 = sharedPreferences.getString("HawkPassword", null);

        // If hawkPassword is not set yet, it will be the current time in milliseconds. This is
        // only to prevent writing a hard coded password for Hawk.
        if(hawkPassword == null) {
            Long time = System.currentTimeMillis();
            hawkPassword = time.toString();
            Log.d(TAG, hawkPassword);
            sharedPreferences.edit().putString("HawkPassword", hawkPassword);
        }

        final long startingTime = System.nanoTime();

        Log.d(TAG, "initialize Hawk...");
        Hawk.init(context, hawkPassword, new Hawk.Callback() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Hawk initialized!");

                // Make sure that the splash screen keeps present, even when Hawk initialization is
                // faster then SPLASH_SCREEN_DURATION.
                long timeDifference = (System.nanoTime() - startingTime) / 1000000;
                if(timeDifference < SPLASH_SCREEN_DURATION) {
                    SystemClock.sleep(SPLASH_SCREEN_DURATION - timeDifference);
                }

                // TODO: start next activity
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }
}
