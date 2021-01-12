package kakao.itstudy.userinterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_view_attribute.*

class ViewAttributeActivity : AppCompatActivity(){
    //private var trueBtn : Button? = null
    //private var falseBtn : Button? = null
    //private var targetTextView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attribute)

        //trueBtn=findViewById(R.id.btn_visible_true)
        //targetTextView=findViewById(R.id.text_visible_target)
        //falseBtn=findViewById(R.id.btn_visible_false)

        btn_visible_true?.setOnClickListener {
            text_visible_target?.visibility = View.VISIBLE
        }

        btn_visible_false?.setOnClickListener {
            text_visible_target?.visibility = View.INVISIBLE
        }
    }
}