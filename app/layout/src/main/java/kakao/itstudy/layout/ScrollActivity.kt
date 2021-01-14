package kakao.itstudy.layout

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_constraint_use.*

class ScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)

        val bitmap = resources.getDrawable(R.drawable.largeimage, null)
        val bitmapWidth: Int = bitmap.getIntrinsicWidth()
        val bitmapHeight: Int = bitmap.getIntrinsicHeight()

        imageView.setImageDrawable(bitmap)
        imageView.getLayoutParams().width = bitmapWidth
        imageView.getLayoutParams().height = bitmapHeight
    }
}