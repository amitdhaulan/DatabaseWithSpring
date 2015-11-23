package com.example.mobilerequestcentral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author amitk
 */
public class Splash extends Activity {
    Context context;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcomescreen);
        context = this;

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.schedule(new handlerTask(), 3, TimeUnit.SECONDS);

    }

    class handlerTask implements Runnable {

        @Override
        public void run() {

            startActivity(new Intent(Splash.this, LogIn.class));
            finish();
        }
    }
}
