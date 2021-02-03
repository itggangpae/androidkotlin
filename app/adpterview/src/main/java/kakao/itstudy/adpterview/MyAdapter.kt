package kakao.itstudy.adpterview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MyAdapter(
    //뷰를 출력할 때 필요한 Context(문맥-어떤 작업을 하기 위해 필요한 정보를 저장한 객체) 변수
    var context: Context,
    //ListView에 출력할 데이터
    var data: List<VO>,
    //항목 뷰에 해당하는 레이아웃의 아이디를 저장할 변수
    var layout: Int
) : BaseAdapter() {

    //xml로 만들어진 레이아웃을 뷰로 변환하기 위한 클래스의 변수
    var inflater: LayoutInflater

    init {
        inflater = context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
    }

    //출력할 데이터의 개수를 설정하는 메소드
    override fun getCount(): Int {
        return data.size
    }

    //항목 뷰에 보여질 문자열을 설정하는 메소드
    //position은 반복문이 수행될 때의 인덱스
    override fun getItem(position: Int): Any {
        return data[position].name!!
    }

    //각 항목뷰의 아이디를 설정하는 메소드
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //ListView에 출력될 실제 뷰의 모양을 설정하는 메소드
    //convertView는 화면에 보여질 뷰인데 처음에는 null이 넘어오고 두번째 부터는
    //이전에 출력된 뷰가 넘어옵니다.
    //인덱스마다 다른 뷰를 출력하고자 하면 convertView를 새로 만들지만
    //모든 항목뷰의 모양이 같다면 처음 한번만 만들면 됩니다.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var returnView = convertView
        //convertView 생성
        if (returnView == null) {
            //layout에 정의된 뷰를 parent에 넣을 수 있도록 View로 생성
            returnView = inflater.inflate(layout, parent, false)
        }

        if (position % 2 == 0) returnView!!.setBackgroundColor(Color.RED)
        else returnView!!.setBackgroundColor(Color.BLUE)

        //이미지 출력
        val imgView =
            returnView.findViewById<View>(R.id.img) as ImageView
        imgView.setImageResource(data[position].icon)
        //텍스트 출력
        val txt = returnView.findViewById<View>(R.id.text) as TextView
        txt.text = data[position].name
//버튼의 이벤트 처리
        val btn =
            returnView.findViewById<View>(R.id.btn) as Button
        btn.setOnClickListener {
            val mes = "Select Item:" + data[position].name
            Toast.makeText(context, mes, Toast.LENGTH_LONG).show()
        }
        return returnView
    }
}
