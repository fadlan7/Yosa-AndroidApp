package com.yosa.ui.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yosa.Constanta.ACTION_PAUSE
import com.yosa.Constanta.ACTION_RESUME
import com.yosa.Constanta.ACTION_START
import com.yosa.Constanta.ACTION_STOP
import com.yosa.ui.detail.YogaDetailActivity
import com.yosa.util.NotificationUtil
import com.yosa.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_STOP -> {
                YogaDetailActivity.removeAlarm(context)
                PrefUtil.setTimerState(YogaDetailActivity.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = YogaDetailActivity.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                YogaDetailActivity.removeAlarm(context)
                PrefUtil.setTimerState(YogaDetailActivity.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeupTime = YogaDetailActivity.setAlarm(
                    context,
                    YogaDetailActivity.nowSeconds,
                    secondsRemaining
                )
                PrefUtil.setTimerState(YogaDetailActivity.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeupTime)
            }
            ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeupTime = YogaDetailActivity.setAlarm(
                    context,
                    YogaDetailActivity.nowSeconds,
                    secondsRemaining
                )
                PrefUtil.setTimerState(YogaDetailActivity.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeupTime)
            }

        }
    }
}