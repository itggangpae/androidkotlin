package kakao.itstudy.etccomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = ("Broadcast intent detected : ${intent.action}")
        Toast.makeText(
            context, message,
            Toast.LENGTH_LONG
        ).show()
        when{
            intent?.action == Intent.ACTION_BOOT_COMPLETED -> {
                Log.e("메시지", "부팅 완료됨")
            }
            intent?.action == Intent.ACTION_SCREEN_OFF -> {
                Log.e("메시지", "부팅 완료됨")
            }
        }
    }
}