package kakao.itstudy.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_basic_app.*


class BasicAppActivity : AppCompatActivity() {
    var clickHandler: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            if (v === btn_contacts) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(intent, 10)
            } else if (v === btn_speech) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식 테스트")
                startActivityForResult(intent, 50)
            } else if (v === btn_camera_data) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 30)
            } else if (v === btn_map) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952,126.9779451"))
                startActivity(intent)
            } else if (v === btn_browser) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"))
                startActivity(intent)
            } else if (v === btn_call) {
                if (ContextCompat.checkSelfPermission(
                        this@BasicAppActivity,
                        Manifest.permission.CALL_PHONE
                    ) === PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3790-1997"))
                    startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(
                        this@BasicAppActivity,
                        arrayOf<String>(Manifest.permission.CALL_PHONE),
                        100
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_app)

        btn_contacts.setOnClickListener(clickHandler);
        btn_camera_data.setOnClickListener(clickHandler);
        btn_speech.setOnClickListener(clickHandler);
        btn_map.setOnClickListener(clickHandler);
        btn_browser.setOnClickListener(clickHandler);
        btn_call.setOnClickListener(clickHandler);

    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            val result = data?.dataString
            resultView.setText(result)
        } else if (requestCode == 30 && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras!!["data"] as Bitmap?
            resultImageView.setImageBitmap(bitmap)
        } else if (requestCode == 50 && resultCode == Activity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val result = results!![0]
            resultView.setText(result)
        }
    }

}