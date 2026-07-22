package com.example.hillclimbracing.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.atan

data class Vector2(var x: Float = 0f, var y: Float = 0f)

class Car {
    var position = Vector2(200f, 0f)
    var velocity = Vector2(0f, 0f)
    var acceleration = Vector2(0f, 0f)
    var angle = 0f
    var distanceTraveled = 0f

    private val maxVelocity = 30f
    private val accelerationRate = 0.8f
    private val brakingRate = 1.5f
    private val gravityForce = 0.6f
    private val carWidth = 40f
    private val carHeight = 25f
    private val paint = Paint()
    private var friction = 0.05f

    fun accelerate() {
        // BESKONAČAN BENZIN - nema limitiranja!
        if (velocity.x < maxVelocity) {
            velocity.x += accelerationRate
        }
    }

    fun brake() {
        if (velocity.x > 0) {
            velocity.x -= brakingRate
        } else if (velocity.x < 0) {
            velocity.x = 0f
        }
    }

    fun coast() {
        if (velocity.x > 0) {
            velocity.x -= friction
        }
        if (velocity.x < 0) {
            velocity.x = 0f
        }
    }

    fun update(terrain: Terrain) {
        // Apply gravity
        acceleration.y = gravityForce

        // Update velocity
        velocity.x += acceleration.x
        velocity.y += acceleration.y

        // Limit velocity
        if (velocity.x < 0) velocity.x = 0f
        if (velocity.x > maxVelocity) velocity.x = maxVelocity
        if (velocity.y > 20f) velocity.y = 20f

        // Move
        position.x += velocity.x
        position.y += velocity.y

        // Track distance
        distanceTraveled += velocity.x * 0.1f

        // Gravity - control automobile on terrain
        val terrainHeight = terrain.getHeightAtX(position.x)
        if (position.y >= terrainHeight) {
            position.y = terrainHeight
            velocity.y = 0f
            acceleration.y = 0f
        }

        // Calculate angle based on terrain
        val nextHeight = terrain.getHeightAtX(position.x + 20f)
        val heightDiff = nextHeight - terrainHeight
        angle = atan(heightDiff / 20f)
    }

    fun draw(canvas: Canvas) {
        canvas.save()
        canvas.translate(position.x, position.y)
        canvas.rotate(Math.toDegrees(angle.toDouble()).toFloat())

        // Draw automobile as rectangle
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        canvas.drawRect(
            -carWidth / 2,
            -carHeight / 2,
            carWidth / 2,
            carHeight / 2,
            paint
        )
        
        // Draw windshield
        paint.color = Color.parseColor("#87CEEB")
        canvas.drawRect(
            -carWidth / 4,
            -carHeight / 4,
            carWidth / 4,
            0f,
            paint
        )

        // Wheels
        paint.color = Color.BLACK
        canvas.drawCircle(-15f, carHeight / 2, 6f, paint)
        canvas.drawCircle(15f, carHeight / 2, 6f, paint)

        canvas.restore()
    }
}
