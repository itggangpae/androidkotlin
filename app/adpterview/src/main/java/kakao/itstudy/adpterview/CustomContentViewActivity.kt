package kakao.itstudy.adpterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.*
import android.widget.ListView

class CustomContentViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_content_view)

        //ListView에 출력할 데이터 생성
        //ListView에 출력할 데이터 생성
        val data: MutableList<VO> = ArrayList()

        var vo = VO()
        vo.icon = R.mipmap.ic_launcher
        //Stack:LIFO(Last Input First Out)-마지막에 삽입된 데이터가 먼저 출력
        //함수의 데이터 저장에 사용
        //Stack:LIFO(Last Input First Out)-마지막에 삽입된 데이터가 먼저 출력
        //함수의 데이터 저장에 사용
        vo.name = "Stack"
        data.add(vo)

        vo = VO()
        vo.icon = R.mipmap.ic_launcher
        vo.name = "Queue"
        data.add(vo)

        vo = VO()
        vo.icon = R.mipmap.ic_launcher
        vo.name = "Deque"
        data.add(vo)

        //데이터를 ListView에 출력할 수 있도록 Adapter에 주입
        val adapter = MyAdapter(
            this, data, R.layout.icontext
        )

        //리스트에 adapter 연결
        val list: ListView = findViewById<ListView>(R.id.listView)
        list.adapter = adapter

        val set = AnimationSet(true)
        val rtl: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        rtl.duration = 1000
        set.addAnimation(rtl)

        val alpha: Animation = AlphaAnimation(0.0f, 1.0f)
        alpha.duration = 1000
        set.addAnimation(alpha)

        val controller = LayoutAnimationController(set, 0.5f)
        list.layoutAnimation = controller

    }
}