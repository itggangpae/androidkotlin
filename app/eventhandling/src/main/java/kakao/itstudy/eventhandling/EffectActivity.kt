package kakao.itstudy.eventhandling

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import kotlinx.android.synthetic.main.activity_effect.*

class EffectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_effect)

        val clickListener = View.OnClickListener {
            if (it === btn_vibration) {
                val vib =
                    getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                    vib.vibrate(
                        VibrationEffect.createOneShot(
                            500,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                }
                else {
                    vib.vibrate(1000)
                }
            }
            else if (it === btn_system_beep) {
                val notification: Uri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val ringtone =
                    RingtoneManager.getRingtone(applicationContext, notification)
                ringtone.play()
            } else if (it === btn_custom_sound) {
                val player: MediaPlayer = MediaPlayer.create(this, R.raw.buttoneffect)
                player.start()
            }
        }

        btn_vibration.setOnClickListener(clickListener)
        btn_system_beep.setOnClickListener(clickListener)
        btn_custom_sound.setOnClickListener(clickListener)
    }
}