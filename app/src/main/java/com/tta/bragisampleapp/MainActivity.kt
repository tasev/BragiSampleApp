package com.tta.bragisampleapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tta.bragisampleapp.shared.ConnectionViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private val connectionViewModel: ConnectionViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Every 5 seconds get new connection status,
         change the 'connectionStatus' liveData
         and let every fragment that observe currently have the latest one
         */
        disposable = Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .repeat()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                connectionViewModel.setConnectionStatus(connectionViewModel.getNextRandomConnectionStatus())
            }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}