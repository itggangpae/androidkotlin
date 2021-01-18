package kakao.itstudy.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val intent = intent
        val text = intent.getStringExtra("TextIn")
        if (text != null) {
            stredit.setText(text)
        }

        val handler: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.btnok -> {
                        val intent = Intent()
                        intent.putExtra("TextOut", stredit.getText().toString())
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                    R.id.btncancel -> {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                }
            }
        }

        btnok.setOnClickListener(handler)
        btncancel.setOnClickListener(handler)
    }
}