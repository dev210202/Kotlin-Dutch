package com.dutch2019.util.marker

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View.*
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapView
import com.dutch2019.R
import com.dutch2019.util.Color


class MarkerOverlay(view: TMapView, context: Context, labelName: String) : TMapMarkerItem2() {
    private var balloonView: BalloonOverlayView
    private var dm: DisplayMetrics = DisplayMetrics()
    var mAnimationCount = 0
    private var rect = Rect()
    var mMapView: TMapView = view
    private var isCallOut = false

    init {
        val wmgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wmgr.defaultDisplay.getMetrics(dm)

        this.balloonView = BalloonOverlayView(context, labelName)

        balloonView.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        balloonView.layout(
            0, 0, balloonView.measuredWidth, balloonView.measuredHeight
        )
    }


    override fun draw(
        canvas: Canvas, mapView: TMapView, showCallout: Boolean
    ) {
        val x = mapView.getRotatedMapXForPoint(
            tMapPoint.latitude, tMapPoint.longitude
        )
        val y = mapView.getRotatedMapYForPoint(
            tMapPoint.latitude, tMapPoint.longitude
        )
        canvas.save()
        canvas.rotate(
            -mapView.rotate, mapView.centerPointX.toFloat(), mapView.centerPointY.toFloat()
        )
        val xPos = positionX
        val yPos = positionY
        val nposX: Int
        val nposY: Int
        val marginX: Int
        val marginY: Int
        val nMarkerIconWidth: Int = icon.width
        val nMarkerIconHeight: Int = icon.height
        nposX = (xPos * nMarkerIconWidth).toInt()
        nposY = (yPos * nMarkerIconHeight).toInt()
        marginX = if (nposX == 0) {
            nMarkerIconWidth / 2
        } else {
            nposX
        }
        marginY = if (nposY == 0) {
            nMarkerIconHeight / 2
        } else {
            nposY
        }
        canvas.translate(x - marginX.toFloat(), y - marginY.toFloat())
        canvas.drawBitmap(icon, 0f, 0f, null)
        canvas.restore()
        if (showCallout) {
            balloonView.visibility = VISIBLE
            canvas.save()
            canvas.rotate(
                -mapView.rotate, mapView.centerPointX.toFloat(), mapView.centerPointY.toFloat()
            )
            balloonView.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val nTempX = (x - balloonView.measuredWidth / 2)
            val nTempY = (y - marginY - balloonView.measuredHeight)
            canvas.translate(nTempX.toFloat(), nTempY.toFloat())
            balloonView.draw(canvas)

            // 풍선뷰 영역 설정
            rect.left = nTempX
            rect.top = nTempY
            rect.right = rect.left + balloonView.measuredWidth
            rect.bottom = rect.top + balloonView.measuredHeight


            calloutRect = rect
            isCallOut = true
            canvas.restore()
        }
    }

    override fun onSingleTapUp(point: PointF?, mapView: TMapView): Boolean {
        // marker event 부분
        mapView.onMarker2ClickListener.onCalloutMarker2ClickEvent(this.id, this)
        mapView.showCallOutViewWithMarkerItemID(id)
        Log.e("onSingleTapUp", "!!")
        return false
    }

    var mHandler: Handler? = null

    override fun startAnimation() {
        super.startAnimation()
        val mRunnable: Runnable = object : Runnable {
            override fun run() {
                if (animationIcons.size > 0) {
                    if (mAnimationCount >= animationIcons.size) mAnimationCount = 0
                    icon = animationIcons[mAnimationCount]
                    mMapView.postInvalidate()
                    mAnimationCount++
                    mHandler!!.postDelayed(this, aniDuration.toLong())
                }
            }
        }
        mHandler = Handler()
        mHandler!!.post(mRunnable)
    }

    fun changeTextPrimaryColor(context: Context) {
        balloonView.findViewById<TextView>(R.id.bubble_title)
            .setTextColor(Color.TEXT_PRIMARY.getColor(context))
    }

    fun changeTextBlueColor(context: Context) {
        balloonView.findViewById<TextView>(R.id.bubble_title)
            .setTextColor(Color.TEXT_RATIO.getColor(context))
    }

    fun changeTextDefaultColor(context: Context) {
        balloonView.findViewById<TextView>(R.id.bubble_title)
            .setTextColor(Color.MARKER_DEFAULT.getColor(context))
    }
}