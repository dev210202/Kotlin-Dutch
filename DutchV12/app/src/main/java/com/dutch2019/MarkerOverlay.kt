package com.dutch2019

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View.MeasureSpec
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import org.w3c.dom.Text


class MarkerOverlay(context: Context, view: TMapView, labelName: String, id: String) :
    TMapMarkerItem2() {
    lateinit var balloonView: BalloonOverlayView
    lateinit var dm: DisplayMetrics
    var mAnimationCount = 0
    var rect = Rect()
    lateinit var mMapView: TMapView

    init {
        mMapView = view
        dm = DisplayMetrics()
        var wmgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wmgr.defaultDisplay.getMetrics(dm)

        balloonView = BalloonOverlayView(context, labelName, id)

        balloonView!!.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        balloonView!!.layout(
            0,
            0,
            balloonView!!.getMeasuredWidth(),
            balloonView!!.getMeasuredHeight()
        )

    }


    override fun getIcon(): Bitmap {
        return super.getIcon()
    }

    override fun setIcon(bitmap: Bitmap?) {
        super.setIcon(bitmap)
    }

    override fun setTMapPoint(point: TMapPoint?) {
        super.setTMapPoint(point)
    }

    override fun getTMapPoint(): TMapPoint {
        return super.getTMapPoint()
    }

    override fun setPosition(dx: Float, dy: Float) {
        super.setPosition(dx, dy)
    }

    override fun setCalloutRect(rect: Rect?) {
        super.setCalloutRect(rect)
    }


    override fun draw(
        canvas: Canvas,
        mapView: TMapView,
        showCallout: Boolean
    ) {
        val x = mapView.getRotatedMapXForPoint(
            tMapPoint.latitude,
            tMapPoint.longitude
        )
        val y = mapView.getRotatedMapYForPoint(
            tMapPoint.latitude,
            tMapPoint.longitude
        )
        canvas.save()
        canvas.rotate(
            -mapView.rotate,
            mapView.centerPointX.toFloat(),
            mapView.centerPointY.toFloat()
        )
        val xPos = positionX
        val yPos = positionY
        val nPos_x: Int
        val nPos_y: Int
        var nMarkerIconWidth = 0
        var nMarkerIconHeight = 0
        var marginX = 0
        var marginY = 0
        nMarkerIconWidth = icon.width
        nMarkerIconHeight = icon.height
        nPos_x = (xPos * nMarkerIconWidth).toInt()
        nPos_y = (yPos * nMarkerIconHeight).toInt()
        marginX = if (nPos_x == 0) {
            nMarkerIconWidth / 2
        } else {
            nPos_x
        }
        marginY = if (nPos_y == 0) {
            nMarkerIconHeight / 2
        } else {
            nPos_y
        }
        canvas.translate(x - marginX.toFloat(), y - marginY.toFloat())
        canvas.drawBitmap(icon, 0f, 0f, null)
        canvas.restore()
        if (showCallout) {
            canvas.save()
            canvas.rotate(
                -mapView.rotate,
                mapView.centerPointX.toFloat(),
                mapView.centerPointY.toFloat()
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


            setCalloutRect(rect)
            canvas.restore()
        }
    }

    override fun onSingleTapUp(point: PointF?, mapView: TMapView): Boolean {
        mapView.showCallOutViewWithMarkerItemID(id)
        return false
    }

    var mHandler: Handler? = null

    override fun startAnimation() {
        super.startAnimation()
        val mRunnable: Runnable = object : Runnable {
            override fun run() {
                if (animationIcons.size > 0) {
                    if (mAnimationCount >= animationIcons.size) mAnimationCount = 0
                    setIcon(animationIcons[mAnimationCount])
                    mMapView.postInvalidate()
                    mAnimationCount++
                    mHandler!!.postDelayed(this, aniDuration.toLong())
                }
            }
        }
        mHandler = Handler()
        mHandler!!.post(mRunnable)
    }

    fun changeTextRedColor(context: Context) {
        balloonView.findViewById<TextView>(R.id.bubble_title)
            .setTextColor(ContextCompat.getColor(context, R.color.orange))
    }

    fun chagneTextBlueColor(context: Context) {
        balloonView.findViewById<TextView>(R.id.bubble_title)
            .setTextColor(ContextCompat.getColor(context, R.color.blue))
    }
}