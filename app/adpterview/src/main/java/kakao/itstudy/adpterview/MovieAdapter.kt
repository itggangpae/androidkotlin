package kakao.itstudy.adpterview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import java.net.URL

class MovieAdapter(
    //뷰를 출력할 때 필요한 Context(문맥-어떤 작업을 하기 위해 필요한 정보를 저장한 객체) 변수
    var context: Context,
    //ListView에 출력할 데이터
    var data: MutableList<Movie>,
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
        return data[position].title!!
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

        //이미지 출력
        val imgView =
            returnView?.findViewById<ImageView>(R.id.movieimage)
        var imagethread = ImageThread()
        imagethread.imagename = data[position].thumbnail
        imagethread.imageview = imgView
        imagethread.start()
        //텍스트 출력
        val titleview = returnView?.findViewById<TextView>(R.id.movietitle)
        titleview?.text = data[position].title

        val subtitleview = returnView?.findViewById<TextView>(R.id.moviesubtitle)
        subtitleview?.text = data[position].subtitle

        val ratingview = returnView?.findViewById<RatingBar>(R.id.movierating)
        ratingview?.rating = (data[position].rating!! / 5).toFloat()
        Log.e("rating", "${ratingview?.rating}")
        return returnView
    }

    inner class ImageThread : Thread(){
        var imagename:String? = null
        var imageview:ImageView? = null

        override fun run(){
            val inuptStream = URL("http://cyberadam.cafe24.com/movieimage/${imagename}").openStream()
            val bit = BitmapFactory.decodeStream(inuptStream)
            inuptStream.close()
            val message = Message()
            val map = mutableMapOf<String, Any>()
            map.put("bit", bit)
            map.put("imageview", imageview!!)
            message.obj = map
            imageHandler.sendMessage(message)
        }
    }
    val imageHandler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg : Message){
            val map = msg.obj as MutableMap<String, Any>
            val imageview = map.get("imageview") as ImageView
            val bit = map.get("bit") as Bitmap
            imageview.setImageBitmap(bit)
        }
    }
}