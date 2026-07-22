package com.example.hillclimbracing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hillclimbracing.game.GameView

class MainActivity : AppCompatActivity() {
    
    private lateinit var gameView: GameView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Skrivanje status bara i action bara
        actionBar?.hide()
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
        
        gameView = GameView(this)
        setContentView(gameView)
    }
    
    override fun onResume() {
        super.onResume()
        gameView.resume()
    }
    
    override fun onPause() {
        super.onPause()
        gameView.pause()
    }
}
