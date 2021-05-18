package com.example.notes.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notes.data.Notes
import com.example.notes.R
import com.example.notes.Utils
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EditNotesFragment : Fragment() {
    private lateinit var editTitle : EditText
    private lateinit var editDate : TextView
    private lateinit var editDescription : EditText
    private lateinit var btnPreservation : Button
    private lateinit var selectDate: Button
    private lateinit var noteDate: Date
    private val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val note = bundle?.getSerializable(Utils.KEY) as Notes

        btnPreservation = view.findViewById(R.id.btnPreservation)
        editTitle = view.findViewById(R.id.editTitle)
        editDate = view.findViewById(R.id.editDate)
        editDescription = view.findViewById(R.id.editDescription)
        selectDate = view.findViewById(R.id.saveEditDate)

        editTitle.setText(note.notesTitle)
        editDate.text = DateFormat.getDateInstance().format(note.notesDate).toString()
        editDescription.setText(note.notesDescription)

        btnPreservation.setOnClickListener {
            note.notesTitle = editTitle.text.toString()
            note.notesDescription = editDescription.text.toString()
            setSharedPreferences(Utils.notesList)
        }
        editDate.setOnClickListener {
            val myCalendar = Calendar.getInstance()
            val year = myCalendar.get(Calendar.YEAR)
            val month = myCalendar.get(Calendar.MONTH)
            val day = myCalendar.get(Calendar.DAY_OF_MONTH)

            context?.let {
                DatePickerDialog(
                    it,
                    DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        editDate.text = (selectedDate)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                         noteDate = sdf.parse(selectedDate) as Date
                    },
                    year,
                    month,
                    day
                ).show()
            }
        }
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val noteArg = bundle?.getSerializable(Utils.KEY) as Notes
        editTitle.setText(noteArg.notesTitle)
        editDescription.setText(noteArg.notesDescription)
        editDate.text = sdf.format(noteArg.notesDate).toString()

    }
    private fun setSharedPreferences(userNotes: MutableList<Notes>) {
        val sharedPreference =
            context?.getSharedPreferences("notesList", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val userNotesString = gson.toJson(userNotes)
        editor?.putString("notesList", userNotesString)
        editor?.apply()
    }

}
