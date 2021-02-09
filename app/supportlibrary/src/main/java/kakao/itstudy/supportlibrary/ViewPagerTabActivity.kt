package kakao.itstudy.supportlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class TabOne : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.tab_one, container, false) as LinearLayout
    }
}

class TabTwo : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.tab_two, container, false) as LinearLayout
    }
}

class TabThree : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (container == null) {
            null
        } else inflater.inflate(R.layout.tab_three, container, false) as LinearLayout?
    }
}

class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var fragments: MutableList<Fragment> = ArrayList()
    var title = arrayOf("Tab1", "Tab2", "Tab3")
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    init {
        fragments.add(TabOne())
        fragments.add(TabTwo())
        fragments.add(TabThree())
    }
}

class ViewPagerTabActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var fab: FloatingActionButton? = null
    var relativeLayout: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_tab)

        relativeLayout = findViewById<RelativeLayout>(R.id.container)
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager!!.adapter = MyPagerAdapter(supportFragmentManager)

        val tabLayout: TabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        fab =
            findViewById<FloatingActionButton>(R.id.fab)
        fab!!.setOnClickListener{
            Snackbar.make(relativeLayout!!, "I am SnackBar", Snackbar.LENGTH_LONG)
                .setAction("MoreAction", View.OnClickListener {
                    //...........
                }).show()

        }
    }
}