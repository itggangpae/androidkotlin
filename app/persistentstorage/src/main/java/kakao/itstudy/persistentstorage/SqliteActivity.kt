package kakao.itstudy.persistentstorage

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sqlite.*

class SqliteActivity : AppCompatActivity() {
    fun clickNextTask(){
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(addTitleView.windowToken, 0)
        imm.hideSoftInputFromWindow(addContentView.windowToken, 0)

        addTitleView.setText("")
        addContentView.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        insert.setOnClickListener{
            val title = addTitleView.getText().toString()
            val content = addContentView.getText().toString()
            val helper = DBHelper(this)
            val db = helper.writableDatabase

            db.execSQL("insert into tb_memo (title, content) values (?,?)",
                arrayOf<String>(title, content))
            /*
            val row = ContentValues()
            row.put("title", title)
            row.put("content", content)
            db.insert("tb_memo", null, row)
            */

            db.close()
            clickNextTask()
            Toast.makeText(this@SqliteActivity, "삽입 성공", Toast.LENGTH_LONG).show()
        }

        select.setOnClickListener{
            val title = addTitleView.getText().toString()
            val intent = Intent(this, ReadActivity::class.java)
            intent.putExtra("keyword", title)
            startActivity(intent)
        }

        update.setOnClickListener{
            val title = addTitleView.getText().toString()
            val content = addContentView.getText().toString()
            val helper = DBHelper(this)
            val db = helper.writableDatabase
            db.execSQL("update tb_memo set content = ? where title = ?",
                arrayOf<String>(content, title))
            db.close()
            clickNextTask()
            Toast.makeText(this@SqliteActivity, "수정 성공", Toast.LENGTH_LONG).show()
        }

        delete.setOnClickListener{
            val title = addTitleView.getText().toString()
            val helper = DBHelper(this)
            val db = helper.writableDatabase
            if(TextUtils.isEmpty(title)){
                db.execSQL("delete from tb_memo")
            }else {
                db.execSQL("delete from tb_memo where title = ?", arrayOf<String>(title))
            }
            db.close()
            clickNextTask()
            Toast.makeText(this@SqliteActivity, "삭제 성공", Toast.LENGTH_LONG).show()
        }
    }
}