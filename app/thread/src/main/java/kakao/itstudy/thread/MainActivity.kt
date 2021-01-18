package kakao.itstudy.thread

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    fun click(view: View?) {
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
        }
        txt.text = "버튼을 눌렀습니다."
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
