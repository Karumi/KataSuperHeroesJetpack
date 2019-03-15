package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.platform.app.InstrumentationRegistry
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers

interface ScreenshotTest {
    fun compareScreenshot(activity: Activity) {
        Screenshot.snapActivity(activity).record()
    }

    fun compareScreenshot(holder: RecyclerView.ViewHolder, height: Int) {
        compareScreenshot(holder.itemView, height)
    }

    fun compareScreenshot(view: View, height: Int) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        ViewHelpers.setupView(view)
            .setExactHeightPx(context.resources.getDimensionPixelSize(height))
            .setExactWidthPx(metrics.widthPixels)
            .layout()
        Screenshot.snap(view).record()
    }
}