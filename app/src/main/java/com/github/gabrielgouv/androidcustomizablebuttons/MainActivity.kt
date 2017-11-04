package com.github.gabrielgouv.androidcustomizablebuttons

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTest.text = "teste"
        btnTest.backgroundColorNormal = Color.BLACK

        supportActionBar!!.title = "Customizable Buttons (API " + Build.VERSION.SDK_INT + ")"

    }
}
