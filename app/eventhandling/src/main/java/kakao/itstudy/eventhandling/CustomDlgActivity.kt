package kakao.itstudy.eventhandling

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_custom_dlg.*

class CustomDlgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dlg)

        call.setOnClickListener(View.OnClickListener{
            val linear =
                View.inflate(this@CustomDlgActivity, R.layout.login, null) as LinearLayout

            AlertDialog.Builder(this@CustomDlgActivity)
                .setTitle("Login")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setView(linear)
                .setPositiveButton("로그인",  DialogInterface.OnClickListener{
                        dialog, which ->
                    val id : EditText = linear.findViewById(R.id.id)
                    val password : EditText = linear.findViewById(R.id.password)
                    text.setText("id:" + id.text +
                            " password:" + password.text);
                })
                .setNegativeButton("취소", null)
                .show();
        })
    }
}