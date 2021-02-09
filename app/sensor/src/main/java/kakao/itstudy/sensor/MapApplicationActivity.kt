package kakao.itstudy.sensor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MapApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_application)

        val btn: Button = findViewById<Button>(R.id.view)
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val latitude = 37.497844
                val longitude = 127.027531
                val pos = String.format("geo:%f,%f?z=16", latitude, longitude)
                val uri: Uri = Uri.parse(pos)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })
    }
}