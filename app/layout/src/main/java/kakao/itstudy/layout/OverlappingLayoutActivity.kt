package kakao.itstudy.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_overlapping_layout.*

class OverlappingLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlapping_layout)

        //btn1 눌렀을 때 처리
        btn1.setOnClickListener{v ->  page1.setVisibility(View.VISIBLE)
            page2.setVisibility(View.INVISIBLE)
            page3.setVisibility(View.INVISIBLE)
            btn1.setVisibility(View.INVISIBLE)
            btn2.setVisibility(View.VISIBLE)
            btn3.setVisibility(View.VISIBLE)
        }

        btn2.setOnClickListener{v ->  page1.setVisibility(View.VISIBLE)
            page1.setVisibility(View.INVISIBLE)
            page2.setVisibility(View.VISIBLE)
            page3.setVisibility(View.INVISIBLE)
            btn1.setVisibility(View.VISIBLE)
            btn2.setVisibility(View.INVISIBLE)
            btn3.setVisibility(View.VISIBLE)
        }

        btn3.setOnClickListener{v ->  page1.setVisibility(View.VISIBLE)
            page1.setVisibility(View.INVISIBLE)
            page2.setVisibility(View.INVISIBLE)
            page3.setVisibility(View.VISIBLE)
            btn1.setVisibility(View.VISIBLE)
            btn2.setVisibility(View.VISIBLE)
            btn3.setVisibility(View.INVISIBLE)
        }

    }
}