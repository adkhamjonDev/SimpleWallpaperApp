package com.example.simplewallpaperapp
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simplewallpaperapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import eightbitlab.com.blurview.RenderScriptBlur
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_random
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //----------------------------------
        val radius = 10f
        val decorView = window.decorView
        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        binding.appBarMain.contentMain.blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)

        binding.appBarMain.contentMain.navBarHome.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.nav_home)
        }
        binding.appBarMain.contentMain.navBarPopular.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.nav_gallery)
        }
        binding.appBarMain.contentMain.navBarLiked.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.nav_slideshow)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.addCategory(Intent.CATEGORY_DEFAULT)
                i.data = Uri.parse("package:$packageName")
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun hide() {
        supportActionBar!!.hide()
        binding.appBarMain.contentMain.cardNavBar.visibility= View.GONE
    }
    fun show() {
        supportActionBar!!.show()
        binding.appBarMain.contentMain.cardNavBar.visibility= View.VISIBLE
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}