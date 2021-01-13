package kakao.itstudy.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_timer.*

class TimerActivity : AppCompatActivity() {
    var value = 0
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timer = object : CountDownTimer(10 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                value++
                text.setText("Value = $value")
            }

            override fun onFinish() {
                text.setText("finshed")
            }
        }
        btnStart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                (timer as CountDownTimer).start()
            }
        })

        btnStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                (timer as CountDownTimer).cancel()
            }
        })

    }
}