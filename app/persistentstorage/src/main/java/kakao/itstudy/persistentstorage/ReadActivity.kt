package kakao.itstudy.persistentstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val intent = getIntent()
        val keyword = intent.getStringExtra("keyword")
        Log.e("keyword", keyword!!)
        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val cursor= db.rawQuery("select title, content from tb_memo where title like ? order by _id desc", arrayOf<String>("%" + keyword + "%"));
        var result:String = ""
        while (cursor.moveToNext()){
            result = result + cursor.getString(0) + "\n"
            result = result + "\t" + cursor.getString(1) + "\n"
        }
        resultView.setText(result);
        db.close();

        backBtn.setOnClickListener{
            finish()
        }
    }
}