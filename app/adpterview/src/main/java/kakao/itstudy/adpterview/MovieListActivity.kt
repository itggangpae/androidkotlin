package kakao.itstudy.adpterview

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MovieListActivity : AppCompatActivity() {
    //가장 하단에서 스크롤 했는지 확인하기 위한 프로퍼티
    var lastitemVisibleFlag = false
    //페이지 번호를 저장하기 위한 프로퍼티
    var pageno = 1


    var json:String? = null
    var th : MovieThread? = null

    //데이터 목록을 저장할 리스트
    var movieList : MutableList<Movie>? = null
    //데이터 개수를 저장할 변수
    var count:Int? = null

    //ListView에 출력하기 위한 Adapter
    //var movieAdapter: ArrayAdapter<Movie>? = null
    var movieAdapter: MovieAdapter? = null


    var listview : ListView? = null
    var downloadview: ProgressBar? = null

    val handler : Handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message){
            movieAdapter!!.notifyDataSetChanged()
            downloadview?.visibility = View.GONE
            th = null
        }
    }

    inner class MovieThread : Thread() {
        override fun run() {
            try {
                //다운로드 받을 주소 생성
                //var url: URL = URL("http://cyberadam.cafe24.com/movie/list")
                var url: URL = URL("http://cyberadam.cafe24.com/movie/list?page=${pageno}")


                //연결 객체 생성
                val con =
                    url!!.openConnection() as HttpURLConnection
                //옵션 설정
                con.requestMethod = "GET" //전송 방식 선택
                con.useCaches = false //캐시 사용 여부 설정
                con.connectTimeout = 30000 //접속 시도 시간 설정
                con.readTimeout = 3000 //읽는데 걸리는 시간 설정
                con.doOutput = true //출력 사용
                con.doInput = true //입력 사용
//문자열을 다운로드 받기 위한 스트림을 생성
                val br =
                    BufferedReader(InputStreamReader(con.inputStream))
                val sb: StringBuilder = StringBuilder()
                //문자열을 읽어서 저장
                while (true) {
                    val line = br.readLine() ?: break
                    sb.append(line.trim())
                }
                json = sb.toString()
                //읽은 데이터 확인
                Log.e("json", json!!)
                //사용한 스트림과 연결 해제
                br.close()
                con.disconnect()
            } catch (e: Exception) {
                Log.e("다운로드 실패", e.message!!)
            }

            //json 파싱
            if(json!!.trim().length > 0){
                val data = JSONObject(json)
                count = data.getInt("count")
                val list = data.getJSONArray("list")
                var i = 0
                while (i < list.length()) {
                    val item = list.getJSONObject(i)
                    val movie = Movie()
                    movie.movieid = item.getInt("movieid")
                    movie.title = item.getString("title")
                    movie.subtitle = item.getString("subtitle")
                    movie.genre = item.getString("genre")
                    movie.rating = item.getDouble("rating")
                    movie.thumbnail = item.getString("thumbnail")
                    movie.link = item.getString("link")
                    //movieList!!.add(movie)
                    movieList!!.add(0, movie)

                    i = i + 1
                }
                Log.e("파싱 결과 - count", "${count}")
                Log.e("파싱 결과 - movieList", "${movieList.toString()}")
            }
            handler.sendEmptyMessage(0)
        }
    }

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_movie_list)
         movieList = mutableListOf<Movie>()
         if (th != null) {
             return
         }
         th = MovieThread()
         th!!.start()


         listview = findViewById<ListView>(R.id.listview)
         downloadview = findViewById<ProgressBar>(R.id.downloadview)

         /*
         movieAdapter = ArrayAdapter(
             this, android.R.layout.simple_list_item_1, movieList!!
         )
        */
         movieAdapter = MovieAdapter(
             this, movieList!!, R.layout.movie_cell
         )

         listview?.adapter = movieAdapter
         listview?.setDivider(ColorDrawable(Color.RED))
         listview?.setDividerHeight(3)

         listview?.onItemClickListener =
             AdapterView.OnItemClickListener { parent, view, position, id ->
                 //첫번째 매개변수는 이벤트가 발생한 ListView
                 //두번째 매개변수는 이벤트가 발생한 항목 뷰
                 //세번째 매개변수는 이벤트가 발생한 인덱스
                 //네번째 매개변수는 이벤트가 발생한 항목 뷰의 아이디
                 val movie: Movie = movieList!!.get(position)
                 val link: String = movie.link!!
                 val intent: Intent = Intent(this, LinkActivity::class.java)
                 intent.putExtra("link", link)
                 startActivity(intent)
             }

         listview?.setOnScrollListener(object : AbsListView.OnScrollListener {
             override fun onScroll(
                 view: AbsListView,
                 firstVisibleItem: Int,
                 visibleItemCount: Int,
                 totalItemCount: Int
             ) {
                 lastitemVisibleFlag =
                     totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount
             }


             override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                 if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                     pageno = pageno + 1
                     val cnt = 10
                     Log.e("count", count!!.toString())
                     if (pageno * cnt >= count!!) {
                         Toast.makeText(
                             this@MovieListActivity,
                             "더이상의 데이터가 없습니다.",
                             Toast.LENGTH_LONG
                         ).show()
                         Log.e("ddd", "Dfsfads")
                         return
                     }
                     if (th != null) {
                         return
                     }
                     downloadview?.visibility = View.VISIBLE
                     th = MovieThread()
                     th!!.start()
                 }
             }
         })

         val swipe_layout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
         swipe_layout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
             pageno = pageno + 1
             val cnt = 10
             if (pageno * cnt >= count!!) {
                 Toast.makeText(this@MovieListActivity, "더이상의 데이터가 없습니다.", Toast.LENGTH_LONG)
                     .show()
             } else {
                 if (th != null) {
                 } else {
                     downloadview?.visibility = View.VISIBLE
                     th = MovieThread()
                     th!!.start()
                     swipe_layout.setRefreshing(false)
                 }
             }
         })
     }
}