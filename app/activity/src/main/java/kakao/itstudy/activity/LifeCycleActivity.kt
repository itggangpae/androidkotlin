package kakao.itstudy.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_life_cycle.*

class LifeCycleActivity : AppCompatActivity() {
    var datas: ArrayList<String>? = null
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

        val clickListener: View.OnClickListener = object : View.OnClickListener {

            override fun onClick(v: View) {
                if (v === main_btn_detail) {
                    val intent = Intent(this@LifeCycleActivity, SubLifeActivity::class.java)
                    startActivity(intent)
                } else if (v === main_btn_dialog) {
                    val intent = Intent(this@LifeCycleActivity, DialogActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        main_btn_detail.setOnClickListener(clickListener)
        main_btn_dialog.setOnClickListener(clickListener)

        datas = ArrayList()
        datas!!.add("onCreate....")
        adapter = ArrayAdapter(this, R.layout.item_main_list, datas!!)
        main_list.setAdapter(adapter)

    }

    override fun onResume() {
        super.onResume()
        datas!!.add("onResume....")
        adapter!!.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        datas!!.add("onPause....")
        adapter!!.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        datas!!.add("onStart....")
        adapter!!.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        datas!!.add("onStop....")
        adapter!!.notifyDataSetChanged()
    }

    override fun onRestart() {
        super.onRestart()
        datas!!.add("onRestart....")
        adapter!!.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        datas!!.add("onDestory....")
        adapter!!.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        datas!!.add("onSaveInstanceStatel....")
        adapter!!.notifyDataSetChanged()
        outState.putString("data1", "hello")
        outState.putInt("data2", 100)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        datas!!.add("onRestoreInstanceState....")
        adapter!!.notifyDataSetChanged()
        val data1 = savedInstanceState.getString("data1")
        val data2 = savedInstanceState.getInt("data2")
        Toast.makeText(this, "$data1:$data2", Toast.LENGTH_SHORT).show()
    }

}