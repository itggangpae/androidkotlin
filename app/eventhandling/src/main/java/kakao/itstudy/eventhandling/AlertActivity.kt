package kakao.itstudy.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_alert.*

class AlertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)


        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // 빌더 생성 후 속성 설정
                val dlg: AlertDialog.Builder = AlertDialog.Builder(this@AlertActivity)
                dlg.setTitle("대화상자 만들기")
                dlg.setMessage("대화상자를 열었습니다.")
                dlg.setIcon(android.R.drawable.ic_dialog_alert)
                dlg.setCancelable(false)
                dlg.setNegativeButton("닫기", null)
                dlg.show()
            }
        })

    }
}