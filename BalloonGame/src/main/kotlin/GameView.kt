package com.balloons.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.random
import kotlin.math.sqrt

class GameView(
    context: Context,
    private val gameMode: GameMode,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val balloons = mutableListOf<Balloon>()
    private var score = 0
    private var timeElapsed = 0
    private var gameRunning = true
    private var scoreListener: ((Int, Int) -> Unit)? = null

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    init {
        when (gameMode) {
            GameMode.TAP -> initTapMode()
            GameMode.FALLING -> initFallingMode()
            GameMode.MATCHING -> initMatchingMode()
        }

        post {
            startGameLoop()
        }
    }

    private fun initTapMode() {
        repeat(8) {
            addRandomBalloon()
        }
    }

    private fun initFallingMode() {
        repeat(5) {
            balloons.add(
                Balloon(
                    x = (50..width - 50).random().toFloat(),
                    y = -100f,
                    color = getRandomColor(),
                    radius = 40f,
                    velocityY = 3f
                )
            )
        }
    }

    private fun initMatchingMode() {
        val colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
        repeat(6) {
            balloons.add(
                Balloon(
                    x = (50..width - 50).random().toFloat(),
                    y = (100..height - 100).random().toFloat(),
                    color = colors.random(),
                    radius = 40f
                )
            )
        }
    }

    private fun addRandomBalloon() {
        balloons.add(
            Balloon(
                x = (50..width - 50).random().toFloat(),
                y = (100..height - 100).random().toFloat(),
                color = getRandomColor(),
                radius = 40f
            )
        )
    }

    private fun getRandomColor(): Int {
        val colors = listOf(
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.CYAN, Color.MAGENTA
        )
        return colors.random()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.WHITE)

        for (balloon in balloons) {
            paint.color = balloon.color
            canvas.drawCircle(balloon.x, balloon.y, balloon.radius, paint)

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            paint.color = Color.BLACK
            canvas.drawCircle(balloon.x, balloon.y, balloon.radius, paint)
            paint.style = Paint.Style.FILL
        }

        if (gameRunning) {
            invalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!gameRunning || event == null) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y

                val clickedBalloon = balloons.firstOrNull { balloon ->
                    val distance = sqrt(
                        (balloon.x - x) * (balloon.x - x) +
                                (balloon.y - y) * (balloon.y - y)
                    )
                    distance <= balloon.radius
                }

                if (clickedBalloon != null) {
                    balloons.remove(clickedBalloon)
                    score += 10

                    when (gameMode) {
                        GameMode.TAP -> {
                            if (balloons.isEmpty()) {
                                repeat(8) { addRandomBalloon() }
                            }
                        }
                        GameMode.MATCHING -> {
                            if (balloons.isEmpty()) {
                                score += 100
                                initMatchingMode()
                            }
                        }
                        else -> {}
                    }

                    scoreListener?.invoke(score, timeElapsed)
                }
            }
        }

        return true
    }

    private fun startGameLoop() {
        Thread {
            var lastTime = System.currentTimeMillis()
            while (gameRunning) {
                val currentTime = System.currentTimeMillis()
                val deltaTime = currentTime - lastTime
                lastTime = currentTime

                for (balloon in balloons.toList()) {
                    when (gameMode) {
                        GameMode.FALLING -> {
                            balloon.y += balloon.velocityY
                            if (balloon.y > height + 100) {
                                balloons.remove(balloon)
                                if (balloons.size < 5) {
                                    balloons.add(
                                        Balloon(
                                            x = (50..width - 50).random().toFloat(),
                                            y = -100f,
                                            color = getRandomColor(),
                                            radius = 40f,
                                            velocityY = 3f
                                        )
                                    )
                                }
                            }
                        }
                        else -> {}
                    }
                }

                timeElapsed++
                scoreListener?.invoke(score, timeElapsed)

                Thread.sleep(16)
            }
        }.start()
    }

    fun setScoreListener(listener: (Int, Int) -> Unit) {
        scoreListener = listener
    }

    fun resume() {
        gameRunning = true
    }

    fun pause() {
        gameRunning = false
    }
}

data class Balloon(
    var x: Float,
    var y: Float,
    val color: Int,
    val radius: Float,
    var velocityY: Float = 0f,
    var velocityX: Float = 0f
)
