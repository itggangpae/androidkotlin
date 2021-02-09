package kakao.itstudy.sensor

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.pedro.library.AutoPermissions.Companion.loadAllPermissions
import com.pedro.library.AutoPermissions.Companion.parsePermissions
import com.pedro.library.AutoPermissionsListener

class LocationActivity : AppCompatActivity(), AutoPermissionsListener {
    var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        textView = findViewById(R.id.textView)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startLocationService()
            }
        })

        loadAllPermissions(this, 101)

    }

    //권한 요청을 위한 메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
        parsePermissions(
            this,
            requestCode,
            permissions as Array<String>,
            this
        )
    }

    //권한을 거부한 경우 호출되는 메소드
    override fun onDenied(requestCode: Int, permissions: Array<String>) {
        Toast.makeText(
            this, "permissions denied : " + permissions.size,
            Toast.LENGTH_LONG
        ).show()
    }

    //권한 사용을 허가한 경우 호출되는 메소
    override fun onGranted(requestCode: Int, permissions: Array<String>) {
        Toast.makeText(this, "permissions granted : " + permissions.size, Toast.LENGTH_LONG).show()
    }

    inner class GPSListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude: Double = location.getLatitude()
            val longitude: Double = location.getLongitude()
            val message =
                "내 위치 -> Latitude : $latitude\nLongitude:$longitude"
            textView!!.setText(message)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(
            provider: String,
            status: Int,
            extras: Bundle
        ) {
        }
    }

    //버튼을 눌렀을 때 호출되는 메소드
    fun startLocationService() {
        val manager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val location =
                manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val message =
                    "최근 위치 -> Latitude : $latitude\nLongitude:$longitude"
                textView!!.text = message
            }
            val gpsListener = GPSListener()
            val minTime: Long = 10000
            val minDistance = 0f
            manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, gpsListener
            )
            Toast.makeText(
                applicationContext, "내 위치확인 요청함",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}