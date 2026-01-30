package com.accord.myapp.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.accord.myapp.databinding.ActivityDashboardBinding
import com.accord.myapp.logic.BackupManager
import androidx.appcompat.app.AppCompatDelegate

fun toggleDarkMode(isDark: Boolean) {
    if (isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}

// Usage with Switch in DashboardActivity
binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
    toggleDarkMode(isChecked)
}

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var backupManager: BackupManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -----------------------------
        // Initialize Backup Manager
        // -----------------------------
        backupManager = BackupManager(this)

        // Sync local DB to Google Drive automatically
        backupManager.syncToDrive()
    }
}