package com.example.reminders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reminders.databinding.ItemReminderBinding
import com.example.reminders.models.Reminder

class ReminderAdapter(
    private val reminders: List<Reminder>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnDelete.setOnClickListener {
                onDelete(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.binding.tvReminderText.text = reminder.text
        holder.binding.tvReminderDateTime.text = reminder.dateTime
    }

    override fun getItemCount() = reminders.size
}