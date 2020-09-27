package com.example.aidlsample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var iRemoteServiceAidl: IRemoteServiceAidl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit.setOnClickListener(this)

        //creating intent for bind service
        val multiplyServiceIntent = Intent(this,MultiplicationService::class.java)


        bindService(multiplyServiceIntent,getServiceConnection(), Context.BIND_AUTO_CREATE)

    }

    // to get the service connection
    private fun getServiceConnection(): ServiceConnection {
        return object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
                service?.let {
                    iRemoteServiceAidl = IRemoteServiceAidl.Stub.asInterface(it)
                }
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.e(TAG, "Service is disconnected")
                iRemoteServiceAidl = null
            }
        }
    }

    companion object {
        const val TAG = "++++++"
    }

    override fun onClick(v: View?) {
        val firstNo = firstNumber.text.toString()
        val secondNo = secondNumber.text.toString()
        Log.d(TAG,iRemoteServiceAidl?.pid.toString())
        if(firstNo.isNotEmpty() && secondNo.isNotEmpty()) {
            try {
                result.text = iRemoteServiceAidl?.multiplyTwoValuesTogether(
                    Integer.parseInt(firstNo),
                    Integer.parseInt(secondNo)
                ).toString()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }else{
            Toast.makeText(this@MainActivity,"Invalid Input!",Toast.LENGTH_SHORT).show()
        }
    }
}