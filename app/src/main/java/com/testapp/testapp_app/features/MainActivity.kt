package com.testapp.testapp_app.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.testapp.testapp_app.R
import com.testapp.testapp_app.setup.BaseActivity

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
    //endregion

    //region Methods
    //endregion

    companion object {
        private val LOGTAG: String = MainActivity::class.java.simpleName
        fun intent(context: Context) = Intent(context, MainActivity::class.java)
    }
}