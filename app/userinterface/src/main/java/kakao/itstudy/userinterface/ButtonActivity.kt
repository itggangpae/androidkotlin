package kakao.itstudy.userinterface

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_button.*

class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        btn.setOnClickListener({edit.setText("버튼 누름") })

        ColorGroup.setOnCheckedChangeListener { group, checkedId ->
            if (group.id == R.id.ColorGroup) {
                when (checkedId) {
                    R.id.Red -> MyToggle.setTextColor(Color.RED)
                    R.id.Green -> MyToggle.setTextColor(Color.GREEN)
                    R.id.Blue -> MyToggle.setTextColor(Color.BLUE)
                }
            }}

        BigFont.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.id == R.id.BigFont) {
                if (isChecked) {
                    MyToggle.textSize = 40.0f
                } else {
                    MyToggle.textSize = 20.0f
                }
            }
        }

    }
}