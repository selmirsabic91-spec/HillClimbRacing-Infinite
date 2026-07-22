package com.example.hillclimbracing.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.sin
import kotlin.math.atan

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    
    private lateinit var gameThread: GameThread
    private lateinit var car: Car
    private lateinit var terrain: Terrain
    private val paint = Paint()
    private var gasPressed = false
    private var brakePressed = false
    private var gamePaused = false
    private var gameOver = false
    
    init {
        holder.addCallback(this)
        isFocusable = true
    }
    
    fun resume() {
        gamePaused = false
    }
    
    fun pause() {
        gamePaused = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        car = Car()
        terrain = Terrain(width, height)
        gameThread = GameThread(holder, this)
        gameThread.setRunning(true)
        gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameThread.setRunning(false)
        try {
            gameThread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val width = width

        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (event.x > width / 2) {
                    // Gas pedal (desno)
                    gasPressed = true
                    brakePressed = false
                } else {
                    // Brake pedal (levo)
                    brakePressed = true
                    gasPressed = false
                }
            }
            MotionEvent.ACTION_UP -> {
                gasPressed = false
                brakePressed = false
            }
        }
        return true
    }

    fun update() {
        if (gamePaused || gameOver) return
        
        // Update car physics
        if (gasPressed) {
            car.accelerate()
        } else if (brakePressed) {
            car.brake()
        } else {
            car.coast()
        }

        // Update automobile position on terrain
        car.update(terrain)

        // Update camera (follow automobile)
        terrain.updateCamera(car)
        
        // Check if car fell
        if (car.position.y > height + 200) {
            gameOver = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Background color (sky)
        canvas.drawColor(Color.parseColor("#87CEEB"))

        // Draw terrain
        terrain.draw(canvas)

        // Draw automobile
        car.draw(canvas)

        // Draw HUD
        drawHUD(canvas)
        
        if (gameOver) {
            drawGameOver(canvas)
        }
    }

    private fun drawHUD(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.textSize = 40f
        paint.isAntiAlias = true
        paint.isFakeBoldText = true

        // Fuel (INFINITE!)
        canvas.drawText("⛽ BENZIN: ∞", 30f, 60f, paint)

        // Speed
        canvas.drawText("Brzina: ${car.velocity.x.toInt()} km/h", 30f, 120f, paint)

        // Distance
        canvas.drawText("Distanca: ${car.distanceTraveled.toInt()} m", 30f, 180f, paint)
        
        // Instructions
        paint.textSize = 28f
        paint.color = Color.YELLOW
        canvas.drawText("← KOČENJE | GAS →", 30f, 240f, paint)
    }
    
    private fun drawGameOver(canvas: Canvas) {
        paint.color = Color.argb(200, 0, 0, 0)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        
        paint.color = Color.RED
        paint.textSize = 80f
        paint.isFakeBoldText = true
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("GAME OVER", width / 2f, height / 2f, paint)
        
        paint.color = Color.WHITE
        paint.textSize = 50f
        canvas.drawText("Distanca: ${car.distanceTraveled.toInt()} m", width / 2f, height / 2f + 80f, paint)
    }
}
