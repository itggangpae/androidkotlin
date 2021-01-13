package kakao.itstudy.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_longclick.*

class LongclickActivity : AppCompatActivity() {
    var cnt = 0

    var longClickListener: View.OnLongClickListener = object : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            when (v.getId()) {
                R.id.decrease -> {
                    cnt = 0
                    count.text = "" + cnt
                    return true
                }
                R.id.increase -> {
                    cnt = 100
                    count!!.text = "" + cnt
                }
            }
            return false
        }
    }

    fun onClick(v: View) {
        when (v.getId()) {
            R.id.decrease -> {
                cnt--
                count!!.text = "" + cnt
            }
            R.id.increase -> {
                cnt++
                count!!.text = "" + cnt
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_longclick)

        decrease.setOnLongClickListener(longClickListener)
        increase.setOnLongClickListener(longClickListener)

    }
}