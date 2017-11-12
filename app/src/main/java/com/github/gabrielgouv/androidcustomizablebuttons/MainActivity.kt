package com.github.gabrielgouv.androidcustomizablebuttons


import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customizableButton.setBackgroundColorNormal(Color.parseColor("#F34605"))
        customizableButton.setBackgroundColorPressed(Color.parseColor("#F37402"))
        customizableButton.setTextColorNormal(Color.parseColor("#F3FF82"))
        customizableButton.setTextColorPressed(Color.parseColor("#F34605"))
        customizableButton.text = ("Customizable Bugdgtton")
        customizableButton.textSize = 20F

    }

}
