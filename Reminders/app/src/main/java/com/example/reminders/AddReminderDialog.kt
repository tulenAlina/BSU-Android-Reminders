package com.example.reminders

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.reminders.databinding.DialogAddReminderBinding
import com.example.reminders.models.Reminder
import com.example.reminders.utils.ReminderStorage
import java.text.SimpleDateFormat
import java.util.*

class AddReminderDialog : DialogFragment() {
    private lateinit var binding: DialogAddReminderBinding
    private var selectedDateTime: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddReminderBinding.inflate(layoutInflater)

        binding.btnPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, y, m, d ->
                TimePickerDialog(requireContext(), { _, h, min ->
                    calendar.set(y, m, d, h, min)
                    selectedDateTime = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(calendar.time)
                    binding.tvPickedDateTime.text = selectedDateTime
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_reminder)
            .setView(binding.root)
            .setPositiveButton(R.string.save, null)  // Временно null
            .setNegativeButton(R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            val saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            saveButton.setOnClickListener {
                val title = binding.etReminderText.text.toString().trim()

                // Проверяем, введено ли название
                if (title.isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.enter_title), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Останавливаем выполнение, не закрываем окно
                }

                // Проверяем, выбрана ли дата и время
                if (selectedDateTime.isBlank()) {
                    Toast.makeText(requireContext(), getString(R.string.select_date_time), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Останавливаем выполнение, не закрываем окно
                }

                // Если всё введено корректно
                val reminders = ReminderStorage.load(requireContext())
                reminders.add(Reminder(title, selectedDateTime))
                ReminderStorage.save(requireContext(), reminders)

                // Обновляем главный экран
                (requireActivity() as MainActivity).recreate()

                // Закрываем диалог
                dismiss()  // Закрытие диалога из метода dismiss() фрагмента
            }
        }

        return dialog
    }
}
