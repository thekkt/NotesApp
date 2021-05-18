package com.example.notes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notes.data.Notes
import com.example.notes.R
import com.example.notes.Utils
import java.text.SimpleDateFormat

class DetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

        val bundle = this.arguments
        val note = bundle?.getSerializable(Utils.KEY) as Notes


        view.findViewById<TextView>(R.id.noteTitle).text = note.notesTitle
        view.findViewById<TextView>(R.id.noteDescription).text = note.notesDescription
        view.findViewById<TextView>(R.id.noteDate).text =  sdf.format(note.notesDate).toString()

       

    }
}