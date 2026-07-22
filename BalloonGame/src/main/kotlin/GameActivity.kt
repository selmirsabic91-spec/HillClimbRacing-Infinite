package com.balloons.game

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var tvScore: TextView
    private lateinit var tvTime: TextView
    private var gameMode: GameMode = GameMode.TAP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvScore = findViewById(R.id.tv_score)
        tvTime = findViewById(R.id.tv_time)
        val gameContainer = findViewById<FrameLayout>(R.id.game_container)

        val modeOrdinal = intent.getIntExtra("GAME_MODE", 0)
        gameMode = GameMode.values()[modeOrdinal]

        gameView = GameView(this, gameMode)
        gameView.setScoreListener { score, time ->
            tvScore.text = "Score: $score"
            tvTime.text = "Time: ${time}s"
        }

        gameContainer.addView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        gameView.pause()
        super.onPause()
    }
}
