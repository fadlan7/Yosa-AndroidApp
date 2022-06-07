package com.yosa.ui.detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.util.Util
import com.yosa.databinding.ActivityYogaDetailBinding
import com.yosa.ui.broadcastreceiver.TimerExpiredReciver
import com.yosa.util.PrefUtil
import java.util.*

class YogaDetailActivity : AppCompatActivity() {

    companion object {
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReciver::class.java)

            val pi = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pi)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime


        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpiredReciver::class.java)
            val pi = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.cancel(pi)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class TimerState {
        Stopped, Paused, Running
    }

    private lateinit var binding: ActivityYogaDetailBinding
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState = TimerState.Stopped

    private var secondsRemaining = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabPlay.setOnClickListener { v ->
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }

        binding.fabPause.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        binding.fabStop.setOnClickListener { v ->
            timer.cancel()
            onTimerFinished()
        }
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm(this)
        //remove bg timer, hide notif
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running) {
            timer.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            //start bg timer and show notif
        } else if (timerState == TimerState.Paused) {
            //show notif
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)

        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        //change secondsRemaining to where bg timer stopped
        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            secondsRemaining -= nowSeconds - alarmSetTime

        //resume where we left off
        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        setNewTimerLength()

        binding.progressCountdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer() {
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(milisUntilFinished: Long) {
                secondsRemaining = milisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        binding.progressCountdown.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        binding.progressCountdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()

        binding.tvCountdown.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"

        binding.progressCountdown.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                binding.apply {
                    fabPlay.isEnabled = false
                    fabPause.isEnabled = true
                    fabStop.isEnabled = true
                }
            }
            TimerState.Stopped -> {
                binding.apply {
                    fabPlay.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = false
                }
            }
            TimerState.Paused -> {
                binding.apply {
                    fabPlay.isEnabled = true
                    fabPause.isEnabled = false
                    fabStop.isEnabled = true
                }
            }
        }
    }

}