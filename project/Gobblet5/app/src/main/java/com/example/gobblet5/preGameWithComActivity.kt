package com.example.gobblet5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_pre_game_with_man.*

class preGameWithComActivity : BaseClass() {
    var radio:RadioGroup? =null
    var playFirst:Int?= null
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_game_with_com)

        iniPlayFirst()
        iniRadioButtons()
        iniAD()

        backButton.setOnClickListener {
            playSound(cancelSE)
            val intent = Intent(this,MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        gameStartBtn.setOnClickListener {
            playSound(gameStartSE)
            val intent = Intent(this, GameWithComActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniPlayFirst(){
        playFirst=pref?.getInt("playFirst", 1)
        when(playFirst){
            1 -> {findViewById<RadioButton>(R.id.Button1p).isChecked = true}
            -1 -> {findViewById<RadioButton>(R.id.Button2p).isChecked = true}
            0 -> {findViewById<RadioButton>(R.id.ButtonRandom).isChecked = true}
        }
    }

    private fun iniRadioButtons(){
        radio = findViewById<RadioGroup>(R.id.RadioGroup)
        radio!!.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.Button1p->{playFirst=1}
                R.id.Button2p->{playFirst= -1}
                R.id.ButtonRandom->{playFirst=0}
            }
            playSound(radioButtonSE)
            val editor=pref!!.edit()
            editor.putInt("playFirst",playFirst!!).apply()
            Log.d("gobblet2", "${playFirst}")
        }
    }

    private fun iniAD(){
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}