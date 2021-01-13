package kakao.itstudy.eventhandling

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_variety_dialog.*
import java.util.*

class VarietyDialogActivity : AppCompatActivity() {
    var customDialog: AlertDialog? = null
    var listDialog: AlertDialog? = null
    var alertDialog: AlertDialog? = null

    private fun showToast(message: String) {
        Log.e("message", message)
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    var dialogListener = DialogInterface.OnClickListener {
            dialog, which ->
        if (dialog === customDialog && which == DialogInterface.BUTTON_POSITIVE) {
            showToast("custom dialog 확인 click....")
        } else if (dialog === listDialog) {
            val datas =
                resources.getStringArray(R.array.database)
            showToast(datas[which].toString() + "선택하셨습니다.")
        } else if (dialog === alertDialog && which == DialogInterface.BUTTON_POSITIVE) {
            showToast("alert dialog ok click....")
        }
    }

    val clickListener = View.OnClickListener {
        if(it==btn_alert){
            val builder:AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setTitle("알림")
            builder.setMessage("정말 종료 하시겠습니까?")
            builder.setPositiveButton("OK", dialogListener)
            builder.setNegativeButton("NO", null)
            alertDialog=builder.create()
            alertDialog?.show()
        }else if(it==btn_list){
            val builder:AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("데이터베이스")
            builder.setSingleChoiceItems(R.array.database, 0, dialogListener)
            builder.setPositiveButton("확인", null)
            builder.setNegativeButton("취소", null)
            listDialog=builder.create()
            listDialog?.show()
        }
        else if(it==btn_progress){
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setIcon(android.R.drawable.ic_dialog_alert)
            progressDialog.setTitle("Wait..")
            progressDialog.setMessage("서버 연동중입니다. 잠시만 기다리세요.")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressDialog.setIndeterminate(true)
            progressDialog.show()
        }else if(it==btn_date) {
            val c: Calendar = Calendar.getInstance()
            val year : Int = c.get(Calendar.YEAR)
            val month : Int = c.get(Calendar.MONTH)
            val day : Int = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog : DatePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year,  month, dayOfMonth ->
                    showToast("${year} : ${month+1} : ${dayOfMonth} ")
                }, year, month, day);
            datePickerDialog.show()
        }
        else if(it==btn_time){
            val c: Calendar = Calendar.getInstance()
            val hour:Int=c.get(Calendar.HOUR_OF_DAY)
            val minute:Int=c.get(Calendar.MINUTE)
            val timePickerDialog: TimePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener {
                        view, hourOfDay, minute ->
                    showToast("${hourOfDay} : ${minute}")
                }, hour, minute, false)
            timePickerDialog.show();
        }else if(it==btn_custom){
            val builder:AlertDialog.Builder = AlertDialog.Builder(this)
            val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view:View = inflater.inflate(R.layout.dialog_layout, null)
            builder.setView(view)
            builder.setPositiveButton("확인", dialogListener)
            builder.setNegativeButton("취소", null)
            customDialog=builder.create()
            customDialog?.show()
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_variety_dialog)

        btn_alert.setOnClickListener(clickListener)
        btn_list.setOnClickListener(clickListener)
        btn_progress.setOnClickListener(clickListener)
        btn_date.setOnClickListener(clickListener)
        btn_time.setOnClickListener(clickListener)
        btn_custom.setOnClickListener(clickListener)

    }
}