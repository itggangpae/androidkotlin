package kakao.itstudy.userinterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);

        val linear = LinearLayout(this)

        val bt1 = Button(this)
        bt1.setText("버튼1")
        linear.addView(bt1)

        val bt2 = Button(this)
        bt2.setText("버튼2")
        linear.addView(bt2)

        setContentView(linear)

    }
}
