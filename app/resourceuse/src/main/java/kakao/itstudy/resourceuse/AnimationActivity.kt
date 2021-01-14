package kakao.itstudy.resourceuse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val scaleBtn: Button = findViewById<View>(R.id.scaleBtn) as Button
        scaleBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val anim: Animation =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.scale1)
                v.startAnimation(anim)
            }
        })


        // 두번째 버튼 이벤트 처리
        val scale2Btn: Button = findViewById<View>(R.id.scale2Btn) as Button
        scale2Btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val anim: Animation =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.scale2)
                v.startAnimation(anim)
            }
        })
    }
}