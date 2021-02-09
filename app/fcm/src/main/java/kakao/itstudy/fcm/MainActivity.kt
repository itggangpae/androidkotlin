package kakao.itstudy.fcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApi
import com.google.firebase.database.*

class Post {
    var postId = ""
    var writerId = ""
    var message = ""
    var writeTime: Any = Any()
    var commentCount = 0
}

class MainActivity : AppCompatActivity() {
    // 로그에 TAG 로 사용할 문자열
    val TAG = "MainActivity"
    // 파이어베이스의 test 키를 가진 데이터의 참조 객체를 가져온다.
    val ref = FirebaseDatabase.getInstance().getReference("nickname")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 값의 변경이 있는 경우의 이벤트 리스너를 추가한다.
        ref.addValueEventListener(object : ValueEventListener {
            // 데이터 읽기가 취소된 경우 호출 된다.
            // ex) 데이터의 권한이 없는 경우
            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
            // 데이터의 변경이 감지 되면 호출된다.
            override fun onDataChange(snapshot: DataSnapshot) {
                // nickname 키를 가진 데이터 스냅샷에서 값을 읽고 문자열로 변경한다.
                val message = snapshot.value.toString()
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                // Firebase 에서 전달받은 메세지로 제목을 변경한다.
                supportActionBar?.title = message
            }
        })

        // Post 객체 생성
        val post = Post()
        // Firebase 의 Posts 참조에서 객체를 저장하기 위한 새로운 카를 생성하고 참조를 newRef 에 저장
        val newRef = FirebaseDatabase.getInstance().getReference("Posts").push()
        // 글이 쓰여진 시간은 Firebase 서버의 시간으로 설정
        post.writeTime = ServerValue.TIMESTAMP
        // 메세지는 input EditText 의 텍스트 내용을 할당
        post.message = "안녕하세요 첫번째 글입니다."
        // 글쓴 사람의 ID 는 디바이스의 아이디로 할당
        post.writerId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        // 글의 ID 는 새로 생성된 파이어베이스 참조의 키로 할당
        post.postId = newRef.key.toString()
        // Post 객체를 새로 생성한 참조에 저장
        newRef.setValue(post)
        // 저장성공 토스트 알림을 보여주고 Activity 종료
        Toast.makeText(applicationContext, "공유되었습니다.", Toast.LENGTH_SHORT).show()

        /*
        val ref = FirebaseDatabase.getInstance().getReference("nickname")
        ref.removeValue()
         */

        val ref = FirebaseDatabase.getInstance().getReference()
        val childUpdates:MutableMap<String, Any> = mutableMapOf()
        val userValue:MutableMap<String, Any> = mutableMapOf()
        userValue.put("message", "데이터를 2번째 수정")

        childUpdates.put("/Posts/" + "-MT2ubeOLqDLCCrAMrSA", userValue);
        ref.updateChildren(childUpdates);
        Toast.makeText(applicationContext, "수정 성공.", Toast.LENGTH_SHORT).show()



    }
}