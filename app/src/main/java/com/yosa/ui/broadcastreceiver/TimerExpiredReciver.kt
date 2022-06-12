package com.yosa.ui.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yosa.ui.detail.YogaDetailActivity
import com.yosa.util.NotificationUtil
import com.yosa.util.PrefUtil

class TimerExpiredReciver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //show notification
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(YogaDetailActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}