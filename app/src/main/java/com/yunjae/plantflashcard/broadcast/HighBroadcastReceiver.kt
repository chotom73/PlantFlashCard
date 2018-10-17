package com.yunjae.plantflashcard.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.yunjae.plantflashcard.FlashcardActivity

class HighBroadcastReceiver: BroadcastReceiver() {

    private var power = false

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // are we here because the device is connected to power.
            power = true
            synchronize(context)
        } else if (intent?.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            // are we here because the device is not connected to power.
            power = false
            synchronize(context)
        }

    }

    private fun synchronize(context: Context?) {
        if (power) {
           val i = 1 + 1
            Toast.makeText(context, "power connected", Toast.LENGTH_SHORT).show()
            FlashcardActivity.getInstance()?.updateTheTextView("power connected")
        } else {
            val i = 1 + 1
            Toast.makeText(context, "power disconnected", Toast.LENGTH_SHORT).show()
            FlashcardActivity.getInstance()?.updateTheTextView("power disconnected")
        }
    }


}