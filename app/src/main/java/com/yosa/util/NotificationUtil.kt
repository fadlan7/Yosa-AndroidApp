package com.yosa.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.yosa.Constanta.ACTION_PAUSE
import com.yosa.Constanta.ACTION_RESUME
import com.yosa.Constanta.ACTION_START
import com.yosa.Constanta.ACTION_STOP
import com.yosa.R
import com.yosa.ui.broadcastreceiver.TimerNotificationActionReceiver
import com.yosa.ui.detail.YogaDetailActivity
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnspecifiedImmutableFlag")
class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEl_NAME_TIMER = "YOSA App"
        private const val TIMER_ID = 0

        fun showTimerExpired(context: Context) {
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = ACTION_START

            val startPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                startIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer Expired!")
                .setContentText("Start Again?")
                .setContentIntent(
                    getPendingIntentWithStack(
                        context,
                        YogaDetailActivity::class.java
                    )
                )
                .addAction(R.drawable.ic_baseline_play_arrow_24, "Start", startPendingIntent)

            val nManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEl_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerRunning(context: Context, wakeUpTime: Long) {
            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stopIntent.action = ACTION_STOP

            val stopPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = ACTION_PAUSE

            val pausePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                pauseIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is Running.")
                .setContentText("End:${df.format(Date(wakeUpTime))}")
                .setContentIntent(
                    getPendingIntentWithStack(
                        context,
                        YogaDetailActivity::class.java
                    )
                )
                .setOngoing(true)
                .addAction(R.drawable.ic_baseline_stop_24, "Stop", stopPendingIntent)
                .addAction(R.drawable.ic_baseline_pause_24, "Pause", pausePendingIntent)

            val nManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEl_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerPaused(context: Context) {
            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            resumeIntent.action = ACTION_RESUME

            val resumePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                resumeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is Paused.")
                .setContentText("Resume?")
                .setContentIntent(
                    getPendingIntentWithStack(
                        context,
                        YogaDetailActivity::class.java
                    )
                )
                .setOngoing(true)
                .addAction(R.drawable.ic_baseline_play_arrow_24, "Resume", resumePendingIntent)

            val nManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEl_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun hideTimerNotification(context: Context) {
            val nManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(
            context: Context,
            channelId: String,
            playSound: Boolean
        ): NotificationCompat.Builder {
            val notificationSound: Uri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_timer_24)
                .setAutoCancel(true)
                .setDefaults(0)

            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(
            context: Context,
            javaClass: Class<T>
        ): PendingIntent {
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        //        @TargetApi(value: 26)
        private fun NotificationManager.createNotificationChannel(
            channelID: String,
            channelName: String,
            playSound: Boolean
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW

                val nChannel = NotificationChannel(channelID, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }
    }
}