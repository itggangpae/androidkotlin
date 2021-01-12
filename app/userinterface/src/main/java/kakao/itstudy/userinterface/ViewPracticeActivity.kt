package kakao.itstudy.userinterface

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_practice.*

class ViewPracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_practice)

        val typeface = Typeface.createFromAsset(assets, "nanumgothic.ttf")
        fontView.setTypeface(typeface)

        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkbox.setText("is Checked")
            } else {
                checkbox.setText("is unChecked")
            }
        }
    }
}