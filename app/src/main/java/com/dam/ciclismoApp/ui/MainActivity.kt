package com.dam.ciclismoApp.ui
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.ActivityMainBinding
import com.dam.ciclismoApp.utils.P

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_view_main) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        binding.rootView.viewTreeObserver.addOnGlobalLayoutListener {
            detectKeyboard { isVisible ->
                if (isVisible) {
                    binding.navView.visibility = View.GONE
                } else {
                    binding.navView.visibility = View.VISIBLE
                }
            }
        }
        P.init(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun detectKeyboard(onKeyboardVisibilityChanged: (Boolean) -> Unit) {
        val rootView = window.decorView.rootView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootView.height
            val keyboardHeight = screenHeight - r.bottom
            val isKeyboardVisible = keyboardHeight > screenHeight * 0.15
            onKeyboardVisibilityChanged(isKeyboardVisible)
        }
    }
}
