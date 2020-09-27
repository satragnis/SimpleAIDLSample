package com.example.aidlsample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process

class MultiplicationService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }

    private val myBinder = object :IRemoteServiceAidl.Stub(){
        override fun getPid(): Int {
            return Process.myPid()
        }

        override fun multiplyTwoValuesTogether(firstNumber: Int, SecondNumber: Int): Int {
            return firstNumber * SecondNumber
        }

    }
}
