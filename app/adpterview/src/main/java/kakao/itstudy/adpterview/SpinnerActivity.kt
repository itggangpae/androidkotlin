package kakao.itstudy.adpterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class SpinnerActivity : AppCompatActivity() {
    var adspin: ArrayAdapter<CharSequence>? = null
    var mInitSpinner = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)

        val spin = findViewById<Spinner>(R.id.myspinner)
        spin.prompt = "분야를 고르세요."

        adspin = ArrayAdapter.createFromResource(
            this, R.array.departments,
            android.R.layout.simple_spinner_item
        )
        adspin!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adspin

        val i = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (mInitSpinner == false) {
                    mInitSpinner = true;
                    return;
                }
                Toast.makeText(this@SpinnerActivity, "${adspin!!.getItem(position)}를 선택"
                    , Toast.LENGTH_LONG).show();
            }
        }
        spin.setOnItemSelectedListener(i)
    }
}