package kakao.itstudy.multimedia

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import kotlin.concurrent.timer

private const val REQUEST_READ_EXTERNAL_STORAGE = 1000

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        // 권한이 부여되었는지 확인 - 외부 저장 장치를 사용하는 권한 확인 - 1
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // 권한이 허용되지 않음 - 2
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 이전에 이미 권한이 거부되었을 때 설명 - 3
                alert("사진 정보를 얻기 위해서는 외부 저장소 권한이 필수로 필요합니다", "권한이 필요한 이유") {
                    yesButton {
                        // 권한 요청
                        ActivityCompat.requestPermissions(this@GalleryActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton { }
                }.show()
            } else {
                // 권한 요청 - 4
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else {
            // 권한이 이미 허용됨 - 5
            getAllPhotos()
        }
    }

    //권한 허용 여부 결과가 오면 호출되는 메소드
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //외부 저장 장치 권한에 대한 결과 처리
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty()
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 권한 허용됨
                    getAllPhotos()
                } else {
                    // 권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    //사진을 가져오기
    private fun getAllPhotos() {
        val fragments = mutableListOf<Fragment>()

        // 모든 사진 정보 가져오기
        val query = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,    // 데이터 위
            null,       // 어떤 항목의 데이터를 가져올 지 : select
            null,       // 어떤 조건의 데이터를 가져올 지 : where
            null,   // selection에 ?가 있는 경우 ?의 실제 데이터
            "${MediaStore.Images.ImageColumns.DATE_ADDED} DESC")    // 정렬 조건

        // Scoped Storage 대응
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                fragments.add(PhotoFragment.newInstance(contentUri))
            }
        }

        // 어댑터
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter

        // 3초마다 자동으로 슬라이드
        timer(period = 3000) {
            runOnUiThread {
                if (viewPager.currentItem < adapter.count - 1) {
                    viewPager.currentItem = viewPager.currentItem + 1
                } else {
                    viewPager.currentItem = 0
                }
            }
        }
    }
}