package com.tta.bragisampleapp.shared

import androidx.lifecycle.MutableLiveData
import com.tta.bragisampleapp.data.ConnectionData
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ConnectionViewModelTest {

    private var connectionViewModel = ConnectionViewModel()

    @Test
    fun `Status Connection Text should not be equal to the previous one`() {
        @Before
        connectionViewModel.connectionStatusTrigger = mock(MutableLiveData::class.java) as MutableLiveData<ConnectionData>
        for(item in 1..15) {
            val previousConnectionStatus =
                connectionViewModel.getNextRandomConnectionStatus().titleResource
            assertEquals(
                false,
                connectionViewModel.getNextRandomConnectionStatus().titleResource == previousConnectionStatus
            )
        }
    }

    @Test
    fun `Status Connection Color should not be equal to the previous one`() {
        @Before
        connectionViewModel.connectionStatusTrigger = mock(MutableLiveData::class.java) as MutableLiveData<ConnectionData>
        for(item in 1..15) {
            val previousConnectionStatus =
                connectionViewModel.getNextRandomConnectionStatus().statusColorResource
            assertEquals(
                false,
                connectionViewModel.getNextRandomConnectionStatus().statusColorResource == previousConnectionStatus
            )
        }
    }
}