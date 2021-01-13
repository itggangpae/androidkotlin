package kakao.itstudy.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_async_dialog.*

class AsyncDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_dialog)

        /*
        call.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                }
                AlertDialog.Builder(this@AsyncDialogActivity)
                    .setTitle("에러 발생")
                    .setMessage("에러가 발생해서 종료합니다.")
                    .setPositiveButton("종료", null)
                    .show()
                finish()
                Toast.makeText(this@AsyncDialogActivity, "작업이 무사히 끝났습니다.", Toast.LENGTH_LONG).show()
            }
        })
         */

        call.setOnClickListener(object : View.OnClickListener {
            override open fun onClick(v: View?): Unit {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                }
                AlertDialog.Builder(this@AsyncDialogActivity).setTitle("에러 발생")
                    .setMessage("에러가 발생해서 종료합니다.")
                    .setPositiveButton(
                        "종료"
                    ) { dialog, whichButton ->
                        finish()
                        Toast.makeText(
                            this@AsyncDialogActivity, "작업이 무사히 끝났습니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .show()
            }
        })

    }
}