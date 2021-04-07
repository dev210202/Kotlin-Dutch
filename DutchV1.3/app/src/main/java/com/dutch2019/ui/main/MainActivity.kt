package com.dutch2019.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dutch2019.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //        binding.logo.setOnClickListener {
//            val intent = Intent(this, DeveloperInfoActivity::class.java)
//            startActivity(intent)
//        }

//        binding.searchmiddlelocationbutton.setOnClickListener {
//            if (viewmodel.dynamicButtonLocationInfoArray.size > 1) {
//                var thread = Thread(Runnable {
//                    locationRepository.setRecentDB(application)
//                    viewmodel.dynamicButtonArray.forEach { i ->
//                        if (binding.buttonview.findViewById<Button>(i).text != "위치를 입력해주세요") {
//                            locationRepository.insertRecentData(viewmodel.dynamicButtonLocationInfoArray[i])
//                            Log.e("locationRepo", locationRepository.getRecentLocationListData()[i].locationName)
//                            Log.e("locationRepo id", ""+locationRepository.getRecentLocationListData()[i].id)
//                        }
//                    }
//                }).start()
//            } else {
//                Toast.makeText(applicationContext, "2개 이상 위치를 설정해주세요!", Toast.LENGTH_LONG)
//            }
//        }


    private fun changeIntToDP(value: Int): Int {

        val displayMetrics = resources.displayMetrics
        return (value * displayMetrics.density).roundToInt()

    }

//    fun dynamicButtonCreate(view: View) {
//
//        // 데이터 단
//        viewmodel.dynamicButtonArray.add(
//            viewmodel.dynamicCreateChooseNum,
//            viewmodel.dynamicCreateChooseNum
//        )
//
//
//
//        val buttonText = ("위치를 입력해주세요")
//        val dynamicButton = Button(this)
//        val layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT, changeIntToDP(52)
//        )
//        layoutParams.setMargins(changeIntToDP(16), changeIntToDP(20), changeIntToDP(16), 0)
//        dynamicButton.layoutParams = layoutParams
//        dynamicButton.text = buttonText
//        dynamicButton.textSize = 16F
//        dynamicButton.setTextColor(Color.rgb(134, 134, 134))
//        dynamicButton.setBackgroundResource(R.drawable.button_main_location_search)
//
//        dynamicButton.id = viewmodel.dynamicCreateChooseNum
//
//
//        viewmodel.dynamicCreateChooseNum++
//        dynamicButton.setOnClickListener {
//            val intent = Intent(applicationContext, SearchLocationActivity::class.java)
//            startActivityForResult(
//                intent,
//                viewmodel.dynamicButtonArray[dynamicButton.id]
//            )
//        }
//
//        binding.buttonview.addView(dynamicButton)
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//
//            var thread = Thread(Runnable {
//                locationRepository.setDB(application)
//                var list = locationRepository.getLocationListData()
//                viewmodel.dynamicButtonArray.forEach { i ->
//                    if (requestCode == viewmodel.dynamicButtonArray[i]) {
//                        binding.buttonview.findViewById<Button>(i)
//                            .setTextColor(Color.rgb(47, 47, 47))
//                        binding.buttonview.findViewById<Button>(i).text = list!![0].locationName
//                        viewmodel.dynamicButtonLocationInfoArray.add(list[0])
//                    }
//                }
//            }).start()
//
//        }
//    }

    /*
        private fun setLocationName(i: Int, userChooseLocation: LocationInfo) {
            try {
                if (binding.buttonview.findViewById<Button>(i).text != "위치를 입력해주세요") {
                    for (j in 0 until LocationSetData.data.size) {
                        if (binding.buttonview.findViewById<Button>(j) != null) {
                            val removeData =
                                binding.buttonview.findViewById<Button>(i).text.toString()
                            if (LocationSetData.data[j].locationName == removeData) {

                                Log.e("LocationSetData Before", LocationSetData.data.toString())
                                LocationSetData.data.removeAt(j)
                                LocationSetData.data.add(j, userChooseLocation)
                                Log.e("LocationSetData After", LocationSetData.data.toString())
                            }
                        }

                    }
                } else {
                    LocationSetData.data.add(userChooseLocation)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "오류발생! 해당 메시지를 캡쳐해서 helios2sic@gmail.com으로 보내주시면 오류수정에 도움이됩니다!\n" + e.printStackTrace() ,Toast.LENGTH_LONG).show()
            }

        }*/
}