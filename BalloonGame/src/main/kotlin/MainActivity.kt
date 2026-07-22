package com.balloons.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTapMode = findViewById<Button>(R.id.btn_tap_mode)
        val btnFallingMode = findViewById<Button>(R.id.btn_falling_mode)
        val btnMatchingMode = findViewById<Button>(R.id.btn_matching_mode)

        btnTapMode.setOnClickListener {
            startGame(GameMode.TAP)
        }

        btnFallingMode.setOnClickListener {
            startGame(GameMode.FALLING)
        }

        btnMatchingMode.setOnClickListener {
            startGame(GameMode.MATCHING)
        }
    }

    private fun startGame(mode: GameMode) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("GAME_MODE", mode.ordinal)
        startActivity(intent)
    }
}

enum class GameMode {
    TAP, FALLING, MATCHING
}
