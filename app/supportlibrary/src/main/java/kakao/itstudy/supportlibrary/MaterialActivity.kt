package kakao.itstudy.supportlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MaterialActivity : AppCompatActivity() {
    var drawer: DrawerLayout? = null
    var toggle: ActionBarDrawerToggle? = null
    var isDrawerOpend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        drawer = findViewById(R.id.main_drawer)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toggle!!.syncState()

        val navigationView: NavigationView = findViewById(R.id.main_drawer_view)
        navigationView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id: Int = item.getItemId()
                if (id == R.id.menu_drawer_home) {
                    showToast("NavigationDrawer... home...")
                    Log.e("home", "NavigationDrawer... home...")
                } else if (id == R.id.menu_drawer_message) {
                    showToast("NavigationDrawer... message...")
                    Log.e("message", "NavigationDrawer... message...")
                } else if (id == R.id.menu_drawer_add) {
                    showToast("NavigationDrawer... add...")
                    Log.e("add", "NavigationDrawer... add...")
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            false
        } else super.onOptionsItemSelected(item!!)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

}