package com.outlivethesun.floatingwindow

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout

class FloatingWindowService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var linearLayout: LinearLayout
    private lateinit var windowManagerLayoutParams: WindowManager.LayoutParams

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        createLinearLayout()
        createCloseButton()
        setupWindowManager()
        windowManager.addView(linearLayout, windowManagerLayoutParams)

        setupLayoutMovement()
    }

    private fun createCloseButton() {
        val closeButton = Button(this)
        closeButton.text = "Close"
        closeButton.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        linearLayout.addView(closeButton)

        closeButton.setOnClickListener {
            windowManager.removeView(linearLayout)
            stopSelf()
        }
    }

    private fun setupLayoutMovement() {
        val updatedParameters: WindowManager.LayoutParams = windowManagerLayoutParams
        var x = 0
        var y = 0
        var touchedX = 0f
        var touchedY = 0f

        linearLayout.setOnTouchListener { view, motionEvent ->

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = updatedParameters.x
                    y = updatedParameters.y

                    touchedX = motionEvent.rawX
                    touchedY = motionEvent.rawY
                    view.performClick()
                }
                MotionEvent.ACTION_MOVE -> {
                    updatedParameters.x = (x + (motionEvent.rawX - touchedX)).toInt()
                    updatedParameters.y = (y + (motionEvent.rawY - touchedY)).toInt()
                    windowManager.updateViewLayout(linearLayout, updatedParameters)
                    view.performClick()
                }

                else -> {
                    view.performClick()
                }
            }
        }
    }

    private fun setupWindowManager() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManagerLayoutParams = WindowManager.LayoutParams(
            600,
            350,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        windowManagerLayoutParams.x = 0
        windowManagerLayoutParams.y = 0
        windowManagerLayoutParams.gravity = Gravity.CENTER or Gravity.CENTER
    }

    private fun createLinearLayout() {
        linearLayout = LinearLayout(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.setBackgroundColor(Color.argb(66, 255, 0, 0))
        linearLayout.layoutParams = layoutParams
    }

}