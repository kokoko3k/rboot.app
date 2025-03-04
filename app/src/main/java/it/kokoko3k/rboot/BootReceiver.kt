package it.kokoko3k.rboot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import it.kokoko3k.rboot.R

fun getApplicationName(context: Context): String {
    val applicationInfo = context.applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context.getString(stringId)
}

class BootReceiver : BroadcastReceiver() {

    private val tag = "BootReceiver"

    private val PREFS_NAME = "RbootPrefs"
    private val RBOOT_ENABLED_KEY = "RbootEnabled"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(tag, "BootReceiver.onReceive() called")

        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            Log.d(tag, "Received BOOT_COMPLETED or LOCKED_BOOT_COMPLETED intent")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(tag, "Permission POST_NOTIFICATIONS not granted")
                    return
                }
            }

            //Enable Rboot at boot?
            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val isRbootEnabledAtBoot = sharedPrefs.getBoolean(RBOOT_ENABLED_KEY, false)
            if (isRbootEnabledAtBoot) {
                createNotificationChannel(context)
                showNotification(context)
                Log.d(tag, "Starting RBoot at boot...")
                RbootUtils.StartRboot(context)
            } else {
                Log.d(tag, "Not enabling Rboot at boot...")
            }


        } else {
            Log.d(tag, "Intent received: ${intent.action}")
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "boot_channel"
            val name = "Boot Notification Channel"
            val descriptionText = "Channel for boot notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d(tag, "Notify channel created")
        }
    }

    private fun showNotification(context: Context) {
        val builder = NotificationCompat.Builder(context, "boot_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getApplicationName(context))
            .setContentText("App started.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(context)) {
                val notificationId = 123
                notify(notificationId, builder.build())
                Log.d(tag, "Notifica mostrata con ID: $notificationId")
            }
        } else {
            Log.d(tag, "Unable to show notification it seems permession POST_NOTIFICATIONS is missing")
        }
    }
}