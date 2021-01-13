package kakao.itstudy.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_click.*

class ClickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.apple -> mobile.text = "Apple"
            R.id.google -> mobile.text = "Google"
        }
    }

}