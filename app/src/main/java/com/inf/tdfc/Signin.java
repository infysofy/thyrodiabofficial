package com.inf.tdfc;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class Signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}
