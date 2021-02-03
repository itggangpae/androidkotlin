package kakao.itstudy.network

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    var listView: ListView? = null
    var datas: MutableList<String>? = null
    var adapter: ArrayAdapter<String>? = null

    //전화기 상태가 변경되었을 때 의 이벤트를 처리하는 리스너
    var listener: PhoneStateListener = object : PhoneStateListener() {
        override fun onServiceStateChanged(serviceState: ServiceState) {
            when (serviceState.state) {
                ServiceState.STATE_EMERGENCY_ONLY -> datas?.add("onServiceStateChanged STATE_EMERGENCY_ONLY")
                ServiceState.STATE_IN_SERVICE -> datas?.add("onServiceStateChanged STATE_IN_SERVICE")
                ServiceState.STATE_OUT_OF_SERVICE -> datas?.add("onServiceStateChanged STATE_OUT_OF_SERVICE")
                ServiceState.STATE_POWER_OFF -> datas?.add("onServiceStateChanged STATE_POWER_OFF")
                else -> datas?.add("onServiceStateChanged Unknown")
            }
            adapter!!.notifyDataSetChanged()
        }

        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> datas?.add("onCallStateChanged : CALL_STATE_IDLE : $incomingNumber")
                TelephonyManager.CALL_STATE_RINGING -> datas?.add("onCallStateChanged : CALL_STATE_RINGING : $incomingNumber")
                TelephonyManager.CALL_STATE_OFFHOOK -> datas?.add("onCallStateChanged : CALL_STATE_OFFHOOK : $incomingNumber")
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    //네트워크 상태를 체크하는 메소드
    private fun checkNetwork() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null) {
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                datas!!.add("Network Info : Online - " + networkInfo.typeName)
            } else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                datas!!.add("Network Info : Online - " + networkInfo.typeName)
            }
        } else {
            datas!!.add("Network Info : Offline")
        }
        adapter!!.notifyDataSetChanged()
    }

    //wifi 상태를 체크하는 메소드
    private fun checkWifi() {
        val wifiManager =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            datas!!.add("WifiManager : wifi disabled")
            if (wifiManager.wifiState != WifiManager.WIFI_STATE_ENABLED) {
                wifiManager.isWifiEnabled = true
            }
        } else {
            datas!!.add("WifiManager : wifi enabled")
        }
        adapter!!.notifyDataSetChanged()
    }

    //네트워크 상태가 변경된 경우 수신하는 리시버
    var wifiReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiManager.WIFI_STATE_ENABLED) {
                    datas!!.add("WIFI_STATE_CHANGED_ACTION : enable")
                } else {
                    datas!!.add("WIFI_STATE_CHANGED_ACTION : disable")
                }
            } else if (intent.action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
                val networkInfo =
                    intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                val wifiManager =
                    context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                val ssid = wifiInfo.ssid
                if (networkInfo!!.state == NetworkInfo.State.CONNECTED) {
                    datas!!.add("NETWORK_STATE_CHANGED_ACTION : connected...$ssid")
                } else if (networkInfo!!.state == NetworkInfo.State.DISCONNECTED) {
                    datas!!.add("NETWORK_STATE_CHANGED_ACTION : disconnected...$ssid")
                }
            }
            else if (intent.action == WifiManager.RSSI_CHANGED_ACTION) {
                datas!!.add("RSSI_CHANGED_ACTION")
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    //퍼미션 요청의 결과 메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                } else {
                }
                return
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getHashKey()

        listView =  findViewById(R.id.lab1_listview) as ListView
        datas = mutableListOf<String>()
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas!!)
        listView?.setAdapter(adapter)

        val telManager : TelephonyManager =  getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        telManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE+PhoneStateListener.LISTEN_SERVICE_STATE)

        datas?.add("countryIos:" + telManager.getNetworkCountryIso())
        datas?.add("operatorName:" + telManager.getNetworkOperatorName())
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                100)
            return
        }
        if (telManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
            datas!!.add("networkType:LTE")
        } else if (telManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA) {
            datas!!.add("networkType:3G")
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
           // datas?.add("PhoneNumber:"+telManager.getLine1Number())
        }else {
            ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.READ_PHONE_STATE), 100)
        }
        checkNetwork()
        checkWifi()
        val  wifiFilter : IntentFilter = IntentFilter()
        wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        wifiFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        registerReceiver(wifiReceiver, wifiFilter)
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

}
