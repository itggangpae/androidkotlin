package kakao.itstudy.adpterview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val ar =
            arrayOf("박찬호", "김병현", "서재응", "봉중근", "류현진")

         */

        /*
        val ar = ArrayList<String>()
        ar.add("박찬호")
        ar.add("김병현")
        ar.add("서재응")
        ar.add("봉중근")
        ar.add("류현진")

        // 어댑터 준비
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, ar
        )
        // 어댑터 연결
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

         */

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.pl,
            android.R.layout.simple_list_item_1
        )

        // 어댑터 연결
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        listView.divider = ColorDrawable(Color.RED)
        listView.dividerHeight = 2


    }
}