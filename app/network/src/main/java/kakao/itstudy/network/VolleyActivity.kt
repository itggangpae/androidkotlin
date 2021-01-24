package kakao.itstudy.network

import android.R.attr.button
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_volley.*


class VolleyActivity : AppCompatActivity() {
    //RequestQueue를 저장할 변수
    var requestQueue: RequestQueue? = null

    fun makeRequest() {
        //URL 생성
        val url: String = editURL.getText().toString()
        //문자열을 다운로드 받는 StringRequest 객체 생성
        val request: StringRequest = object : StringRequest(
            Request.Method.GET,
            url,
            object : Response.Listener<String?> {
                //다운로드 받는데 성공했을 때 호출되는 메소드 - response가 다운로드 받은 데이터
                override fun onResponse(response: String?) {
                    println("응답 -> " + response);
                }
            },
            object : Response.ErrorListener{
                //에러가 발생했을 때 호출되는 메소드
                //error가 에러 객체
                override fun onErrorResponse(error: VolleyError) {
                    println("에러 -> " + error.message)
                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return HashMap()
            }
        }
        request.setShouldCache(false)
        requestQueue!!.add(request)
        println("요청 보냄.")
    }

    //매개변수를 출력하는 메소드
    fun println(data: String) {
        resultView.append(
            """
                $data
                
                """.trimIndent()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley)

        //버튼의 클릭 이벤트 처리
        btnDownload.setOnClickListener{
            makeRequest()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editURL.windowToken, 0)
        }

        //큐 생성
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext)
        }
    }
}