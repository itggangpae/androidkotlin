package kakao.itstudy.supportlibrary


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout


class Fragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment1, container, false)
    }
}

class Fragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2, container, false)
    }
}

class Fragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment3, container, false)
    }
}


class TabUseActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    var fragment1: Fragment1? = null
    var fragment2: Fragment2? = null
    var fragment3: Fragment3? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_use)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)

        fragment1 = Fragment1()
        fragment2 = Fragment2()
        fragment3 = Fragment3()

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment1!!).commit()

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.addTab(tabs.newTab().setText("통화기록"))
        tabs.addTab(tabs.newTab().setText("스팸기록"))
        tabs.addTab(tabs.newTab().setText("연락처"))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position: Int = tab.getPosition()
                Log.d("MainActivity", "선택된 탭 : $position")
                var selected: Fragment? = null
                if (position == 0) {
                    selected = fragment1
                } else if (position == 1) {
                    selected = fragment2
                } else if (position == 2) {
                    selected = fragment3
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, selected!!).commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        val bottomNavigation: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.tab1 -> {
                        Toast.makeText(applicationContext, "첫 번째 탭 선택됨", Toast.LENGTH_LONG)
                            .show()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragment1!!).commit()
                        return true
                    }
                    R.id.tab2 -> {
                        Toast.makeText(applicationContext, "두 번째 탭 선택됨", Toast.LENGTH_LONG)
                            .show()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragment2!!).commit()
                        return true
                    }
                    R.id.tab3 -> {
                        Toast.makeText(applicationContext, "세 번째 탭 선택됨", Toast.LENGTH_LONG)
                            .show()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragment3!!).commit()
                        return true
                    }
                }
                return false
            }
        })
    }
}