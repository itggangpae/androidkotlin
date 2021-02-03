package kakao.itstudy.adpterview

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.SimpleCursorAdapter

class CusorListActicity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cusor_list_acticity)

        val titleView: ListView =
            findViewById<ListView>(R.id.main_listview_simple)
        val cursorView: ListView =
            findViewById<ListView>(R.id.main_listview_cursor)

        val jobData: MutableList<Map<String, String>> =
            ArrayList()

        val helper = JobDBHelper(this)
        val db = helper.readableDatabase
        val cursor: Cursor = db.rawQuery("select * from job_data", null)
        while (cursor.moveToNext()) {
            val map: HashMap<String, String> = HashMap()
            map["name"] = cursor.getString(1)
            map["content"] = cursor.getString(2)
            jobData.add(map)
        }

        val titleAdapter = SimpleAdapter(
            this,
            jobData,
            android.R.layout.simple_list_item_2,
            arrayOf("name", "content"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        titleView.setAdapter(titleAdapter)

        val cursorAdapter: CursorAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf("name", "content"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        cursorView.setAdapter(cursorAdapter)

    }
}