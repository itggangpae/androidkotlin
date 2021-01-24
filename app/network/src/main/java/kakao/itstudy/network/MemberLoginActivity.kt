package kakao.itstudy.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_member_login.*
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.regex.Matcher
import java.util.regex.Pattern

class MemberLoginActivity : AppCompatActivity() {

    //로그인 처리 후 필요한 데이터를 저장할 변수
    var profileUrl: String? = null
    var email: String? = null
    var nickname: String? = null
    var regdate: String? = null

    //메시지 출력을 위한 핸들러
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            when (message.what) {
                //로그인 실패 한 경우
                0 -> {
                    val msg = message.obj as String
                    Toast.makeText(this@MemberLoginActivity, msg, Toast.LENGTH_SHORT).show()
                }
                //로그인 성공한 경우
                1 -> {
                    val result = message.obj as Boolean
                    if (result == true) {
                        Toast.makeText(this@MemberLoginActivity, "로그인 성공", Toast.LENGTH_SHORT)
                            .show()
                        ImageThread().start()
                        //키보드 관리 객체 가져오기
                        val imm: InputMethodManager = getSystemService(
                            Context.INPUT_METHOD_SERVICE
                        ) as InputMethodManager
                        imm.hideSoftInputFromWindow(emailinput.getWindowToken(), 0)
                        imm.hideSoftInputFromWindow(pwinput.getWindowToken(), 0)
                        emailinput.setText("")
                        pwinput.setText("")
                    } else {
                        Toast.makeText(this@MemberLoginActivity, "로그인 실패", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                //이미지 출력을 위한 부분
                2 -> {
                    val bit: Bitmap = message.obj as Bitmap
                    if (bit == null) {
                        Toast.makeText(
                            this@MemberLoginActivity,
                            "bitmap is null",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        profileimage.setImageBitmap(bit)
                    }
                }
            }
        }
    }

    //로그인 처리를 위한 스레드
    internal inner class ThreadEx : Thread() {
        var json: String? = null
        override fun run() {
            try {
                //다운로드 받을 주소 생성
                val url = URL("http://cyberadam.cafe24.com/member/login")
                //URL에 연결
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                con.setRequestMethod("POST")
                con.setReadTimeout(10000)
                con.setConnectTimeout(10000)
                con.setDoOutput(true)
                con.setDoInput(true)
                con.setUseCaches(false)

                //POST 방식에서의 파라미터 생성 및 전송
                var data: String =
                    URLEncoder.encode("email", "UTF-8").toString() + "=" + URLEncoder.encode(
                        emailinput.getText().toString().trim(),
                        "UTF-8"
                    )
                data += "&" + URLEncoder.encode("pw", "UTF-8").toString() + "=" + URLEncoder.encode(
                    pwinput.getText().toString().trim(),
                    "UTF-8"
                )
                val wr = OutputStreamWriter(con.getOutputStream())
                wr.write(data)
                wr.flush()

                //문자열을 다운로드 받기 위한 스트림을 생성
                val br = BufferedReader(
                    InputStreamReader(
                        con.getInputStream()
                    )
                )
                val sb = StringBuilder()
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
                Log.e("로그인 예외", e.localizedMessage)
                val message = Message()
                message.obj = "로그인 처리 에러"
                message.what = 0
                handler.sendMessage(message)
            }
            if (json != null) {
                try {
                    val `object` = JSONObject(json)
                    Log.e("데이터", `object`.toString())
                    val result: Boolean = `object`.getBoolean("login")
                    if (result == true) {
                        profileUrl = `object`.getString("profile")
                        email = `object`.getString("email")
                        nickname = `object`.getString("nickname")
                        regdate = `object`.getString("regdate")
                    }
                    try {
                        val fos: FileOutputStream = openFileOutput(
                            "login.txt",
                            Context.MODE_PRIVATE
                        )
                        val str =
                            "$email:$nickname:$profileUrl:$regdate"
                        fos.write(str.toByteArray())
                        fos.close()
                    } catch (e: Exception) {
                    }
                    val message = Message()
                    message.obj = result
                    message.what = 1
                    handler.sendMessage(message)
                } catch (e: Exception) {
                    Log.e("파싱 예외", e.localizedMessage)
                    val message = Message()
                    message.obj = "JSON 파싱 에러"
                    message.what = 0
                    handler.sendMessage(message)
                }
            }
        }
    }

    //이미지를 다운로드 받는 핸들러
    internal inner class ImageThread : Thread() {
        override fun run() {
            try {
                val `is`: InputStream =
                    URL("http://cyberadam.cafe24.com/profile/$profileUrl").openStream()
                val bit: Bitmap = BitmapFactory.decodeStream(`is`)
                `is`.close()
                val message = Message()
                message.obj = bit
                message.what = 2
                handler.sendMessage(message)
            } catch (e: Exception) {
                Log.e("이미지 다운로드 에러", e.localizedMessage)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_login)

        //이전 로그인 여부 확인
        val loginMessage = Message()
        try {
            val fis: FileInputStream = openFileInput("login.txt")
            val data = ByteArray(fis.available())
            while (fis.read(data) !== -1) {
            }
            fis.close()
            val content = String(data)
            val ar = content.split(":".toRegex()).toTypedArray()
            loginMessage.obj = String.format("%s라는 닉네임으로 로그인 하셨습니다.", ar[1])
        } catch (e: Exception) {
            loginMessage.obj = "로그인을 한 적이 없습니다."
        }
        loginMessage.what = 0
        handler.sendMessage(loginMessage)


        //로그인 버튼 클릭 이벤트 처리
        btnlogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val errorMessage = Message()
                val email: String = emailinput?.getText().toString().trim()
                val pw: String = pwinput?.getText().toString().trim()

                if (email.length == 0) {
                    errorMessage.obj = "email은 비어있을 수 없습니다."
                    handler.sendMessage(errorMessage)
                    return
                } else {
                    val regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
                    val p: Pattern = Pattern.compile(regex)
                    val m: Matcher = p.matcher(email)
                    if (m.matches() === false) {
                        errorMessage.obj = "email 형식과 일치하지 않습니다."
                        errorMessage.what = 0
                        handler.sendMessage(errorMessage)
                        return
                    }
                }
                if (pw.length == 0) {
                    errorMessage.obj = "비밀번호는 비어있을 수 없습니다."
                    errorMessage.what = 0
                    handler.sendMessage(errorMessage)
                    return
                } else {
                    val regex =
                        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}"
                    val p: Pattern = Pattern.compile(regex)
                    val m: Matcher = p.matcher(pw)
                    if (m.matches() === false) {
                        errorMessage.obj = "비밀번호는 영문 대소문자 1개 이상 특수문자 1개 숫자 1개 이상으로 만들어져야 합니다. "
                        errorMessage.what = 0
                        handler.sendMessage(errorMessage)
                        return
                    }
                }
                ThreadEx().start()
            }
        })

        //로그아웃 버튼 클릭 이벤트 처리
        btnlogout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val message = Message()
                if (deleteFile("login.txt")) {
                    message.obj = "로그아웃에 성공했습니다."
                } else {
                    message.obj = "로그인을 한 적이 없습니다."
                }
                message.what = 0
                handler.sendMessage(message)
            }
        })
    }
}