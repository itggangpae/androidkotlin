package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_html.*
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HtmlActivity : AppCompatActivity() {
    //다운로드 받은 문자열을 저장할 변수
    var html: String? = null
    //다운로드 받는 도중 문자열을 저장할 변수
    var sBuffer: StringBuffer? = null
    //파싱이 끝나면 데이터를 출혁하기 위한 핸들러
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val data = msg.obj as String
            list.text = data
        }
    }

    //문자열을 다운로드 받는 스레드
    protected inner class ThreadEx : Thread() {
        override fun run() {
            //데이터 다운로드
            sBuffer = StringBuffer()
            try {
                val urlAddr = "http://www.hani.co.kr/"
                val url = URL(urlAddr)
                val conn =
                    url.openConnection() as HttpURLConnection
                conn.connectTimeout = 20000
                conn.useCaches = false
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val isr =
                        InputStreamReader(conn.inputStream)
                    val br = BufferedReader(isr)
                    while (true) {
                        val line = br.readLine() ?: break
                        sBuffer!!.append(line)
                    }
                    br.close()
                    conn.disconnect()
                }
            }
            catch (e: Exception) {
                Log.e("다운로드 중 에러 발생", e.message!!)
            }
            html = sBuffer.toString()
            Log.e("html", sBuffer.toString())
            if(TextUtils.isEmpty(html)){
                Toast.makeText(this@HtmlActivity, "받아온 데이터가 없음", Toast.LENGTH_LONG).show()
            }

            try {
                //데이터 파싱 - 목록 펼치기
                val doc = Jsoup.parse(html)
                //a 태그만 전부 가져오기
                val elements = doc.select("div > h4 > a")
                sBuffer = StringBuffer()
                for (link in elements) {

                    sBuffer!!.append(
                        """
    ${link.text().trim { it <= ' ' }} 
    
    """.trimIndent()
                    )
                }
                val msg = Message()
                msg.obj = sBuffer.toString()
                mHandler.sendMessage(msg)
            } catch (e: Exception) {
                Log.e("파싱 중 에러 발생", e.message!!)
            }
        }
    }


    //버튼의 클릭 이벤트
    fun click(v: View?) {
        Toast.makeText(this, "시작", Toast.LENGTH_LONG).show()
        val th = ThreadEx()
        th.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html)
    }
}