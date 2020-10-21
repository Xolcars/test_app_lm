package com.testapp.testapp_app.features

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.testapp.testapp_app.R

class SplashActivity : AppCompatActivity() {
    //region Vars
    private var mDelayHandler: Handler? = null
    private val DELAY: Long = com.testapp.testapp_app.BuildConfig.SPLASH_DELAY
    //endregion

    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        mDelayHandler = Handler()
        mDelayHandler?.postDelayed(mRunnable, DELAY)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
    //endregion

    //region Methods
    private val mRunnable: Runnable = Runnable {
        val intent = MainActivity.intent(this)
        if (!isFinishing) {
            startActivity(intent)
            finish()
        }
    }
    //endregion
}