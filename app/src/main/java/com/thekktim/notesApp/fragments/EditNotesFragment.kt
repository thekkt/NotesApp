package com.example.notes.fragments

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


class EditNotesFragment : Fragment() {
    private lateinit var editTitle : EditText
    private lateinit var editDate : TextView
    private lateinit var editDescription : EditText
    private lateinit var btnPreservation : Button
    private val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_user_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val note = bundle?.getSerializable(Utils.KEY) as Notes

        btnPreservation = view.findViewById(R.id.btnPreservation)
        editTitle = view.findViewById(R.id.editTitle)
        editDate = view.findViewById(R.id.editTextTime)
        editDescription = view.findViewById(R.id.editDescription)
        
        editTitle.setText(note.notesTitle)
        editDate.text = DateFormat.getDateInstance().format(note.notesDate).toString()
        editDescription.setText(note.notesDescription)

        btnPreservation.setOnClickListener {
            note.notesTitle = editTitle.text.toString()
            note.notesDescription = editDescription.text.toString()
            setSharedPreferences(Utils.notesList)
        }

    }
    private fun setSharedPreferences(userNotes: MutableList<Notes>) {
        val sharedPreference =
            context?.getSharedPreferences("list", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val userNotesString = gson.toJson(userNotes)
        editor?.putString("list", userNotesString)
        editor?.apply()
    }
}
