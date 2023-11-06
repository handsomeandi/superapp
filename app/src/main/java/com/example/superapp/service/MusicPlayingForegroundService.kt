package com.example.superapp.service

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.coroutines.superapp.R

class MusicPlayingForegroundService : Service() {
    private companion object {
        private const val NOTIFICATION_CHANNEL_ID = "musicplayer_channel"
        private const val NOTIFICATION_ID = 1
    }

    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {
        createNotificationChannel()
        mediaPlayer = MediaPlayer().apply {
            setDataSource("https://www.audioguias-bluehertz.es/audioguia_cuevas_san_jose/ruso/audioguia_bienvenida_ruso.mp3")
            prepare()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION.STARTFOREGROUND_ACTION.name -> {
                mediaPlayer.start()
            }
            ACTION.STOPFOREGROUND_ACTION.name -> {
                mediaPlayer.pause()
            }
            else -> {
                stopService()
            }
        }
        startForeground(NOTIFICATION_ID, createNotification())
        return START_NOT_STICKY
    }

    private fun stopService() {
        mediaPlayer.stop()
        mediaPlayer.release()
        stopSelf()
    }


    private fun createNotification(): Notification {
        val stopIntent = Intent(this, MusicPlayingForegroundService::class.java)
        stopIntent.action = ACTION.STOPFOREGROUND_ACTION.name
        val pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, FLAG_IMMUTABLE)
        val startIntent = Intent(this, MusicPlayingForegroundService::class.java)
        startIntent.action = ACTION.STARTFOREGROUND_ACTION.name
        val pendingStartIntent = PendingIntent.getService(this, 0, startIntent, FLAG_IMMUTABLE)
        val fullStopIntent = Intent(this, MusicPlayingForegroundService::class.java)
        val pendingFullStopIntent = PendingIntent.getService(this, 0, fullStopIntent, FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Playing Music")
            .setSmallIcon(R.drawable.ic_music)
            .addAction(R.drawable.ic_pause, "Pause", pendingStopIntent)
            .addAction(R.drawable.ic_play, "Play", pendingStartIntent)
            .addAction(R.drawable.ic_stop, "Stop", pendingFullStopIntent)

        return builder.build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Music Player1",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(channel)
    }
}

internal enum class ACTION {
    STARTFOREGROUND_ACTION,
    STOPFOREGROUND_ACTION
}
