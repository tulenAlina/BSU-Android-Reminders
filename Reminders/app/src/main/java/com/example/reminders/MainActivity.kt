package com.example.reminders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reminders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ReminderListFragment())
            .commit()

        binding.btnAddReminder.setOnClickListener {
            AddReminderDialog().show(supportFragmentManager, "AddReminderDialog")
        }
    }
}