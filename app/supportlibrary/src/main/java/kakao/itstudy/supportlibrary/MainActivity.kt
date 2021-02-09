package kakao.itstudy.supportlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    var manager: FragmentManager? = null
    var oneFragment: OneFragment? = null
    var twoFragment: TwoFragment? = null
    var threeFragment: ThreeFragment? = null
    var searchView: SearchView? = null
    internal class MyPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        var fragments: MutableList<Fragment>? = null
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(i: Int): Fragment {
            return fragments!!.get(i)
        }

        init {
            fragments = mutableListOf<Fragment>()
            fragments!!.add(OneFragment())
            fragments!!.add(ThreeFragment())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setIcon(R.drawable.icon)

        manager = supportFragmentManager
        oneFragment = OneFragment()
        twoFragment = TwoFragment()
        threeFragment = ThreeFragment()

        val main_btn1 = findViewById<Button>(R.id.main_btn1)

        main_btn1.setOnClickListener {
            if(!oneFragment!!.isVisible()){
                val tf : FragmentTransaction = manager!!.beginTransaction();
                tf.addToBackStack(null);
                tf.replace(R.id.main_container, oneFragment!!);
                tf.commit();

            }
        }

        val main_btn2 = findViewById<Button>(R.id.main_btn2)
        main_btn2.setOnClickListener {
            if(!twoFragment!!.isVisible()){
                twoFragment!!.show(manager!!, null);
            }
        }

        val main_btn3 = findViewById<Button>(R.id.main_btn3)
        main_btn3.setOnClickListener {
            if(!threeFragment!!.isVisible()){
                val tf:FragmentTransaction = manager!!.beginTransaction();
                tf.addToBackStack(null);
                tf.replace(R.id.main_container, threeFragment!!);
                tf.commit()
                registerForContextMenu(threeFragment!!.view!!.findViewById<TextView>(R.id.textView))
            }
        }

        val tf: FragmentTransaction = manager!!.beginTransaction()
        tf.addToBackStack(null)
        tf.add(R.id.main_container, oneFragment!!)
        tf.commit()

        val pager = findViewById<ViewPager>(R.id.pager)
        val adapter = MyPagerAdapter(supportFragmentManager)
        pager.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_lab, menu)
        try {
            val method: Method = menu.javaClass.getDeclaredMethod(
                "setOptionalIconsVisible",
                Boolean::class.javaPrimitiveType
            )
            method.setAccessible(true)
            method.invoke(menu, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val menuItem: MenuItem = menu.findItem(R.id.menu_main_search)
        searchView = menuItem.actionView as SearchView
        searchView!!.queryHint = resources.getString(R.string.query_hint)
        searchView!!.setOnQueryTextListener(queryTextListener)
        return true
    }

    var queryTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                searchView!!.setQuery("", false)
                searchView!!.isIconified = true
                Log.e("menu", s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        }
}