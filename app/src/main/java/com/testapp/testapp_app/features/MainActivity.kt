package com.testapp.testapp_app.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.testapp.testapp_app.R
import com.testapp.testapp_app.setup.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity() {
    //region Vars
    private val navController by lazy { findNavController(R.id.fragment) }
    //endregion

    //region Override Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        // Get the NavController for your NavHostFragment
        val navController = findNavController(R.id.fragment)
        // Set up the ActionBar to stay in sync with the NavController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.action_favorite) {
            this.fragment.view?.findNavController()?.navigate(R.id.action_global_beer_favs)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Methods
    //endregion

    companion object {
        private val LOGTAG: String = MainActivity::class.java.simpleName
        fun intent(context: Context) = Intent(context, MainActivity::class.java)
    }
}