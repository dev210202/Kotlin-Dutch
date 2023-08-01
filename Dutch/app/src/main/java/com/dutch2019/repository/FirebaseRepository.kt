package com.dutch2019.repository

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseRepository {
    private val storage = Firebase.storage
    suspend fun readImage(): String = suspendCancellableCoroutine { continuation ->

        val storageRef = storage.reference
        val task = storageRef.listAll()

        task.addOnSuccessListener { listResult ->
            listResult.items[0].downloadUrl.addOnCompleteListener { task ->
                continuation.resume(task.result.toString())
            }
        }
        task.addOnFailureListener {error ->
            Log.e("readImage error", error.toString())
            continuation.resume("https://user-images.githubusercontent.com/32587845/255312406-b487bb92-8728-406a-8b78-310da211d765.jpg")
        }

    }
}