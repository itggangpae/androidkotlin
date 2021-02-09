package kakao.itstudy.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var value: Int = 0

    /*
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what === 0) {
                txt.text = "Value : $value"
            }
        }
    }


    inner class BackThread : Thread() {
        override fun run() {
            while (value < 20) {
                value++
                try {
                    sleep(1000)
                    //txt.setText("Value : $value")
                    handler.sendEmptyMessage(0);
                } catch (e: InterruptedException) {
                    return
                }
            }
        }
    }
    */

    var handler = Handler(Looper.getMainLooper())

    inner class BackThread : Thread() {
        override fun run() {
            while (value < 20) {
                value++
                handler.post {
                    val txt = findViewById<TextView>(R.id.txt)
                    txt.text = "value : $value"
                }
                Thread.sleep(1000)
            }
        }
    }

    fun click(view: View?) {
        /*
                try {
            Thread.sleep(2000)
        } catch (e: Exception) {
        }
        txt.text = "버튼을 눌렀습니다."

         */

        /*
        while (value < 20) {
            value++
            try {
                Thread.sleep(1000)
                Log.e("Value", "${value}")
                txt.setText("Value : ${value}")
            } catch (e: InterruptedException) {
                return
            }
        }
        */

        val th: Thread = BackThread()
        th.isDaemon = true
        th.start()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
