package kakao.itstudy.adpterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class SingleActivity : AppCompatActivity() {
    var data: MutableList<String>? = null
    var adapter: ArrayAdapter<String>? = null
    var listView:ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        //Model의 역할을 하는 데이터 생성
        data = mutableListOf<String>()
        data?.add("Oracle");
        data?.add("MySQL");
        data?.add("MongoDB");

        //Controller 역할을 하는 Adapter 객체 생성
        adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_single_choice, data!!
        )

        //View의 역할을 하는 ListView 만들기
        listView = findViewById<ListView>(R.id.listView)
        listView?.adapter = adapter
        listView?.choiceMode = ListView.CHOICE_MODE_SINGLE

        //list에서 항목을 선택했을 때 호출되는 메소드 설정
        //list에서 항목을 선택했을 때 호출되는 메소드 설정
        listView?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                //첫번째 매개변수는 이벤트가 발생한 ListView
                //두번째 매개변수는 이벤트가 발생한 항목 뷰
                //세번째 매개변수는 이벤트가 발생한 인덱스
                //네번째 매개변수는 이벤트가 발생한 항목 뷰의 아이디
                //Toast로 선택한 데이터를 출력
                val item = data!![position]
                Toast.makeText(
                    this@SingleActivity, item, Toast.LENGTH_LONG
                ).show()
            }

        val add = findViewById<Button>(R.id.add)
        add.setOnClickListener {
                val newItem = findViewById<View>(R.id.newitem) as EditText
                val item = newItem.text.toString()
                if (item != null && item.trim { it <= ' ' }.length > 0) {
                    data!!.add(item.trim { it <= ' ' })
                    //데이터가 갱신되었다는 사실을 리스트뷰에게 통보
                    //ListView를 다시 출력합니다.
                    adapter!!.notifyDataSetChanged()
                    newItem.setText("")
                }
        }

        //delete 버튼을 눌렀을 때 선택된 항목 지우기
        val delete = findViewById<Button>(R.id.delete)
        delete.setOnClickListener {
            //체크된 항목 가져오기
            val pos : Int = listView?.checkedItemPosition as Int
            if (pos >= 0 && pos < data!!.size) {
                data!!.removeAt(pos)
                //선택된 것 해제
                listView?.clearChoices()
                adapter!!.notifyDataSetChanged()
            }
        }


    }
}