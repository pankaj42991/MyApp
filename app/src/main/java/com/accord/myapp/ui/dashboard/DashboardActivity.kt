package com.accord.myapp.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.accord.myapp.R
import com.accord.myapp.databinding.ActivityDashboardBinding
import com.accord.myapp.logic.BackupManager
import com.accord.myapp.ui.calendar.CalendarFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var backupManager: BackupManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Load Calendar Fragment (ONLY ONCE)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.calendar_container, CalendarFragment())
                .commit()
        }

        // Theme switch
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            toggleDarkMode(isChecked)
        }

        // Backup
        backupManager = BackupManager(this)
        backupManager.syncToDrive()
    }

    private fun toggleDarkMode(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
