package com.example.hillclimbracing.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.sin
import kotlin.math.cos

class Terrain(private val screenWidth: Int = 1280, private val screenHeight: Int = 720) {
    private val paint = Paint()
    private var cameraX = 0f
    private var cameraY = 0f
    private val terrainPoints = mutableListOf<Float>()
    private val startY = screenHeight - 150f

    init {
        generateTerrain()
    }

    private fun generateTerrain() {
        // Generate terrain with sine waves and slopes
        for (x in 0..5000 step 5) {
            val xFloat = x.toFloat()
            val wave1 = sin(xFloat / 150) * 80
            val wave2 = sin(xFloat / 400) * 120
            val slope = (xFloat / 15) % 150
            val height = startY - wave1 - wave2 - slope
            terrainPoints.add(height)
        }
    }

    fun getHeightAtX(x: Float): Float {
        val index = (x / 5).toInt()
        return if (index >= 0 && index < terrainPoints.size) {
            terrainPoints[index]
        } else if (index < 0) {
            terrainPoints[0]
        } else {
            terrainPoints[terrainPoints.size - 1]
        }
    }

    fun updateCamera(car: Car) {
        cameraX = car.position.x - screenWidth / 3
        cameraY = car.position.y - screenHeight / 3
        
        // Limit camera
        if (cameraX < 0) cameraX = 0f
        if (cameraY < 0) cameraY = 0f
    }

    fun draw(canvas: Canvas) {
        paint.color = Color.parseColor("#228B22")
        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        val startIndex = (cameraX / 5).toInt()
        val endIndex = (startIndex + screenWidth / 5 + 100).toInt()

        // Draw terrain lines
        for (i in startIndex until minOf(endIndex, terrainPoints.size - 1)) {
            val x1 = (i * 5 - cameraX).toFloat()
            val y1 = (terrainPoints[i] - cameraY).toFloat()
            val x2 = ((i + 1) * 5 - cameraX).toFloat()
            val y2 = (terrainPoints[i + 1] - cameraY).toFloat()

            if (x1 > -100 && x1 < screenWidth + 100) {
                canvas.drawLine(x1, y1, x2, y2, paint)
            }
        }

        // Draw terrain base
        paint.color = Color.parseColor("#8B4513")
        paint.style = Paint.Style.FILL
        canvas.drawRect(
            0f,
            screenHeight - 40f,
            screenWidth.toFloat(),
            screenHeight.toFloat(),
            paint
        )
        
        // Draw grass
        paint.color = Color.parseColor("#228B22")
        for (i in startIndex until minOf(endIndex, terrainPoints.size)) {
            val x = (i * 5 - cameraX).toFloat()
            val y = (terrainPoints[i] - cameraY).toFloat()
            canvas.drawCircle(x, y, 2f, paint)
        }
    }
}
