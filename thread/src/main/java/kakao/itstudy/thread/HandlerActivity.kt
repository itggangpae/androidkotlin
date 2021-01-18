package kakao.itstudy.thread

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class HandlerActivity : AppCompatActivity() {
    /*
    fun click(v: View?) {
        AlertDialog.Builder(this)
            .setTitle("질문")
            .setMessage("업로드 하시겠습니까?")
            .setPositiveButton("예",
                DialogInterface.OnClickListener { dialog, whichButton -> doUpload() })
            .setNegativeButton("아니오", null)
            .show()
    }

    fun doUpload() {
        for (i in 0..9) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
            }
        }
        Toast.makeText(this, "업로드를 완료했습니다.", 0).show()
    }

     */

    /*
    fun click(v: View?) {
        AlertDialog.Builder(this)
            .setTitle("질문")
            .setMessage("업로드 하시겠습니까?")
            .setPositiveButton(
                "예"
            ) { dialog, whichButton -> mHandler.sendEmptyMessageDelayed(0, 10) }
            .setNegativeButton("아니오", null)
            .show()
    }

    var mHandler: Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            if (msg.what === 0) {
                doUpload()
            }
        }
    }

    fun doUpload() {
        for (i in 0..19) {
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
            }
        }
        Toast.makeText(this, "업로드를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
    */

    fun click(v: View?) {
        AlertDialog.Builder(this)
            .setTitle("질문")
            .setMessage("업로드 하시겠습니까?")
            .setPositiveButton(
                "예"
            ) { dialog, whichButton ->
                val btnUpload: Button = findViewById<View>(R.id.upload) as Button
                btnUpload.postDelayed(Runnable { doUpload() }, 10)
            }
            .setNegativeButton("아니오", null)
            .show()
    }

    fun doUpload() {
        for (i in 0..19) {
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
            }
        }
        Toast.makeText(this, "업로드를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
    }
}