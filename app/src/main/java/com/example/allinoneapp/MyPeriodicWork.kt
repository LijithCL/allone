package com.example.allinoneapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyPeriodicWork(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    var TAG:String  = "MyPeriodicWork"

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        Log.e(TAG, "work Manager success")
        return Result.Success()
    }
}
