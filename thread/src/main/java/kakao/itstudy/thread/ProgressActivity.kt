package kakao.itstudy.thread

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {
    var value = 0

    var mProgress: ProgressDialog? = null
    var isQuit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
    }

    /*
    fun click(v: View?) {
        value = 0
        update()
    }

    fun update() {
        for (i in 0..99) {
            value++
            text!!.text = Integer.toString(value)
            try {
                Thread.sleep(50)
            } catch (e: InterruptedException) {
            }
        }
    }

     */

    /*
    fun click(v: View?) {
        value = 0
        handler.sendEmptyMessage(0)
    }

    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            value++
            text!!.text = Integer.toString(value)
            try {
                Thread.sleep(50)
            } catch (e: InterruptedException) {
            }
            if (value < 100) {
                this.sendEmptyMessage(0)
            }
        }
    }
    */

    /*
    fun click(v: View?) {
        value = 0
        mProgress = ProgressDialog(this)
        mProgress!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        mProgress!!.setTitle("Updating")
        mProgress!!.setMessage("Wait...")
        mProgress!!.setCancelable(false)
        mProgress!!.show()
        isQuit = false
        handler.sendEmptyMessage(0)
    }

    val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg:Message) {
            value++
            text?.setText(Integer.toString(value))
            try { Thread.sleep(50) } catch (e:InterruptedException ) {}
            if (value < 100 && isQuit == false) {
                mProgress?.setProgress(value)
                this.sendEmptyMessage(0)
            } else {
                mProgress?.dismiss()
            }
        }
    }

     */

    fun click(v: View?) {
        value = 0
        isQuit = false
        handler.sendEmptyMessage(0)
    }

    val handler : Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg:Message) {
            value++
            text?.setText(Integer.toString(value))
            try { Thread.sleep(50)} catch (e:InterruptedException ) {}
            if (value < 100 && isQuit == false) {
                //mProgress?.setProgress(value)
                progress?.setProgress(value)
                this.sendEmptyMessage(0)
            } else {
                //mProgress?.dismiss()
            }
        }
    }

}
