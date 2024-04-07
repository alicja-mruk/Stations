package com.example.myapp.stations.data.worker

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.example.myapp.stations.domain.use_case.InsertLatestDataCheckTimeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class UpdateService : Service() {
    @Inject
    lateinit var insertLatestDataCheckTimeUseCase: InsertLatestDataCheckTimeUseCase

    lateinit var handler: Handler

    private val updateTask: Runnable = object : Runnable {
        override fun run() {
            CoroutineScope(Dispatchers.IO).launch {
                insertLatestDataCheckTimeUseCase(Instant.now().toEpochMilli())
            }

            handler.postDelayed(this, INTERVAL_MS)
        }
    }

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(updateTask)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTask)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val INTERVAL_MS: Long = 60 * 60 * 1000 // 60 minutes in milliseconds
    }
}
