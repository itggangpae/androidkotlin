package kakao.itstudy.persistentstorage

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingUseActivity : AppCompatActivity() {
    // PreferenceFragment: XML 로 작성한 Preference 를 UI 로 보여주는 클래스
    class MyPrefFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Preference 정보가 있는 XML 파일 지정
            addPreferencesFromResource(R.xml.setting_pref)
            //환경 설정 값이 변경될 때 작업을 수행
            val singerlistPref = findPreference<ListPreference>("singerlist")
            singerlistPref?.setOnPreferenceChangeListener {
                    preference, newValue ->
                Log.e("singerlist",  newValue.toString());
                true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_use)

        //환경 설정 레이아웃으로 뷰를 변경
        supportFragmentManager.beginTransaction().replace(android.R.id.content, MyPrefFragment()).commit()
        //환경 설정 값을 읽어오기
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        Log.e("내가 좋아하는 걸그룹", prefs.getString("singerlist", "기본값").toString())
    }
}