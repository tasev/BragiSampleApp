package com.tta.bragisampleapp.shared

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tta.bragisampleapp.R
import com.tta.bragisampleapp.data.ConnectionData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ConnectionViewModel : ViewModel() {

    private val connectionStatusDataError = ConnectionData(R.string.connection_error, R.color.connectionStatusErrorColor)
    private val connectionStatusDataConnecting =
        ConnectionData(R.string.connection_connecting, R.color.connectionStatusConnectingColor)
    private val connectionStatusDataEstablished =
        ConnectionData(R.string.connection_established, R.color.connectionStatusEstablishedColor)

    /**
     * Status has 3 possible states
     - connection error
     - connecting
     - connection established
     */
    private val listOfPossibleConnectionStatuses = listOf(
        connectionStatusDataError,
        connectionStatusDataConnecting,
        connectionStatusDataEstablished
    )

    private var lastConnectionStatusDataindex = 1

    var connectionStatusTrigger = MutableLiveData(ConnectionData())
    val connectionStatus: LiveData<ConnectionData> = connectionStatusTrigger

    var disposable: Disposable? = null

    /**
     * Function that sets the new Connection Status
     * @param connectionData object of ConnectionData that includes connection title and color resource
     */
    fun setConnectionStatus(connectionData: ConnectionData) {
        connectionStatusTrigger.value = connectionData
    }

    /**
     * Function that returns next Connection Status (currently by random choice)
     * no params needed
     * returns object of ConnectionData that includes connection title and color resource
     */
    fun getNextRandomConnectionStatus(): ConnectionData {
        var randomIndex = 1
        do {
            randomIndex = Random.nextInt(listOfPossibleConnectionStatuses.size)
        } while (randomIndex == lastConnectionStatusDataindex)
        lastConnectionStatusDataindex = randomIndex
        return listOfPossibleConnectionStatuses[randomIndex]
    }

    /**
     * button with a Send Commands title to the Login Screen
     * When a user presses this button, creates an array of commands with ID from 1 to 10 and each command takes ID-seconds to execute.
     * For each command just prints its ID to the console at the end of the execution.
     * Start the next command only when the previous one is completed.
     * @param delayTime is time that DummyCommand lasts
     * @param timeUnit is always TimeUnit.SECONDS for now
     */
    fun runDummyCommandWithIdAndTimer(delayTime: Long, timeUnit: TimeUnit) {
        disposable?.dispose()
        disposable = Observable.timer(delayTime, timeUnit)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("disposable CommandID","$delayTime")
                if(delayTime<10) {
                    runDummyCommandWithIdAndTimer(delayTime + 1, timeUnit)
                }
            }
    }

}