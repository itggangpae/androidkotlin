package kakao.itstudy.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_detail.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ItemDetailActivity : AppCompatActivity() {
    //Main에서 넘겨받은 itemid를 저장하기 위한 변수
    var itemid = 7

    //파싱한 결과를 저장하기 위한 변수
    var map: MutableMap<String, Any>? = null

    //다운로드 받은 문자열을 저장하기 위한 변수
    var json: String? = null

    //출력을 위한 핸들러
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            //메시지 출력
            if (msg.what === 0) {
                val str = msg.obj as String
                Toast.makeText(this@ItemDetailActivity, str, Toast.LENGTH_SHORT).show()
            } else if (msg.what === 1) {
                map = msg.obj as MutableMap<String, Any>
                itemname?.setText(map!!["itemname"] as String?)
                description?.setText(map!!["description"] as String?)
                price?.setText(map!!["price"] as String?)
                //이미지 다운로드
                val th = ImgThread(map!!["pictureurl"] as String?)
                th.start()
            } else if (msg.what === 2) {
                val bit: Bitmap = msg.obj as Bitmap
                if (bit == null) {
                    Toast.makeText(this@ItemDetailActivity, "bitmap is null", Toast.LENGTH_LONG)
                        .show()
                } else {
                    picture!!.setImageBitmap(bit)
                }
            }
        }
    }

    //이미지를 다운로드 받는 스레드
    internal inner class ImgThread(//파일이름을 넘겨받아서 저장하는 생성자
        var filename: String?
    ) : Thread() {
        override fun run() {
            try {
                //서버의 이미지 파일을 읽기 위한 스트림을 생성해서 이미지를 Bitmap에 저장
                val `is`: InputStream =
                    URL("http://cyberadam.cafe24.com/img/$filename").openStream()
                val bit: Bitmap = BitmapFactory.decodeStream(`is`)
                `is`.close()
                //핸들러를 호출
                val message = Message()
                message.obj = bit
                message.what = 2
                handler.sendMessage(message)
            } catch (e: Exception) {
                Log.e("이미지 다운로드 에러", e.message!!)
            }
        }
    }

    //상세 데이터를 다운로드 받는 스레드
    internal inner class ThreadEx : Thread() {
        //다운로드 받은 문자열을 저장하기 위한 인스턴스 생성
        var sb = StringBuilder()
        override fun run() {
            try {
                itemid = 7
                //URL 생성
                val url =
                    URL("http://cyberadam.cafe24.com/item/detail?itemid=$itemid")
                //URL에 연결
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                //옵션 설정
                con.setRequestMethod("GET") //전송 방식 선택
                con.setUseCaches(false) //캐시 사용 여부 설정
                con.setConnectTimeout(30000) //접속 시도 시간 설정
                con.setReadTimeout(3000) //읽는데 걸리는 시간 설정
                con.setDoOutput(true) //출력 사용
                con.setDoInput(true) //입력 사용
//문자열을 다운로드 받기 위한 스트림을 생성
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                //문자열을 읽어서 저장
                while (true) {
                    val line: String = br.readLine() ?: break
                    sb.append(
                        """
                            $line
    """.trimIndent()
                    )
                }
                //사용한 스트림과 연결 해제
                br.close()
                con.disconnect()
                json = sb.toString()
            } catch (e: Exception) {
                Log.e("다운로드 실패", e.localizedMessage)
                val msg = Message()
                msg.obj = "다운로드 실패"
                msg.what = 0
                handler.sendMessage(msg)
            }
            try {
                Log.e("문자열", json.toString())
                //데이터 파싱
                val result = JSONObject(json)
                val item: JSONObject = result.getJSONObject("item")
                if (item != null) {
                    //파싱한 결과를 저장
                    map = mutableMapOf<String, Any>()
                    map?.put("itemid", item.getInt("itemid"))
                    map?.put("itemname", item.getString("itemname"))
                    map?.put("price", item.getString("price"))
                    map?.put("description", item.getString("description"))
                    map?.put("pictureurl", item.getString("pictureurl"))
                    //핸들러 호출
                    val msg = Message()
                    msg.obj = map
                    msg.what = 1
                    handler.sendMessage(msg)
                }
                else {
                    val msg = Message()
                    msg.obj = "잘못된 itemid 입니다. \nitemid를 확인하세요"
                    msg.what = 0
                    handler.sendMessage(msg)
                }
            } catch (e: Exception) {
                Log.e("파싱에러", e.localizedMessage)
                val msg = Message()
                msg.obj = "파싱 에러"
                msg.what = 0
                handler.sendMessage(msg)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        ThreadEx().start()
    }
}