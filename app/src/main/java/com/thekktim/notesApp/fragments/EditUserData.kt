package com.example.notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.adapters.NotesAdapter

class EditUserData: Fragment(), NotesAdapter.OnCardItemClick {


    lateinit var userAvatar : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user_data, container, false)


    }

    override fun onCardClick(position: Int) {

    }

    override fun onChangeClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClick(position: Int) {
        TODO("Not yet implemented")
    }

}