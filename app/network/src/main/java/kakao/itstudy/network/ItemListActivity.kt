package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import kotlinx.android.synthetic.main.activity_item_list.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ItemListActivity : AppCompatActivity() {
    //결과를 출력하기 위한 변수
    var result = ""

    //파싱한 후 결과를 출력하기 위한 핸들러
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            lblResult.text = result
        }
    }

    //데이터를 다운로드 받는 스레드
    internal inner class ThreadEx : Thread() {
        //다운로드 받은 문자열을 저장하기 위한 인스턴스 생성
        var sb = StringBuilder()
        override fun run() {
            try {
                //다운로드 받을 주소 생성
                var url: URL = URL("http://cyberadam.cafe24.com/item/list")

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

                //문자열을 읽어서 저장
                while (true) {
                    val line = br.readLine() ?: break
                    sb.append(
                        """
                        ${line}
                        
                        """.trimIndent()
                    )
                }
                //읽은 데이터 확인
                Log.e("html", sb.toString())
                //사용한 스트림과 연결 해제
                br.close()
                con.disconnect()
            } catch (e: Exception) {
                Log.e("다운로드 실패", e.message!!)
                result = "다운로드 실패"
                handler.sendEmptyMessage(0)
                return
            }
            try {
                result = ""
                //데이터 파싱을 위해서 다운로드 받은 문자열을 JSON 객체로 변환
                val `object` = JSONObject(sb.toString())
                //result 키의 값을 배열로 가져오기
                val itemList = `object`.getJSONArray("list")
                //배열을 순회하면서 객체에 대입
                var i = 0
                while (i < itemList.length()) {
                    val item = itemList.getJSONObject(i)
                    //객체에서 itemid 와 itemname의 값을 가져와서 result에 추가
                    result += item.getString("itemid") + "\t"
                    result += """
                        ${item.getString("itemname")}
                        
                        """.trimIndent()
                    i = i + 1
                }
                //핸들러 호출
                handler.sendEmptyMessage(0)
            } catch (e: Exception) {
                Log.e("파싱에러", e.message!!)
                result = "파싱에러"
                handler.sendEmptyMessage(0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        ThreadEx().start()
    }
}