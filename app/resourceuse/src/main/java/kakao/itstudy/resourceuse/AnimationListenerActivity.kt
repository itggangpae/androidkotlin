package kakao.itstudy.resourceuse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout

class AnimationListenerActivity : AppCompatActivity() {
    var isPageOpen = false
    var translateLeftAnim: Animation? = null
    var translateRightAnim: Animation? = null

    var slidingPage01: LinearLayout? = null
    var openBtn01: Button? = null

    //애니메이션 리스너
    inner class SlidingPageAnimationListener : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {

            if(isPageOpen) {
                slidingPage01?.setVisibility(View.INVISIBLE)
                openBtn01?.setText("Open")
                isPageOpen = false
            } else {
                openBtn01?.setText("Close")
                isPageOpen = true
            }

        }

        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_listener)

        // 슬라이딩으로 보여질 레이아웃 객체 참조
        slidingPage01 = findViewById<View>(R.id.slidingPage01) as LinearLayout

        // 애니메이션 객체 로딩
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left)
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right)

        // 애니메이션 객체에 리스너 설정
        val animListener = SlidingPageAnimationListener()
        translateLeftAnim!!.setAnimationListener(animListener)
        translateRightAnim!!.setAnimationListener(animListener)

        // 버튼 이벤트 처리
        openBtn01 = findViewById<View>(R.id.openBtn01) as Button
        openBtn01!!.setOnClickListener { // 애니메이션 적용
            Log.e("isPageOpen", "$isPageOpen")
            if (isPageOpen) {
                slidingPage01!!.startAnimation(translateRightAnim)
            } else {
                slidingPage01!!.visibility = View.VISIBLE
                slidingPage01!!.startAnimation(translateLeftAnim)
            }
        }


    }
}