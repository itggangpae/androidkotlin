package kakao.itstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        call.setOnClickListener{
            //val intent = Intent(this@MainActivity, SubActivity::class.kt)
            //암시적 인텐트 사용
            val intent : Intent = Intent()
            intent.setAction("com.example.ACTION_VIEW")
            startActivity(intent)
        }
    }
}
