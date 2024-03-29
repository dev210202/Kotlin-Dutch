package com.dutch2019.util.marker

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.dutch2019.R


class BalloonOverlayView(
    context: Context,
    labelName: String?
) :
    FrameLayout(context) {
    private val layout: LinearLayout
    private var title: TextView? = null
    private fun setupView(
        context: Context,
        parent: ViewGroup?,
        labelName: String?
    ) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.view_ballonoverlay, parent, true)
        title = view.findViewById(R.id.bubble_title) as TextView
        setTitle(labelName)

    }

    private fun setTitle(str: String?) {
        title!!.text = str
    }


    init {
        setPadding(0, 0, 0, 10)
        layout = LinearLayout(context)
        layout.visibility = View.VISIBLE
        setupView(context, layout, labelName)
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.NO_GRAVITY
        addView(layout, params)
    }
}
