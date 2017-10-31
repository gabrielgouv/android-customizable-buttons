package com.github.gabrielgouv.androidcustomizablebuttons

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Customizable Buttons (API " + Build.VERSION.SDK_INT + ")"

    }
}
