package com.dutch2019.ui.error

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import com.dutch2019.R
import com.dutch2019.base.BaseActivity
import com.dutch2019.databinding.ActivityErrorBinding
import com.dutch2019.util.Color

class ErrorActivity : BaseActivity<ActivityErrorBinding>(R.layout.activity_error) {

    private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>("lastIntent") }
    private val errorText by lazy { intent.getStringExtra("errorText") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(errorText == "DB 로드 에러입니다"){
            binding.tvTitle.setTextColor(Color.TEXT_PRIMARY.getColor(this))
            binding.tvTitle.text = "DB 버전 업그레이드로 재설치가 필요합니다.\n 앱을 제거하고 다시 설치해주세요."
            binding.tvInfo.visibility = INVISIBLE
            binding.btnRefresh.visibility = INVISIBLE
        }
        binding.ivError.setOnClickListener {
            binding.tvError.text = errorText.toString()
        }
        binding.btnRefresh.setOnClickListener {
            startActivity(lastActivityIntent)
        }
    }

}