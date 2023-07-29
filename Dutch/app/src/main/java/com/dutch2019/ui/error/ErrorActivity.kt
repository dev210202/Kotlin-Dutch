package com.dutch2019.ui.error

import android.content.Intent
import android.os.Bundle
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.databinding.ActivityErrorBinding

class ErrorActivity : BaseActivity<ActivityErrorBinding>(R.layout.activity_error) {

    private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>("lastIntent") }
    private val errorText by lazy { intent.getStringExtra("errorText") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivError.setOnClickListener {
            binding.tvError.text = errorText.toString()
        }
        binding.btnRefresh.setOnClickListener {
            startActivity(lastActivityIntent)
        }
    }

}