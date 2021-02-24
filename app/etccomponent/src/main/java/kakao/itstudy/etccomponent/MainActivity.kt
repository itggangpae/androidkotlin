package kakao.itstudy.etccomponent

import android.content.Context
import android.content.IntentFilter
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    var manager: NotificationManager? = null

    private val CHANNEL_ID = "channel"
    private val CHANNEL_DESCRIPTION = "channel description"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(MyReceiver(), IntentFilter(Intent.ACTION_SCREEN_OFF))

        val button: Button = findViewById(R.id.button1)
        button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                showNoti1()
            }
        })

        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                showNoti2()
            }
        })
    }

    fun showNoti1() {
        /*
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_view)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this).notify(1, builder.build())

         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = CHANNEL_ID
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun showNoti2() {
    }
}