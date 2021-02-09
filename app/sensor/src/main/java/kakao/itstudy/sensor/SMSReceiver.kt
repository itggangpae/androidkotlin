package kakao.itstudy.sensor

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import androidx.core.app.NotificationCompat

//발송 확인이 되면 실행될 브로드캐스트 리시버
class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //넘어온 SMS 메시지를 획득
        val bundle = intent.extras
        val pdus = bundle!!["pdus"] as Array<Any>?
        val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(pdus!!.size)
        //메시지가 있으면 LocalNotification으로 출력
        for (i in pdus!!.indices) {
            messages[i] = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
            try {
                val message = messages[i]!!.getMessageBody().toString()
                val phoneNumber: String? = messages[i]!!.getOriginatingAddress()
                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                var builder: NotificationCompat.Builder? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelId = "one-channel"
                    val channelName = "My Channel One"
                    val channelDescription = "My Channel One Description"
                    val channel = NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    channel.description = channelDescription
                    manager.createNotificationChannel(channel)
                    builder = NotificationCompat.Builder(context, channelId)
                } else {
                    builder = NotificationCompat.Builder(context)
                }
                builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
                builder.setContentTitle("New SMS Message")
                builder.setContentText(message)
                builder.setAutoCancel(true)
                val noti: Notification = builder.build()
                manager.notify(111, noti)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
