package org.first.kotlinlivedatatest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Handler

class MainViewModel: ViewModel() {

    var liveData : MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()

    init{

        var UserData = ArrayList<User>()
        UserData.add(User("first","test"))
        UserData.add(User("second","test"))
        UserData.add(User("third","test"))
        liveData.postValue(UserData)
    }


}