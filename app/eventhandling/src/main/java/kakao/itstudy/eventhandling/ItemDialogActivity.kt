package kakao.itstudy.eventhandling

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_item_dialog.*

class ItemDialogActivity : AppCompatActivity() {
    //var mSelect = 0
    var mSelect = booleanArrayOf(false, false, false, false, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_dialog)

        call.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                /*
                AlertDialog.Builder(this@ItemDialogActivity).setTitle("언어를 선택하시오.")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setItems(R.array.language,
                        DialogInterface.OnClickListener { dialog, which ->
                            val languages =
                                resources.getStringArray(R.array.language)
                            val text =
                                findViewById<View>(R.id.text) as TextView
                            text.text = "선택한 언 = " + languages[which]
                        })
                    .setNegativeButton("취소", null)
                    .show()

                 */

                /*
                AlertDialog.Builder(this@ItemDialogActivity)
                    .setTitle("언어를 선택하시오.")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setSingleChoiceItems(R.array.language, mSelect,
                        DialogInterface.OnClickListener { dialog, which -> mSelect = which })
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            val languages =
                                resources.getStringArray(R.array.language)
                            val text =
                                findViewById<View>(R.id.text) as TextView
                            text.text = "선택한 팀 = " + languages[mSelect]
                        })
                    .setNegativeButton("취소", null)
                    .show()
                 */

                AlertDialog.Builder(this@ItemDialogActivity)
                    .setTitle("언어를 선택하시오.")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setMultiChoiceItems(R.array.language, mSelect,
                        DialogInterface.OnMultiChoiceClickListener{ dialog, which, isChecked -> mSelect[which] = isChecked })
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            val languages = getResources().getStringArray(R.array.language);
                            var result:String = "선택한 언어 = ";

                            var i = 0
                            for (s in mSelect) {
                                if (s) {
                                    result += languages[i] + " ";
                                }
                                i = i + 1
                            }
                            text.setText(result);
                        })
                    .setNegativeButton("취소", null)
                    .show()
            }
        })

    }
}