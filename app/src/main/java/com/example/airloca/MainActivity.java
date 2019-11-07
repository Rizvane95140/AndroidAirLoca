package com.example.airloca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncSleep asyncSleep = new AsyncSleep();
        asyncSleep.execute();
    }

    public class AsyncSleep extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s ) {
            //Handle result here
            super.onPostExecute(s);

            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);

        }
    }


}
