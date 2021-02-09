package kakao.itstudy.supportlibrary

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat


//static 프로퍼티 생성
abstract class ItemVO {
    abstract val type: Int
    companion object {
        val TYPE_HEADER = 0
        val TYPE_DATA = 1
    }
}

//헤더에 사용할 데이터 클래스
class HeaderItem(var date: String) : ItemVO() {
    override val type: Int
        get() = ItemVO.TYPE_HEADER
}

//항목에 사용할 데이터 클래스
internal class DataItem(var id: Int, var title: String, var content: String, var completed: Boolean = false) : ItemVO() {
    override val type: Int
        get() = ItemVO.TYPE_DATA
}

class ToDoListActivity : AppCompatActivity() {

    //데이터 목록을 저장할 List
    var list: MutableList<ItemVO> = mutableListOf()

    //항목을 출력할 어댑터 설정
    inner class MyAdapter(val list: MutableList<ItemVO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        //아이템 항목의 종류를 설정하는 함수
        override fun getItemViewType(position: Int): Int {
            return list.get(position).type
        }

        //헤더와 항목 뷰를 설정하는 함수
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == ItemVO.TYPE_HEADER) {
                //parent?.context : parent가 not null이면 context가 리턴되고 null이면 null이 리턴된다.
                val layoutInflater = LayoutInflater.from(parent?.context)
                return HeaderViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false))
            } else {
                val layoutInflater = LayoutInflater.from(parent?.context)
                return DataViewHolder(layoutInflater.inflate(R.layout.item_main, parent, false))
            }
        }

        //뷰에 데이터를 바인딩 하는 함수
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val itemVO = list.get(position)
            if (itemVO.type == ItemVO.TYPE_HEADER) {
                //as .. type casting
                val viewHolder = holder as HeaderViewHolder
                val headerItem = itemVO as HeaderItem
                viewHolder.headerView!!.setText(headerItem.date)
            } else {
                val viewHolder = holder as DataViewHolder
                val dataItem = itemVO as DataItem
                viewHolder.itemTitleView.setText(dataItem.title)
                viewHolder.itemContentView.setText(dataItem.content)

                if(dataItem.completed){
                    viewHolder.completedIconView.setImageResource(R.drawable.icon_completed)
                }else {
                    viewHolder.completedIconView.setImageResource(R.drawable.icon)
                }

                viewHolder.completedIconView.setOnClickListener{
                    val helper = DBHelper(this@ToDoListActivity)//inner 라는 예약어가 클래스에 추가되어 있어야 한다.
                    val db=helper.writableDatabase

                    if(dataItem.completed){
                        db.execSQL("update tb_todo set completed=? where _id=?", arrayOf(0, dataItem.id))
                        viewHolder.completedIconView.setImageResource(R.drawable.icon)
                    }else {
                        db.execSQL("update tb_todo set completed=? where _id=?", arrayOf(1, dataItem.id))
                        viewHolder.completedIconView.setImageResource(R.drawable.icon_completed)
                    }
                    dataItem.completed = !dataItem.completed
                    db.close()
                }
            }
        }

        //출력할 항목의 개수 설정
        override fun getItemCount(): Int {
            return list.size
        }
    }

    //항목 뷰를 설정하는 클래스
    inner class MyDecoration() : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        )  {
            super.getItemOffsets(outRect, view, parent, state)
            val index = parent!!.getChildAdapterPosition(view)
            val itemVO = list.get(index)
            if (itemVO.type == ItemVO.TYPE_DATA) {
                view!!.setBackgroundColor(0xFFFFFFFF.toInt())
                ViewCompat.setElevation(view, 10.0f)
            }
            outRect!!.set(20, 10, 20, 10)
        }
    }

    //데이터베이스를 읽이서 list에 저장하고 RecyclerView에 데이터를 설정하는 함수
    private fun selectDB(){
        list = mutableListOf()
        val helper = DBHelper(this)
        val db = helper.readableDatabase
        val cursor = db.rawQuery("select * from tb_todo order by date desc", null)

        var preDate: Calendar? = null
        while (cursor.moveToNext()) {
            val dbdate=cursor.getString(3)
            val date = SimpleDateFormat("yyyy-MM-dd").parse(dbdate)
            val currentDate = GregorianCalendar()
            currentDate.time = date

            if(!currentDate.equals(preDate)) {
                val headerItem = HeaderItem(dbdate)
                list.add(headerItem)
                preDate=currentDate
            }

            val completed= cursor.getInt(4) != 0
            val dataItem = DataItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), completed)
            list.add(dataItem)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(list)
        recyclerView.addItemDecoration(MyDecoration())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        //데이터 가져오는 함수를 호출
        selectDB()

        //플로팅 액션 버튼의 클릭 이벤트 처리
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivityForResult(intent, 10)
        }
    }


    //하위 Activity를 호출한 후 호출되는 함수 재정의
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10 && resultCode== Activity.RESULT_OK){
            selectDB()
        }
    }

    //헤더 뷰 설정
    class HeaderViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val headerView = view?.findViewById<TextView>(R.id.itemHeaderView)
    }

    //항목 뷰 설정
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val completedIconView = view?.findViewById<ImageView>(R.id.completedIconView)
        val itemTitleView = view?.findViewById<TextView>(R.id.itemTitleView)
        val itemContentView = view?.findViewById<TextView>(R.id.itemContentView)
    }
}