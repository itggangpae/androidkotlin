package kakao.itstudy.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_popup.*

class PopupActivity : AppCompatActivity() {
    var popup: PopupWindow? = null
    var popupview : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)
        popupview = View.inflate(this, R.layout.popup, null);
        popup = PopupWindow(popupview, 500, 500, true);

        btnshow.setOnClickListener(View.OnClickListener {
            //popup?.showAtLocation(popupview, Gravity.CENTER, 0, 0);
            popup?.setAnimationStyle(-1)
            popup?.showAsDropDown(btnshow)

        });

        val btnclose = popupview?.findViewById(R.id.btnclose) as Button
        btnclose.setOnClickListener(View.OnClickListener {
            popup?.dismiss();
        });

    }
}