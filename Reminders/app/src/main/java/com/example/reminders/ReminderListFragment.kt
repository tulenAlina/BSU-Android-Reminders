package com.example.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminders.databinding.FragmentReminderListBinding
import com.example.reminders.models.Reminder
import com.example.reminders.utils.ReminderStorage

class ReminderListFragment : Fragment() {
    private lateinit var binding: FragmentReminderListBinding
    private lateinit var adapter: ReminderAdapter
    private val reminders = mutableListOf<Reminder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReminderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reminders.addAll(ReminderStorage.load(requireContext()))
        adapter = ReminderAdapter(reminders) {
            reminders.removeAt(it)
            ReminderStorage.save(requireContext(), reminders)
            adapter.notifyItemRemoved(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}