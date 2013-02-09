package com.wawproject.startup;

import android.app.Activity;
import android.os.Bundle;

public class SampleMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.activity_main_title));

    }
}