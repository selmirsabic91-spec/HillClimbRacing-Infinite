package com.example.hillclimbracing.game

import android.view.SurfaceHolder
import android.util.Log

class GameThread(private val holder: SurfaceHolder, private val gameView: GameView) : Thread() {
    
    private var running = false
    private val fps = 60
    private val frameTime = 1000L / fps // milliseconds
    private val TAG = "GameThread"

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        Log.d(TAG, "Game thread started")
        
        while (running) {
            val startTime = System.currentTimeMillis()

            // Game update
            gameView.update()

            // Drawing
            val canvas = holder.lockCanvas()
            if (canvas != null) {
                try {
                    synchronized(holder) {
                        gameView.onDraw(canvas)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error drawing", e)
                } finally {
                    try {
                        holder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error unlocking canvas", e)
                    }
                }
            }

            // Maintain FPS
            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime < frameTime) {
                try {
                    Thread.sleep(frameTime - elapsedTime)
                } catch (e: InterruptedException) {
                    Log.e(TAG, "Thread interrupted", e)
                }
            }
        }
        
        Log.d(TAG, "Game thread ended")
    }
}
